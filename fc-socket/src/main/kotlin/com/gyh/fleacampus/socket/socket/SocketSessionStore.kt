package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.entity.GroupMessage
import com.gyh.fleacampus.socket.entity.Message
import com.gyh.fleacampus.socket.entity.ResponseInfo
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by gyh on 2020/4/12.
 */
object SocketSessionStore {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    internal val userInfoMap = ConcurrentHashMap<Int, UserRoomInfo>()

    fun addUser(session: SessionHandler, id: Int, roomId: Int?): Mono<Unit> {
        val userInfo = UserRoomInfo(session, id, roomId)
        val old = userInfoMap.put(id, userInfo)
        logger.info("添加用户 $id ${session.getSessionId()}")
        return if (old != null) {
            logger.info("用户多地登陆 $id ${old.session.getSessionId()}")
            old.session.send(ResponseInfo.ok<Unit>("用户账号在其他地方登陆"), NotifyOrder.differentPlaceLogin)
                .map { old.session.tryEmitComplete() }.then(Mono.just(Unit))
        } else Mono.just(Unit)
    }

    fun removeUser(userId: Int?) {
        userInfoMap.remove(userId)
        logger.info("移除用户 $userId")
    }

    fun getRoomInfo(userId: Int): UserRoomInfo? {
        return userInfoMap[userId]
    }

    fun getOnLineSize(roomId: Int): Int {
        return userInfoMap.count { it.value.roomId == roomId }
    }

    /**
     * 在自己的房间广播消息
     */
    fun broadcastMag(msg: GroupMessage): Mono<Unit> {
        // TODO 优化房间消息发送机制
        userInfoMap.entries.forEach { (userId, userRoomInfo) ->
            if (msg.areaId == userRoomInfo.roomId && userId != msg.userId) {
                return userRoomInfo.session.send(ResponseInfo.ok("广播", msg), NotifyOrder.groupMag, true)
            }
        }
        return Mono.just(Unit)
    }

    /**
     * 对某个人推送消息
     */
    fun sendMsgToUser(userId: Int, msg: Message): Mono<Unit> {
        return userInfoMap[userId]?.session
            ?.send(ResponseInfo.ok("广播", msg), NotifyOrder.userMsg, true)
            ?: Mono.just(Unit)
    }

    data class UserRoomInfo(
        val session: SessionHandler,
        val userId: Int,
        val roomId: Int?
    )
}
