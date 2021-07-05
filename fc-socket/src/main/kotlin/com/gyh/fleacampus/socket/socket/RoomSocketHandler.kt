package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.entity.ResponseInfo
import com.gyh.fleacampus.socket.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

/**
 * Created by gyh on 2020/4/5.
 */
@WebSocketMapping("/room")
class RoomSocketHandler : SocketHandler() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun onConnect(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler): Mono<*> {
        val id = queryMap["id"] ?: return sessionHandler.send("错误，不支持的参数列表$queryMap")
            .then(sessionHandler.connectionClosed())
        return SocketSessionStore.addUser(sessionHandler, id.toInt(), "userName")
            .onErrorResume {
                sessionHandler.send(ResponseInfo.failed("错误: ${it.message}"), NotifyOrder.errorNotify)
                    .doOnNext { msg -> logger.info("send $msg") }.flatMap { Mono.empty() }
            }
    }

    override fun onDisconnected(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler) {
        val id = queryMap["id"]?.toInt()
        SocketSessionStore.removeUser(id)
    }

}
