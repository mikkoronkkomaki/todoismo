package ffs.misaw.todoismo.controllers

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.services.TaskService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(private val taskService: TaskService) {
    @GetMapping
    fun getAllTasks(): List<Task> {
        val tasks = taskService.getAllTasks()
        return tasks
    }

    @PostMapping
    fun createTask(@RequestParam description: String): ResponseEntity<String> {
        val task = taskService.saveTask(description)
        return ResponseEntity.ok(
            """      
        <div hx-swap="outerHTML" hx-get="/api/tasks/html" hx-trigger="revealed"></div>
    """.trimIndent()
        )
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<String> {
        taskService.deleteTask(id)
        
        return ResponseEntity.ok(
            """
        <div hx-swap="outerHTML" hx-get="/api/tasks/html" hx-trigger="revealed" hx-target="#tasksTable"></div>
    """.trimIndent()
        )
    }
}

@Controller
class HtmlController(private val taskService: TaskService) {
    @GetMapping(value = ["/api/tasks/html"], produces = [MediaType.TEXT_HTML_VALUE])
    fun tasksAsHTML(model: Model): String {
        val tasks = taskService.getAllTasks()
        model.addAttribute("tasks", tasks)
        return "tasks"
    }
}

