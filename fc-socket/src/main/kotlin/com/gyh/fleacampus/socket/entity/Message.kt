package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.gyh.fleacampus.socket.CustomLocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/10
 */
@Table("fc_user_message")
open class Message(
    @Id
    var id: Int? = null,
    var userId: Int? = null,
    var msgType: Int? = null,
    var content: String? = null,
    var path: String? = null,
    var toUid: Int? = null,
    @get:JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var date: LocalDateTime? = null
)
