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

    fun insertTask(description: String): Task {
        val newTask = taskRepository.insertTask(description)
        elasticService.indexTask(newTask)
        return newTask
    }
    fun updateTask(id: Long, description: String, done: Boolean) {
        taskRepository.updateTask(id, description, done)
        elasticService.updateTask(id, description, done)
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteTask(id)
        elasticService.deleteTask(id)
    }
}