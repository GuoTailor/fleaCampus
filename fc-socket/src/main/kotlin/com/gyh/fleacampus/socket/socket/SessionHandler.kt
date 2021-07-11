package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.distribute.ServiceResponseInfo
import org.slf4j.LoggerFactory
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import java.time.Duration
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by gyh on 2021/7/9
 */
class SessionHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val responseCount = AtomicInteger(1)
    private val responseMap = ConcurrentHashMap<Int, SendInfo>()
    private var retryCount = 3
    private var retryTimeout = 1L
    private val source: Sinks.Many<ServiceResponseInfo.DataResponse> = Sinks.many().multicast().onBackpressureBuffer()
    val dataMap = HashMap<String, Any>()

    /**
     * 被动回应调用
     */
    fun send(message: ServiceResponseInfo.DataResponse, confirm: Boolean = false) {
        if (confirm) {
            val cycle = Flux.interval(Duration.ofSeconds(retryTimeout))
                .map {
                    if (false != responseMap[message.req]?.ack || it >= retryCount) {
                        val remove = responseMap.remove(message.req)
                        remove?.cycle?.dispose()
                        reqIncrement(message.req)
                        Mono.just(message)
                    } else {
                        source.tryEmitNext(message)
                    }
                }.subscribeOn(Schedulers.boundedElastic())
                .subscribe()
            responseMap[message.req] = SendInfo(message.req, cycle)
        }
        source.tryEmitNext(message)
    }

    /**
     * 主动发送请调用该方法
     */
    fun <T> send(data: Mono<T>, order: Int, confirm: Boolean = false): Mono<Unit> {
        val req = responseCount.getAndIncrement()
        return ServiceResponseInfo(data, req, order).getMono()
            .map { send(it, confirm) }
    }

    fun tryEmitComplete(): Sinks.EmitResult {
        return source.tryEmitComplete()
    }

    fun asFlux(): Flux<ServiceResponseInfo.DataResponse> {
        return source.asFlux()
    }

    fun setSessionId(sessionId: String) {
        dataMap["sessionId"] = sessionId
    }

    fun getSessionId(): String {
        return dataMap["sessionId"].toString()
    }

    fun reqIncrement(req: Int) {
        val value = responseMap[req]
        if (value != null) {
            value.ack = true
            value.cycle.dispose()
            logger.info("取消 {} ", req)
            responseMap.remove(req)
        }
    }

    data class SendInfo(
        val req: Int,
        val cycle: Disposable,
        var ack: Boolean = false
    )
}