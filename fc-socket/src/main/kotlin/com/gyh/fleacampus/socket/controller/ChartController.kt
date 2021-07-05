package com.gyh.fleacampus.socket.controller

import com.gyh.fleacampus.socket.common.Util
import com.gyh.fleacampus.socket.entity.ResponseInfo
import com.gyh.fleacampus.socket.service.UserService
import com.gyh.fleacampus.socket.socket.SocketSessionStore
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

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

    @RequestMapping("/room/join")
    fun joinRoom(): Mono<Unit> {
        return Util.getcurrentUser().map { SocketSessionStore.joinRoom(it.id!!) }
    }

    @RequestMapping("/room/quit")
    fun quitRoom(): Mono<Unit> {
        return Util.getcurrentUser().map { SocketSessionStore.quitRoom(it.id!!) }
    }

}