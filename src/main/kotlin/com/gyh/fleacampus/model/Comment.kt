package com.gyh.fleacampus.model

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * fc_comment
 * @author
 */
@Schema(description = "评论")
open class Comment (
    @Schema(description = "id")
    var id: Int? = null,

    /**
     * 评论用户
     */
    @Schema(description = "评论用户")
    var userId: Int? = null,

    /**
     * 评论帖子
     */
    @Schema(description = "评论帖子")
    var postId: Int? = null,

    /**
     * 回复的评论
     */
    @Schema(description = "回复的评论")
    var commentId: Int? = null,

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    var content: String? = null,

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    var createTime: Date? = null,

    /**
     * 0:隐藏，1:展示
     */
    @Schema(description = "0:违规隐藏，1:展示")
    var flag: Int? = null,
)