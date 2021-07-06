package com.gyh.fleacampus.core.socket

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import java.time.Duration
import java.util.concurrent.TimeoutException

//@SpringBootTest
class SocketApplicationTests {

    @Test
    fun contextLoads() {
        val receiveProcessor: Sinks.Many<String> = Sinks.many().multicast().onBackpressureBuffer()
        receiveProcessor.asFlux()
            .timeout(Duration.ofMillis(1000))
            .doOnError(TimeoutException::class.java) { println("超时 ") }
            .then()
            .subscribe()
        receiveProcessor.asFlux()
            .filter { false }
            .map { println(it) }
            .subscribe()
        for (i in 1 ..100) {
            receiveProcessor.tryEmitNext(i.toString())
            println(" >> " + i)
            Thread.sleep(100)
        }
        Thread.sleep(5000)
    }

}
