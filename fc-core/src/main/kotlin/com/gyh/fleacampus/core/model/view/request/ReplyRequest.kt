package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Reply
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/8/3
 */
@Schema(description = "回复请求实体")
class ReplyRequest(@Schema(description = "回复类型 'POST'：回复帖子，'DEAL'：回复二手") val type: CommentRequest.CommentType = CommentRequest.CommentType.POST) :
    Reply() {
    fun checkType() = type == CommentRequest.CommentType.POST || type == CommentRequest.CommentType.DEAL
}