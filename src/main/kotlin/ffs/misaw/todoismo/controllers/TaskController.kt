package ffs.misaw.todoismo.controllers

import ffs.misaw.todoismo.dataclasses.Task
import ffs.misaw.todoismo.services.TaskService
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tasks")
class TaskController (private val taskService: TaskService){
    @GetMapping
    fun getAllTasks(): List<Task> {
        val tasks = taskService.getAllTasks()
        return tasks
    }
}

@Controller
class HtmlController (private val taskService: TaskService) {
    @GetMapping(value = ["/api/tasks/html"], produces = [MediaType.TEXT_HTML_VALUE])
    fun tasksAsHTML(model: Model): String {
        val tasks = taskService.getAllTasks()
        model.addAttribute("tasks", tasks)
        return "tasks"
    }
}