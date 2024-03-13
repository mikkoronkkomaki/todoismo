package ffs.misaw.todoismo.repositories

import ffs.misaw.todoismo.dataclasses.Task
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository

@Repository
class TaskRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getAllTasks(): List<Task> {
        return jdbcTemplate.query(
            "SELECT id, description FROM task"
        ) { rs, _ -> Task(rs.getLong("id"), rs.getString("description")) }
    }
}