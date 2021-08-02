package com.gyh.fleacampus.core.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

/**
 * fc_comment
 * @author
 */
@Schema(description = "评论")
open class Comment(
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
     * 回复数
     */
    @Schema(description = "回复数")
    var replys: Int? = null,

    /**
     * 评论内容
     */
    @Schema(description = "评论内容")
    var content: String? = null,

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    var createTime: LocalDateTime? = null,

    /**
     * 0:隐藏，1:展示, 2:删除
     */
    @Schema(description = "0:隐藏，1:展示, 2:删除")
    var flag: Int? = null,

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    var likes: Int? = null,

    /**
     * 置顶顺序，越大排序越靠前
     */
    @Schema(description = "置顶顺序，越大排序越靠前")
    var topOrder: Int? = null,

    /**
     * 隐藏说明，用于违规提示
     */
    @Schema(description = "隐藏说明，用于违规提示")
    var remark: String? = null,
) {
    companion object {
        const val HIDE = 0
        const val SHOW = 1
        const val DELETE = 2
    }
}