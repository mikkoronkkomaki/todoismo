package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.repositories.TaskRepository
import org.elasticsearch.action.delete.DeleteRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentFactory
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val client: RestHighLevelClient
) {

    fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }

    fun getTask(taskId: Long): Task {
        return taskRepository.getTask(taskId)
    }

    fun saveTask(description: String): Task {
        val newTask = taskRepository.saveTask(description)
        indexTaskToElasticsearch(newTask)
        return newTask
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteTask(id)
        deleteTaskFromElasticsearch(id)

    }

    private fun indexTaskToElasticsearch(task: Task) {
        val source = XContentFactory.jsonBuilder()
            .startObject()
            .field("id", task.id)
            .field("description", task.description)
            .endObject()

        val indexRequest = IndexRequest("tasks")
            .source(source)

        client.index(indexRequest, RequestOptions.DEFAULT)
    }

    private fun deleteTaskFromElasticsearch(id: Long) {
        val deleteRequest = DeleteRequest("tasks", id.toString())
        client.delete(deleteRequest, RequestOptions.DEFAULT)
    }

}