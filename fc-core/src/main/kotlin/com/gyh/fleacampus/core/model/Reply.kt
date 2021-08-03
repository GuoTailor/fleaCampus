package com.gyh.fleacampus.core.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

/**
 * fc_reply
 * @author
 */
@Schema(description = "回复")
open class Reply(
    @Schema(description = "id")
    var id: Int? = null,

    /**
     * 帖子id
     */
    @Schema(description = "帖子id")
    @NotNull
    var postId: Int? = null,

    /**
     * 评论id
     */
    @Schema(description = "评论id")
    @NotNull
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
    var replyType: ReplyType? = null,

    /**
     * 回复内容
     */
    @Schema(description = "回复内容")
    @NotEmpty
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
    @NotNull
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
    var createTime: LocalDateTime? = null,

    /**
     * 0:隐藏，1:展示
     */
    @Schema(description = "0:隐藏，1:展示")
    var flag: Short? = null,

    /**
     * 隐藏说明，用于违规提示
     */
    @Schema(description = "隐藏说明，用于违规提示")
    var remark: String? = null,

    ) {

    enum class ReplyType {
        COMMENT, REPLY
    }

    fun checkStatus() {
        postId ?: error("帖子id不能为空")
        commentId ?: error("评论的id不能为空")
        replyType ?: error("回复类型是未定义的 {$replyType}")
    }
}