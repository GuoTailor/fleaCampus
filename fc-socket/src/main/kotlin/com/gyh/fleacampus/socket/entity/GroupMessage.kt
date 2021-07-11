package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.gyh.fleacampus.socket.CustomLocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/10
 * @apiDefine GroupMessage
 * @apiParam {Int} msgType 消息类型，暂时还未定义，预计有图片消息，文字消息，语言消息，现在瞎几把传，没做区分
 * @apiParam {String} content 消息内容
 * @apiParam {Int} areaId 区域id
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
