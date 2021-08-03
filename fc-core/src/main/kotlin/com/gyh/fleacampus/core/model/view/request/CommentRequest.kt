package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Comment
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/8/3
 */
@Schema(description = "评论请求实体")
class CommentRequest(@Schema(description = "评论类型 'POST'：评论帖子，'DEAL'：评论二手") val type: CommentType = CommentType.POST) :
    Comment() {

    enum class CommentType {
        POST, DEAL
    }

    fun checkType(): Boolean = type == CommentType.POST || type == CommentType.DEAL

}