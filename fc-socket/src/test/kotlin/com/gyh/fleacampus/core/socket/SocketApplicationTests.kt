package com.gyh.fleacampus.core.socket

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.lang.IllegalStateException
import java.time.Duration
import java.util.concurrent.TimeoutException

//@SpringBootTest
class SocketApplicationTests {

    @Test
    fun contextLoads() {
        val receiveProcessor: Sinks.Many<Int> = Sinks.many().multicast().onBackpressureBuffer()
        receiveProcessor.asFlux()
            .timeout(Duration.ofMillis(100))
            .doOnError(TimeoutException::class.java) { println("超时 ") }
            .then()
            .subscribe()
        receiveProcessor.asFlux()
            .flatMap {
                println(it)
                if (it == 5) Mono.error<Int>(IllegalStateException("123")).onErrorResume {e -> println("nmka>>>>>>>>>>");Mono.just(51) }
                else Mono.just(it)
            }
            //.filter { it > 5 }
            .map { println("nmka" + receiveProcessor.currentSubscriberCount()) }.log()
            .subscribe()
        for (i in 1..10) {
            println(" >> $i")
            println(receiveProcessor.tryEmitNext(i).isSuccess)
            Thread.sleep(10)
        }
        //Thread.sleep(1000)
    }

}
