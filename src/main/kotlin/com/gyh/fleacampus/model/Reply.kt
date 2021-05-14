package com.gyh.fleacampus.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

/**
 * fc_reply
 * @author
 */
@Schema(description = "回复")
open class Reply (
    @Schema(description = "id")
    var id: Int? = null,

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    var commentId: Int? = null,

    /**
     * 回复目标id
     */
    @Schema(description = "回复目标id")
    var replyId: Int? = null,

    /**
     * 回复类型 COMMENT：回复的评论，REPLY：回复的回复
     */
    @Schema(description = "回复类型 COMMENT：回复的评论，REPLY：回复的回复")
    var replyType: String? = null,

    /**
     * 回复内容
     */
    @Schema(description = "回复内容")
    var content: String? = null,

    /**
     * 回复的用户id
     */
    @Schema(description = "回复的用户id")
    var userId: Int? = null,

    /**
     * 目标用户id
     */
    @Schema(description = "目标用户id")
    var toUid: Int? = null,

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    var likes: Int? = null,

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    var createTime: Date? = null,

    /**
     * 0:隐藏，1:展示
     */
    @Schema(description = "0:隐藏，1:展示")
    var flag: Short? = null,
) {
    enum class ReplyType {
        COMMENT, REPLY
    }

    fun checkStatus() {
        if (!ReplyType.values().map { it.name }.contains(replyType)) {
            error("帖子状态是未定义的 {$replyType}")
        }
    }
}