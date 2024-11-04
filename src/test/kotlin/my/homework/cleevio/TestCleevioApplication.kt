package my.homework.cleevio


import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<CleevioApplication>().with(TestcontainersConfiguration::class).run(*args)
}
