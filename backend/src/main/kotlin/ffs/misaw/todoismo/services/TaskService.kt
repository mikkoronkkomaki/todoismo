package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.repositories.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val elasticService: ElasticService
) {

    fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }

    fun getTask(taskId: Long): Task {
        return taskRepository.getTask(taskId)
    }

    fun saveTask(description: String): Task {
        val newTask = taskRepository.saveTask(description)
        elasticService.indexTaskToElasticsearch(newTask)
        return newTask
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteTask(id)
        elasticService.deleteTaskFromElasticsearch(id)
    }
}