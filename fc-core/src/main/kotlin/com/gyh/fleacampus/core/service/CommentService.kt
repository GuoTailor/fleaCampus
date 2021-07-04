package com.gyh.fleacampus.core.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.CommentMapper
import com.gyh.fleacampus.core.mapper.PostMapper
import com.gyh.fleacampus.core.mapper.ReplyMapper
import com.gyh.fleacampus.core.model.Comment
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.Reply
import com.gyh.fleacampus.core.model.view.response.CommentResponse
import com.gyh.fleacampus.core.model.view.response.ReplyResponse
import org.springframework.stereotype.Service
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

    fun addComment(comment: Comment): Comment {
        comment.postId ?: error("帖子id不能为空")
        val userId = getCurrentUser().id
        comment.userId = userId
        comment.replys = 0
        comment.likes = 0
        comment.topOrder = 0
        comment.flag = 1
        commentMapper.insertSelective(comment)
        postMapper.incrComments(comment.postId!!)
        return comment
    }

    fun addReply(reply: Reply): Reply {
        reply.checkStatus()
        val userId = getCurrentUser().id
        reply.userId = userId
        reply.likes = 0
        reply.flag = 1
        replyMapper.insertSelective(reply)
        postMapper.incrComments(reply.postId!!)
        commentMapper.incrReplys(reply.commentId!!)
        return reply
    }

    fun updateFlag(id: Int, flag: Int): Int {
        return commentMapper.updateByPrimaryKeySelective(Comment(id = id, flag = flag))
    }

    fun deleteComment(id: Int, postId: Int): Int {
        postMapper.minusComments(id, postId)
        return commentMapper.deleteByPrimaryKey(id)
    }

    fun deleteReply(id: Int, postId: Int): Int {
        postMapper.decrComments(postId)
        return replyMapper.deleteByPrimaryKey(id)
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
