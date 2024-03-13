package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.repositories.TaskRepository
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {

    fun getAllTasks(): List<Task> {
        return taskRepository.getAllTasks()
    }
}