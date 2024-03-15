package ffs.misaw.todoismo.repositories

import ffs.misaw.todoismo.dataclasses.Task
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository

@Repository
class TaskRepository(private val jdbcTemplate: JdbcTemplate) {

    fun getAllTasks(): List<Task> {
        return jdbcTemplate.query(
            "SELECT id, description FROM task"
        ) { rs, _ ->
            Task(
                rs.getLong("id"),
                rs.getString("description")
            )
        }
    }

    fun saveTask(description: String): Task {
        val keyHolder: KeyHolder = GeneratedKeyHolder()

        jdbcTemplate.update(
            PreparedStatementCreator { connection ->
                val ps = connection.prepareStatement("INSERT INTO task (description) VALUES (?)", arrayOf("id"))
                ps.setString(1, description)
                ps
            }, keyHolder
        )

        val taskId = keyHolder.key!!.toLong()

        return Task(taskId, description)
    }

    fun getTask(taskId: Long): Task {
        return jdbcTemplate.query(
            "SELECT id, description FROM task WHERE id = ?",
            arrayOf(taskId)
        ) { rs, _ ->
            Task(
                rs.getLong("id"),
                rs.getString("description")
            )
        }.first()
    }

    fun deleteTask(id: Long) {
        jdbcTemplate.update(
            "DELETE FROM task WHERE id = ?",
            id
        )
    }
}