package ffs.misaw.todoismo.dataclasses

data class Task(val id: Long, val description: String, val done: Boolean = false)
data class PagedTasks(val tasks: List<Task>, val remaining: Int)
