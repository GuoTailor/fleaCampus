package com.gyh.fleacampus.model

import java.util.*

/**
 *
 * @TableName fc_post
 */
data class Post (

    var id: Int? = null,
    /**
     * 发布者id
     */
    var userId: Int? = null,
    /**
     * 标题
     */
    var title: String? = null,
    /**
     * 内容
     */
    var content: String? = null,
    /**
     * 创建时间
     */
    var createTime: Date? = null,
    /**
     * 状态 draft：草稿，normal：发布，timing：定时发布
     */
    var state: String? = null,
    /**
     * 定时发布时间
     */
    var releaseTime: Date? = null,
    /**
     * 帖子类型
     * buy：买
     * sell:卖
     * confess：表白
     * game: 游戏
     * other：其他
     */
    var type: String? = null,)
