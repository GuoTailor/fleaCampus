package com.gyh.fleacampus.core.model.view.response

import com.gyh.fleacampus.core.model.Reply
import io.swagger.v3.oas.annotations.media.Schema

/**
 * fc_reply
 * @author
 */
@Schema(description = "回复响应实体")
class ReplyResponse : Reply() {
    @Schema(description = "用户名")
    var username: String? = null

    @Schema(description = "用户头像")
    var headimgurl: String? = null

    @Schema(description = "回复目标用户名")
    var toUsername: String? = null

    @Schema(defaultValue = "0:没有点赞，1:点赞")
    var liked: Int? = null
}