package ffs.misaw.todoismo.configuration

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestClientBuilder
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfig {
    @Value("\${elasticsearch.host:localhost}")
    var host: String? = null

    @Value("\${elasticsearch.port:9200}")
    var port = 0
    @Bean(destroyMethod = "close")
    fun client(): RestHighLevelClient {
        val builder: RestClientBuilder = RestClient.builder(HttpHost(host, port))
        return RestHighLevelClient(builder)
    }
}