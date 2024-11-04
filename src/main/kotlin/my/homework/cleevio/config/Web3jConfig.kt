package my.homework.cleevio.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService


@Configuration
class Web3jConfig {

    @Value("\${ethereum.url}")
    lateinit var url: String

    @Bean
    fun web3j(): Web3j {
        return Web3j.build(HttpService(url.ifEmpty { "http://127.0.0.1:8545" }))
    }
}
