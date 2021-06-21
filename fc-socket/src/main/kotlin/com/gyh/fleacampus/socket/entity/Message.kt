package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.gyh.fleacampus.socket.CustomLocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/10
 */
@Table("sc_room_message")
data class Message(
    @Id
    val id: Int?,
    val userId: Int,
    val msgType: Int,
    val content: String,
    val path: String,
    val name: String,
    val avatar: String,
    val roomId: Int,
    @get:JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    val date: LocalDateTime
)
