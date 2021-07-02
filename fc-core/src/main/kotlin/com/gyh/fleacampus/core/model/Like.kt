package com.gyh.fleacampus.core.model

/**
 * fc_like
 * @author
 */
class Like(
    var id: Int? = null,

    /**
     * 用户id
     */
    var userId: Int? = null,

    /**
     * 帖子id
     */
    var postId: Int? = null,

    /**
     * 评论id
     */
    var commentId: Int? = null,

    /**
     * 回复id
     */
    var replyId: Int? = null,

    /**
     * 点赞状态0--取消赞   1--有效赞
     */
    var status: Int? = null,
) {
    companion object {
        const val VALID = 1
        const val INVALID = 0
    }
}