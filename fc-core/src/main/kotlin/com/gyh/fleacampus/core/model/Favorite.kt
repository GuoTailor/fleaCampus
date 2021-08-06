package com.gyh.fleacampus.core.model

import java.util.*

/**
 * fc_favorite
 * @author
 */
class Favorite(
    var id: Int? = null,

    /**
     * 帖子id
     */
    var postId: Int? = null,

    /**
     * 帖子:POST,二手:DEAL
     */
    var type: String? = null,

    /**
     * 用户id
     */
    var userId: Int? = null,

    /**
     * 创建时间
     */
    var createTime: Date? = null,

    )