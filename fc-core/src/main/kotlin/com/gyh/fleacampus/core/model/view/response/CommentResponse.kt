package com.gyh.fleacampus.core.model.view.response

import com.gyh.fleacampus.core.model.Comment
import com.gyh.fleacampus.core.model.Reply
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

    @Schema(defaultValue = "0:没有点赞，1:点赞")
    var liked: Int? = null

    var replyList: List<ReplyResponse>? = listOf()
}
