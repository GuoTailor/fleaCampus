package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.gyh.fleacampus.socket.CustomLocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/10
 * @apiDefine Message
 * @apiParam {Int} msgType 消息类型，暂时还未定义，预计有图片消息，文字消息，语言消息，现在瞎几把传，没做区分
 * @apiParam {String} content 消息内容
 * @apiParam {Int} toUid 对方用户id
 */
@Table("fc_user_message")
open class Message(
    @Id
    var id: Int? = null,
    var userId: Int? = null,
    var msgType: Int? = null,
    var content: String? = null,
    var toUid: Int? = null,
    @get:JsonSerialize(using = CustomLocalDateTimeSerializer::class)
    var date: LocalDateTime? = null
)
