package com.gyh.fleacampus.socket.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component

/**
 * Created by gyh on 2021/7/3
 */
@Component
class RedisUtil {
    @Autowired
    lateinit var redisTemplate: ReactiveRedisTemplate<String, Any>
}