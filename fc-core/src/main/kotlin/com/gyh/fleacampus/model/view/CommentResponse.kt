package com.gyh.fleacampus.model.view

import com.gyh.fleacampus.model.Comment
import com.gyh.fleacampus.model.Reply
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/5/12
 */
@Schema(description = "评论响应")
class CommentResponse : Comment() {
    @Schema(description = "用户名")
    var username: String? = null

    @Schema(description = "用户头像")
    var photo: String? = null
    var replyList: List<Reply>? = listOf()
}
