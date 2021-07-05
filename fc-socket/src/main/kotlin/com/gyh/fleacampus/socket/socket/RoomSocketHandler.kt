package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.common.Util
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

    @Autowired
    lateinit var userService: UserService

    override fun onConnect(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler): Mono<*> {
        return userService.loadUser()
            .flatMap {
                if (it.areaId == null) Mono.error(IllegalAccessException("该用户没有加入任圈子"))
                else userService.findArea(it.areaId!!)
            }.zipWith(Util.getcurrentUser()) { o1, o2 ->
                SocketSessionStore.addUser(sessionHandler, o2.id!!, o1.id!!)
            }.onErrorResume {
                sessionHandler.send(ResponseInfo.failed("错误: ${it.message}"), NotifyOrder.errorNotify)
                    .doOnNext { msg -> logger.info("send $msg") }.flatMap { Mono.empty() }
            }
    }

    override fun onDisconnected(queryMap: Map<String, String>, sessionHandler: WebSocketSessionHandler) {
        val id = queryMap["id"]?.toInt()
        SocketSessionStore.removeUser(id)
    }

}
