package com.gyh.fleacampus.model.view

import com.gyh.fleacampus.model.Reply
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * fc_reply
 * @author
 */
class ReplyResponse : Reply() {
    @Schema(description = "用户名")
    var username: String? = null

    @Schema(description = "用户头像")
    var photo: String? = null

    @Schema(description = "回复目标用户名")
    var toUsername: String? = null
}