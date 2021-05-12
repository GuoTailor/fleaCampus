package com.gyh.fleacampus.model

import io.swagger.v3.oas.annotations.media.Schema
import java.lang.IllegalStateException
import java.time.LocalDateTime
import java.util.*

/**
 *
 * @TableName fc_post
 */
@Schema(description = "帖子")
data class Post(
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
     * 创建时间
     */
    @Schema(description = "创建时间", example = "1620783689372", type = "integer")
    var createTime: LocalDateTime? = null,

    /**
     * 状态 draft：草稿，normal：发布，timing：定时发布
     */
    @Schema(description = "状态")
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
    @Schema(
        description = "帖子类型\n" +
                "buy：买\n" +
                "sell:卖\n" +
                "confess：表白\n" +
                "game: 游戏\n" +
                "other：其他",
        example = "sell"
    )
    var type: String? = null,
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
