package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.common.Util
import com.gyh.fleacampus.socket.entity.ResponseInfo
import com.gyh.fleacampus.socket.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Mono

/**
 * Created by gyh on 2021/7/5.
 */
@WebSocketMapping("/room")
class RoomSocketHandler : SocketHandler() {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var userService: UserService

    override fun onConnect(sessionHandler: SessionHandler): Mono<*> {
        return userService.loadUser()
            .flatMap {
                sessionHandler.dataMap["id"] = it.id!!
                SocketSessionStore.addUser(sessionHandler, it.id!!, it.areaId)
            }.onErrorResume {
                it.printStackTrace()
                sessionHandler.send(ResponseInfo.failed("错误: ${it.message}"), NotifyOrder.errorNotify)
            }
    }

    override fun onDisconnected(sessionHandler: SessionHandler) {
        val id = sessionHandler.dataMap["id"] as Int
        SocketSessionStore.removeUser(id)
    }

}
