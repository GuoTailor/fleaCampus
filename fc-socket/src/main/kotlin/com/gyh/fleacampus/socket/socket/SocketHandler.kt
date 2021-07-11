package com.gyh.fleacampus.socket.socket

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gyh.fleacampus.common.getJavaTimeModule
import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.distribute.DispatcherServlet
import com.gyh.fleacampus.socket.distribute.ServiceRequestInfo
import com.gyh.fleacampus.socket.distribute.ServiceResponseInfo
import com.gyh.fleacampus.socket.entity.ResponseInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

/**
 * Created by gyh on 2020/5/19.
 */
abstract class SocketHandler : WebSocketHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val json = jacksonObjectMapper()

    @Autowired
    private lateinit var dispatcherServlet: DispatcherServlet

    init {
        json.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        json.registerModule(getJavaTimeModule())
    }

    override fun handle(session: WebSocketSession): Mono<Void> {
        val sessionHandler = SessionHandler()
        sessionHandler.setSessionId(session.id)
        val input = session.receive()
            .map { it.payloadAsText }
            .map(::toServiceRequestInfo)
            .filter { it.order != "/ping" }
            .filter { filterConfirm(it, sessionHandler) }
            .doOnNext(::printLog)
            .flatMap {
                val resp = ServiceResponseInfo(req = it.req, order = NotifyOrder.requestReq)
                dispatcherServlet.doDispatch(it, resp)
                resp.getMono().onErrorResume { e ->
                    logger.info("错误 {}", e.message)
                    ServiceResponseInfo(
                        ResponseInfo.failed("错误 ${e.message}"),
                        NotifyOrder.errorNotify,
                        NotifyOrder.requestReq
                    ).getMono()
                }
            }
            .map { sessionHandler.send(it, true) }
            .doOnTerminate { onDisconnected(sessionHandler); sessionHandler.tryEmitComplete() }
            .then()//.log()
        val output = session.send(sessionHandler.asFlux()
            .map { json.writeValueAsString(it) }
            .doOnError { logger.info("错误 {}", it.message) }
            .map { session.textMessage(it) })
        val onCon = onConnect(sessionHandler)
        return Mono.zip(onCon, input, output).then()
    }

    /**
     * 当socket连接时
     */
    abstract fun onConnect(sessionHandler: SessionHandler): Mono<*>

    /**
     * 当socket断开连接时
     */
    abstract fun onDisconnected(sessionHandler: SessionHandler)

    private fun toServiceRequestInfo(data: String): ServiceRequestInfo {
        return this.json.readValue(data)
    }

    private fun printLog(info: ServiceRequestInfo): ServiceRequestInfo {
        if (info.order != "/echo")
            logger.info("接收到数据order:{} req:{} data:{}", info.order, info.req, info.body)
        return info
    }

    private fun filterConfirm(info: ServiceRequestInfo, sessionHandler: SessionHandler): Boolean {
        if (info.order == "/ok") {
            sessionHandler.reqIncrement(info.req)
            return false
        }
        return true
    }
}