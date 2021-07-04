package com.gyh.fleacampus.socket.config

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gyh.fleacampus.common.toLocalDateTime
import com.gyh.fleacampus.socket.common.Util
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.LocalDateTime
import java.time.ZoneId


/**
 * Created by gyh on 2020/3/24.
 */
@Configuration
class ReactiveRedisConfiguration {

    @Bean
    fun redisOperations(factory: ReactiveRedisConnectionFactory): ReactiveRedisTemplate<String, Any> {
        val om = jacksonObjectMapper()
        //om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        //om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.activateDefaultTyping(
            om.polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        )
        om.registerModule(KotlinModule())
        val jackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer(om)
        val stringRedisSerializer = StringRedisSerializer()
        om.registerModule(Util.getJavaTimeModule())
        val context = RedisSerializationContext.newSerializationContext<String, Any>()
            .key(stringRedisSerializer)             // key采用String的序列化方式
            .value(jackson2JsonRedisSerializer)     // value序列化方式采用jackson
            .hashKey(stringRedisSerializer)         // hash的key也采用String的序列化方式
            .hashValue(jackson2JsonRedisSerializer) // hash的value序列化方式采用jackson
            .build()
        return ReactiveRedisTemplate(factory, context)
    }
}