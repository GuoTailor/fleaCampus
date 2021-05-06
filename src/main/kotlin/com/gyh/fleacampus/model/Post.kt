package com.gyh.fleacampus.model

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import java.util.*

/**
 *
 * @TableName fc_post
 */
@ApiModel(value = "帖子")
data class Post(
    @ApiModelProperty(value = "id")
    var id: Int? = null,
    /**
     * 发布者id
     */
    @ApiModelProperty(value = "发布者id")
    var userId: Int? = null,
    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    var title: String? = null,
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    var content: String? = null,
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    var createTime: Date? = null,
    /**
     * 状态 draft：草稿，normal：发布，timing：定时发布
     */
    @ApiModelProperty(value = "状态")
    var state: String? = null,
    /**
     * 定时发布时间
     */
    @ApiModelProperty(value = "定时发布时间")
    var releaseTime: Date? = null,
    /**
     * 帖子类型
     * buy：买
     * sell:卖
     * confess：表白
     * game: 游戏
     * other：其他
     */
    @ApiModelProperty(value = "帖子类型\n" +
            "buy：买\n" +
            "sell:卖\n" +
            "confess：表白\n" +
            "game: 游戏\n" +
            "other：其他")
    var type: String? = null,
)
