package com.gyh.fleacampus.core.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.*
import com.gyh.fleacampus.core.model.Comment
import com.gyh.fleacampus.core.model.Like
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.Reply
import com.gyh.fleacampus.core.model.view.request.CommentRequest
import com.gyh.fleacampus.core.model.view.request.ReplyRequest
import com.gyh.fleacampus.core.model.view.response.CommentResponse
import com.gyh.fleacampus.core.model.view.response.ReplyResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/12
 */
@Service
class CommentService {
    @Resource
    lateinit var commentMapper: CommentMapper

    @Resource
    lateinit var replyMapper: ReplyMapper

    @Resource
    lateinit var postMapper: PostMapper

    @Resource
    lateinit var dealMapper: DealMapper

    @Resource
    lateinit var likeMapper: LikeMapper

    fun addComment(comment: CommentRequest): Comment {
        comment.postId ?: error("帖子id不能为空")
        val userId = getCurrentUser().id
        comment.userId = userId
        comment.replys = 0
        comment.likes = 0
        comment.topOrder = 0
        comment.flag = Comment.SHOW
        commentMapper.insertSelective(comment)
        when (comment.type) {
            CommentRequest.CommentType.POST -> postMapper.incrComments(comment.postId!!)
            CommentRequest.CommentType.DEAL -> dealMapper.incrComments(comment.postId!!)
        }
        return comment
    }

    fun addReply(reply: ReplyRequest): Reply {
        reply.checkStatus()
        val userId = getCurrentUser().id
        reply.userId = userId
        reply.likes = 0
        reply.flag = 1
        replyMapper.insertSelective(reply)
        when (reply.type) {
            CommentRequest.CommentType.POST -> postMapper.incrComments(reply.postId!!)
            CommentRequest.CommentType.DEAL -> dealMapper.incrComments(reply.postId!!)
        }
        commentMapper.incrReplys(reply.commentId!!)
        return reply
    }

    fun updateFlag(id: Int, flag: Int): Int {
        return commentMapper.updateByPrimaryKeySelective(Comment(id = id, flag = flag))
    }

    /**
     * 删除评论并外键级联删除回复和点赞
     */
    fun deleteComment(id: Int, postId: Int, type: CommentRequest.CommentType): Int {
        when (type) {
            CommentRequest.CommentType.POST -> postMapper.minusComments(id, postId)
            CommentRequest.CommentType.DEAL -> dealMapper.minusComments(id, postId)
        }
        // 数据库外键级联删除回复和点赞
        return commentMapper.deleteByPrimaryKey(id)
    }

    fun deleteReply(id: Int, postId: Int, type: CommentRequest.CommentType): Int {
        when (type) {
            // TODO 无法准确统计回复数量
            CommentRequest.CommentType.POST -> postMapper.decrComments(postId)
            CommentRequest.CommentType.DEAL -> dealMapper.decrComments(postId)
        }
        commentMapper.decrReplys(id)
        return replyMapper.deleteByPrimaryKey(id)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun like(postId: Int, commentId: Int? = null, replyId: Int? = null) {
        val userId = getCurrentUser().id
        val like = Like(userId = userId, postId = postId, commentId = commentId, replyId = replyId)
        val oldLike = likeMapper.findSelectiveForUpdate(like)
        // 如果还没有点过赞
        if (oldLike == null) {
            like.status = Like.VALID
            likeMapper.insertSelective(like)
            if (replyId != null) {
                replyMapper.incrLikes(replyId)
            } else if (commentId != null) {
                commentMapper.incrLikes(commentId)
            }
        } else if (oldLike.status == Like.INVALID) {    // 如果点过赞，但是取消了
            oldLike.status = Like.VALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            if (replyId != null) {
                replyMapper.incrLikes(replyId)
            } else if (commentId != null) {
                commentMapper.incrLikes(commentId)
            }
        } else if (oldLike.status == Like.VALID) {      // 如果点过赞，也没有取消，就取消掉
            oldLike.status = Like.INVALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            if (replyId != null) {
                replyMapper.decrLikes(replyId)
            } else if (commentId != null) {
                commentMapper.decrLikes(commentId)
            }
        }
    }

    fun findCommentByPage(pageNum: Int, pageSize: Int, postId: Int): PageView<CommentResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(commentMapper.findByPage(postId, getCurrentUser().id!!))
    }

    fun findReplyByPage(pageNum: Int, pageSize: Int, commentId: Int): PageView<ReplyResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(replyMapper.findByPage(commentId, getCurrentUser().id!!))
    }
}
