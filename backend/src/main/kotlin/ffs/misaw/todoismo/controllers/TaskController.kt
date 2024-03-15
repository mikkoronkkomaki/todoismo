package ffs.misaw.todoismo.controllers

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.services.ElasticService
import ffs.misaw.todoismo.services.TaskService
import org.springframework.http.ResponseEntity
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService,
    private val elasticService: ElasticService
) {
    @GetMapping
    fun getAllTasks(): List<Task> {
        val tasks = taskService.getAllTasks()
        return tasks
    }

    @PostMapping
    fun createTask(@RequestBody task: Task): ResponseEntity<Task> {
        val savedTask = taskService.saveTask(task.description)
        return ResponseEntity.ok(savedTask)
    }

    @DeleteMapping("{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Void> {
        taskService.deleteTask(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(value = ["/search"])
    fun search(@RequestParam query: String, model: Model): ResponseEntity<List<Task>> {
        val tasks  = elasticService.searchTasks(query)
        return ResponseEntity.ok(tasks)
    }
}
