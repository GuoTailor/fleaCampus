package com.gyh.fleacampus.socket.service

import com.gyh.fleacampus.socket.entity.GroupMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * Created by gyh on 2021/7/3
 */
@Component
class RedisUtil {
    @Autowired
    lateinit var redisTemplate: ReactiveRedisTemplate<String, Any>
    private val groupMsgKey = "GROUP_MSG:"
    private val groupMsgSize = 300L

    fun pushGroupMsg(groupMsg: GroupMessage): Mono<Boolean> {
        return redisTemplate.opsForList().rightPush(groupMsgKey + groupMsg.areaId, groupMsg)
            .flatMap { redisTemplate.opsForList().trim(groupMsgKey + groupMsg.areaId, 0, groupMsgSize) }
    }

    fun getGroupMsg(areaId: Int, pageSize: Int, pageNum: Int): Flux<GroupMessage> {
        val end = pageNum * pageSize
        val start = end - pageSize
        return redisTemplate.opsForList()
            .range(groupMsgKey + areaId, start.toLong(), end.toLong())
            .cast(GroupMessage::class.java)
    }
}
