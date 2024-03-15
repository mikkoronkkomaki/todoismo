package ffs.misaw.todoismo

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoismoApplication

fun main(args: Array<String>) {
	runApplication<TodoismoApplication>(*args) {
		setBannerMode(Banner.Mode.OFF)
	}
}
