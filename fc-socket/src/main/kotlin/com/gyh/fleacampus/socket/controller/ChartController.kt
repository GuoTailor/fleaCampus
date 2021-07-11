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

    /**
     * @api {connect} /echo 测试接口
     * @apiDescription  测试接口，该接口的[value]字段传什么就放回什么
     * @apiName echo
     * @apiParam {String} value 任意字符
     * @apiVersion 0.0.1
     * @apiParamExample {json} 请求-例子:
     * {"order":"/echo", "body": {"value": "123"}, "req":12}
     * @apiSuccessExample {json} 成功返回:
     * {"body":{"code":0,"msg":"成功","data":{"value":"123"}},"req":12,"order":0}
     * @apiGroup Socket
     * @apiPermission user
     */
    @RequestMapping("/echo")
    fun echo(@RequestParam value: String): Mono<ResponseInfo<Map<String, String>>> {
        return ResponseInfo.ok(Mono.just(mapOf("value" to value)))
    }

    /**
     * @api {connect} /message/group 发送群消息
     * @apiDescription 发送群消息
     * @apiName groupMessage
     * @apiUse GroupMessage
     * @apiVersion 0.0.1
     * @apiSuccessExample {json} 成功返回:
     * {"body":{"code":0,"msg":"成功","data":null},"req":12,"order":0}
     * @apiGroup Socket
     * @apiPermission user
     */
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

    /**
     * @api {connect} /message/user 私发消息
     * @apiDescription 私发消息
     * @apiName userMessage
     * @apiUse Message
     * @apiVersion 0.0.1
     * @apiSuccessExample {json} 成功返回:
     * {"body":{"code":0,"msg":"成功","data":null},"req":12,"order":0}
     * @apiGroup Socket
     * @apiPermission user
     */
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