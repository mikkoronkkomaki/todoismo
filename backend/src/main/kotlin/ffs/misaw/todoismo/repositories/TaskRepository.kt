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
            "SELECT id, description, done FROM task"
        ) { rs, _ ->
            Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getBoolean("done")
            )
        }
    }

    fun insertTask(description: String): Task {
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
            "SELECT id, description, done FROM task WHERE id = ?",
            arrayOf(taskId)
        ) { rs, _ ->
            Task(
                rs.getLong("id"),
                rs.getString("description"),
                rs.getBoolean("done")
            )
        }.first()
    }

    fun deleteTask(id: Long) {
        jdbcTemplate.update(
            "DELETE FROM task WHERE id = ?",
            id
        )
    }

    fun updateTask(id: Long, description: String, done: Boolean) {
        jdbcTemplate.update(
            "UPDATE task SET description = ?, done = ? WHERE id = ?",
            description,
            done,
            id
        )
    }
}