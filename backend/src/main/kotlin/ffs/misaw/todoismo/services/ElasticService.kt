package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.dataclasses.PagedTasks
import ffs.misaw.todoismo.dataclasses.Task
import org.json.JSONArray
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
    fun indexTask(task: Task) {
        val taskJson = JSONObject()
        taskJson.put("id", task.id)
        taskJson.put("description", task.description)
        taskJson.put("done", task.done)

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

    fun deleteTask(id: Long) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/$id"))
            .DELETE()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to delete task from Elasticsearch: ${response.body()}")
        }
    }

    fun updateTask(id: Long, description: String, done: Boolean) {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/$id/_update"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"doc\": {\"description\": \"${description}\", \"done\": ${done}}}"))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() != 200) {
            throw RuntimeException("Failed to update task in Elasticsearch: ${response.body()}")
        }
    }

    fun isTaskIndexed(taskId: Long): Boolean {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_doc/$taskId"))
            .GET()
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return response.statusCode() == 200
    }

    fun searchTasks(searchText: String, from: Int, size: Int): PagedTasks {
        val searchQuery = JSONObject()
        searchQuery.put(
            "query", JSONObject().put(
                "wildcard",
                JSONObject().put(
                    "description.keyword",
                    JSONObject().put("value", "*$searchText*").put("case_insensitive", true)
                )
            )
        )
        searchQuery.put(
            "sort", JSONArray().put(
                JSONObject().put(
                    "id", JSONObject().put("order", "desc")
                )
            )
        )
        searchQuery.put("from", from)
        searchQuery.put("size", size)


        val request = HttpRequest.newBuilder()
            .uri(URI.create("${baseUrl}/tasks/_search"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(searchQuery.toString()))
            .build()

        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())

        val tasks = mutableListOf<Task>()
        var totalHits = 0
        if (response.statusCode() == 200) {
            val responseBody = JSONObject(response.body())
            totalHits = responseBody.getJSONObject("hits").getJSONObject("total").getInt("value")
            val hits = responseBody.getJSONObject("hits").getJSONArray("hits")

            (0 until hits.length())
                .map { hits.getJSONObject(it).getJSONObject("_source") }
                .mapTo(tasks) {
                    Task(
                        id = it.getLong("id"),
                        description = it.getString("description"),
                        done = if (it.has("done")) it.getBoolean("done") else false
                    )
                }
        } else {
            throw RuntimeException("Failed to search tasks in Elasticsearch: ${response.body()}")
        }

        val remaining = totalHits - (from + size)
        return PagedTasks(tasks, if (remaining > 0) remaining else 0)
    }
}