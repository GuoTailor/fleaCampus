package com.gyh.fleacampus.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import java.io.IOException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by gyh on 2021/2/10
 */
@Configuration
class OtherConfig {
    @Bean
    fun jackson2ObjectMapperBuilderCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.serializerByType(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime>() {
                override fun serialize(     // 序列化
                    localDateTime: LocalDateTime,
                    jsonGenerator: JsonGenerator,
                    serializerProvider: SerializerProvider
                ) {
                    val timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                    jsonGenerator.writeNumber(timestamp)
                }
            }).deserializerByType(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime>() {
                override fun deserialize(   // 反序列化
                    jsonParser: JsonParser,
                    deserializationContext: DeserializationContext
                ): LocalDateTime {
                    val timestamp = jsonParser.valueAsLong
                    return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                }
            })
        }
    }
}