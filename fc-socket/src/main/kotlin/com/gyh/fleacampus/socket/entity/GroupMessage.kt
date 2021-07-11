package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.gyh.fleacampus.socket.CustomLocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/10
 */
data class GroupMessage(
    var userId: Int? = null,
    val msgType: Int,
    var headImg: String? = null,
    val content: String,
    val areaId: Int,
    @get:JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var date: LocalDateTime? = null
)
