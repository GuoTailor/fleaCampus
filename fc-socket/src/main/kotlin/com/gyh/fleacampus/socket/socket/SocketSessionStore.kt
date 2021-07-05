package com.gyh.fleacampus.socket.socket

import com.gyh.fleacampus.socket.common.NotifyOrder
import com.gyh.fleacampus.socket.entity.ResponseInfo
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

/**
 * Created by gyh on 2020/4/12.
 */
object SocketSessionStore {
    private val logger = LoggerFactory.getLogger(this.javaClass)
    internal val userInfoMap = ConcurrentHashMap<Int, UserRoomInfo>()

    fun addUser(session: WebSocketSessionHandler, id: Int, roomId: Int): Mono<Unit> {
        val userInfo = UserRoomInfo(session, id, roomId)
        val old = userInfoMap.put(id, userInfo)
        logger.info("添加用户 $id ${session.getId()}")
        return if (old != null) {
            logger.info("用户多地登陆 $id ${old.session.getId()}")
            old.session.send(ResponseInfo.ok<Unit>("用户账号在其他地方登陆"), NotifyOrder.differentPlaceLogin)
                .flatMap { old.session.connectionClosed() }.map { Unit }.log()
        } else Mono.just(Unit)
    }

    fun joinRoom(userId: Int) {
        userInfoMap[userId]?.isJoin = true
    }

    fun quitRoom(userId: Int) {
        userInfoMap[userId]?.isJoin = false
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
    fun broadcastMag() {

    }

    data class UserRoomInfo(
        val session: WebSocketSessionHandler,
        val userId: Int,
        val roomId: Int,
        var isJoin: Boolean = false
    )
}
