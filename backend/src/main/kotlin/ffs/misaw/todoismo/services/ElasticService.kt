package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.dataclasses.Task
import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Value

@Service
class ElasticService {
    @Value("\${elasticsearch.baseUrl}")
    private lateinit var baseUrl: String
    private val httpClient = HttpClient.newHttpClient()
    fun indexTaskToElasticsearch(task: Task) {
        val taskJson = JSONObject()
        taskJson.put("id", task.id)
        taskJson.put("description", task.description)

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/${task.id}"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(taskJson.toString()))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 201) {
            throw RuntimeException("Failed to index task to Elasticsearch: ${response.body()}")
        }
    }

    fun deleteTaskFromElasticsearch(id: Long) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/$id"))
            .DELETE()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to delete task from Elasticsearch: ${response.body()}")
        }
    }

    fun searchTasks(searchText: String): List<Task> {
        val searchQuery = JSONObject()
        searchQuery.put("query", JSONObject().put("wildcard", JSONObject().put("description.keyword", JSONObject().put("value", "*$searchText*").put("case_insensitive", true))))

        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_search"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(searchQuery.toString()))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val tasks = mutableListOf<Task>()
        if (response.statusCode() == 200) {
            val hits = JSONObject(response.body()).getJSONObject("hits").getJSONArray("hits")
            (0 until hits.length())
                .map { hits.getJSONObject(it).getJSONObject("_source") }
                .mapTo(tasks) {
                    Task(
                        id = it.getLong("id"),
                        description = it.getString("description")
                    )
                }
        } else {
            throw RuntimeException("Failed to search tasks in Elasticsearch: ${response.body()}")
        }

        return tasks
    }

    fun updateTaskInElasticsearch(id: Long, description: String) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/$id/_update"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"doc\": {\"description\": \"${description}\"}}"))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to update task in Elasticsearch: ${response.body()}")
        }
    }
}