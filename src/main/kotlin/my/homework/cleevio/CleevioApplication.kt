package my.homework.cleevio

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class CleevioApplication

fun main(args: Array<String>) {
    runApplication<CleevioApplication>(*args)
}
