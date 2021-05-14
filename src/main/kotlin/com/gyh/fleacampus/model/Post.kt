package com.gyh.fleacampus.model

import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

/**
 * fc_post
 * @author
 */
@Schema(description = "帖子")
open class Post(
    @Schema(description = "id")
    var id: Int? = null,

    /**
     * 发布者id
     */
    @Schema(description = "发布者id")
    var userId: Int? = null,

    /**
     * 标题
     */
    @Schema(description = "标题")
    var title: String? = null,

    /**
     * 内容
     */
    @Schema(description = "内容")
    var content: String? = null,

    /**
     * 状态 draft：草稿，normal：发布，timing：定时发布
     */
    @Schema(description = "状态 DRAFT：草稿，NORMAL：发布，TIMING：定时发布")
    var state: String? = null,

    /**
     * 定时发布时间
     */
    @Schema(description = "定时发布时间", example = "1620783689372", type = "number")
    var releaseTime: LocalDateTime? = null,

    /**
     * 帖子类型
     * buy：买
     * sell:卖
     * confess：表白
     * game: 游戏
     * other：其他
     */
    @Schema(description = "内容")
    var type: String? = null,

    /**
     * 点赞数
     */
    @Schema(description = "点赞数")
    var likes: Int? = null,

    /**
     * 评论数
     */
    @Schema(description = "评论数")
    var comments: Int? = null,

    /**
     * 收藏数
     */
    @Schema(description = "收藏数")
    var collects: Int? = null,

    /**
     * 0：违规，1：有效
     */
    @Schema(description = "0：违规，1：有效")
    var flag: Int? = null,

    /**
     * 置顶顺序，越大排序越靠前
     */
    @Schema(description = "置顶顺序，越大排序越靠前")
    var topOrder: Int? = null,

    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1620783689372", type = "number")
    var createTime: LocalDateTime? = null,
) {


    enum class ReleaseState {
        DRAFT, NORMAL, TIMING
    }

    fun checkStatus() {
        if (!ReleaseState.values().map { it.name }.contains(state)) {
            error("帖子状态是未定义的 {$state}")
        } else if (ReleaseState.valueOf(state!!) == ReleaseState.TIMING) {
            if (LocalDateTime.now().isAfter(releaseTime)) {
                error("定时发布时间已过 {$releaseTime}")
            }
        }
    }

}