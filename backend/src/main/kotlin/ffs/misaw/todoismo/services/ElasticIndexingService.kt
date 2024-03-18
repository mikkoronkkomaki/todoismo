package ffs.misaw.todoismo.services

import ffs.misaw.todoismo.repositories.TaskRepository
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ElasticIndexingService(private val taskRepository: TaskRepository, private val elasticService: ElasticService) {
    @PostConstruct
    fun indexTasksToElasticSearch() {
        println("Check that all tasks are indexed to Elasticsearch")
        val tasks = taskRepository.getAllTasks()
        tasks.forEach { task ->
            if (!elasticService.isTaskIndexed(task.id)) {
                println("Task with id ${task.id} is not indexed to Elasticsearch, indexing now...") 
                elasticService.indexTask(task)
            }
        }
    }
}