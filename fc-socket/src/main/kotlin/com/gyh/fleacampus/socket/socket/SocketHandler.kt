package com.gyh.fleacampus.socket.socket

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.common.Util
import com.gyh.fleacampus.socket.distribute.DispatcherServlet
import com.gyh.fleacampus.socket.distribute.ServiceRequestInfo
import com.gyh.fleacampus.socket.distribute.ServiceResponseInfo
import com.gyh.fleacampus.socket.entity.ResponseInfo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by gyh on 2020/5/19.
 */
abstract class SocketHandler : WebSocketHandler {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val blankRegex = "\\s".toRegex()
    private val orderRegex = "\"order\":(.*?)[,}]".toRegex()
    private val dataRegex = "\"data\":(.*?})[,}]".toRegex()
    private val reqRegex = "\"req\":(.*?)[,}]".toRegex()
    val json = jacksonObjectMapper()
    @Autowired
    private lateinit var dispatcherServlet: DispatcherServlet

    init {
        json.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addSerializer(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime>() {
            override fun serialize(value: LocalDateTime, gen: JsonGenerator, serializers: SerializerProvider) {
                val timestamp = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                gen.writeNumber(timestamp)
            }
        })
        javaTimeModule.addDeserializer(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime>() {
            override fun deserialize(p: JsonParser, ctxt: DeserializationContext): LocalDateTime {
                val temp = p.valueAsLong
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(temp), ZoneId.systemDefault())
            }
        })
        json.registerModule(javaTimeModule)
    }

    override fun handle(session: WebSocketSession): Mono<Void> {
        val sessionHandler = WebSocketSessionHandler(session)
        val watchDog = WebSocketWatchDog().start(sessionHandler, 5000)
        val queryMap = Util.getQueryMap(sessionHandler.getSession().handshakeInfo.uri.query)
        val connect = sessionHandler.connected().flatMap { onConnect(queryMap, sessionHandler) }
            .flatMap { sessionHandler.send(ResponseInfo.ok<Unit>("连接成功"), NotifyOrder.connectSucceed, true) }
        sessionHandler.disconnected{ onDisconnected(queryMap, sessionHandler) }
        val output = sessionHandler.receive()
            .map(::toServiceRequestInfo)
            .map(::printLog)
            .filter { it.order != "/ping" }    // 心跳就不回应
            .filter { filterConfirm(it, sessionHandler) }
            .flatMap {
                val resp = ServiceResponseInfo(req = it.req, order = NotifyOrder.requestReq)
                dispatcherServlet.doDispatch(it, resp)
                resp.getMono()
            }.onErrorResume {
                it.printStackTrace()
                ServiceResponseInfo(
                    ResponseInfo.failed("错误 ${it.message}"),
                    NotifyOrder.errorNotify,
                    NotifyOrder.requestReq
                ).getMono()
            }.flatMap{ sessionHandler.send(it,true) }
            .doOnNext { logger.info("send> $it") }
            .then()

        return sessionHandler.handle()
            .zipWith(connect)
            .zipWith(watchDog)
            .zipWith(output)
            .then()
    }

    /**
     * 当socket连接时
     */
    abstract fun onConnect(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler): Mono<*>

    /**
     * 当socket断开连接时
     */
    abstract fun onDisconnected(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler)

    private fun toServiceRequestInfo(data: String): ServiceRequestInfo {
        // TODO 经测试正则表达式比jackson反序列化慢
        val json = data.replace(blankRegex, "")
        val orderString = orderRegex.find(json)!!.groups[1]!!.value.replace("\"", "")
        val dataString = dataRegex.find(json)?.groups?.get(1)?.value
        val reqString = reqRegex.find(json)!!.groups[1]!!.value.toInt()
        return ServiceRequestInfo(orderString, dataString, reqString)
    }

    private fun printLog(info: ServiceRequestInfo): ServiceRequestInfo {
        if (info.order != "/echo" && info.order != "/ping")
            logger.info("接收到数据order:{} req:{} data:{}", info.order, info.req, info.body)
        return info
    }

    private fun filterConfirm(info: ServiceRequestInfo, sessionHandler: WebSocketSessionHandler): Boolean {
        if (info.order == "/ok") {
            sessionHandler.reqIncrement(info.req)
            return false
        }
        return true
    }
}