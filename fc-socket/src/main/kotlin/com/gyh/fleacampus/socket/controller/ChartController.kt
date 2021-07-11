package com.gyh.fleacampus.socket.controller

import com.gyh.fleacampus.socket.common.Util
import com.gyh.fleacampus.socket.entity.GroupMessage
import com.gyh.fleacampus.socket.entity.Message
import com.gyh.fleacampus.socket.entity.ResponseInfo
import com.gyh.fleacampus.socket.service.UserService
import com.gyh.fleacampus.socket.socket.SocketSessionStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/7/3
 */
@RestController
class ChartController {
    @Autowired
    lateinit var userService: UserService

    @RequestMapping("/echo")
    fun echo(@RequestParam value: String): Mono<ResponseInfo<Map<String, String>>> {
        return ResponseInfo.ok(Mono.just(mapOf("value" to value)))
    }

    @RequestMapping("/message/group")
    fun groupMessage(@RequestBody msg: GroupMessage): Mono<ResponseInfo<Unit>> {
        return userService.loadUser()
            .map { user ->
                msg.userId = user.id
                msg.date = LocalDateTime.now()
                msg.headImg = user.photo
                msg
            }.flatMap { SocketSessionStore.broadcastMag(it) }
            .flatMap { ResponseInfo.ok("成功") }
    }

    @RequestMapping("/message/user")
    fun userMessage(@RequestBody msg: Message): Mono<ResponseInfo<Unit>> {
        return Util.getcurrentUser()
            .map {
                msg.userId = msg.toUid
                msg.toUid = it.id
                msg
            }.flatMap { SocketSessionStore.sendMsgToUser(it.userId!!, it) }
            .flatMap { ResponseInfo.ok("成功") }
    }

}