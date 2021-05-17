package com.gyh.fleacampus.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.CommentMapper
import com.gyh.fleacampus.mapper.ReplyMapper
import com.gyh.fleacampus.model.Comment
import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Reply
import com.gyh.fleacampus.model.view.CommentResponse
import com.gyh.fleacampus.model.view.ReplyResponse
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

    fun addComment(comment: Comment): Comment {
        val userId = getCurrentUser().id
        comment.userId = userId
        comment.replys = 0
        comment.likes = 0
        comment.topOrder = 0
        comment.flag = 1
        commentMapper.insertSelective(comment)
        return comment
    }

    fun addReply(reply: Reply): Reply {
        reply.checkStatus()
        val userId = getCurrentUser().id
        reply.userId = userId
        reply.likes = 0
        reply.flag = 1
        return reply
    }

    fun updateFlag(id: Int, flag: Int): Int {
        return commentMapper.updateByPrimaryKeySelective(Comment(id = id, flag = flag))
    }

    fun deleteComment(id: Int): Int {
        return commentMapper.deleteByPrimaryKey(id)
    }

    fun findCommentByPage(pageNum: Int, pageSize: Int, postId: Int): PageView<CommentResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(commentMapper.findByPage(postId))
    }

    fun findReplyByPage(pageNum: Int, pageSize: Int, commentId: Int): PageView<ReplyResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(replyMapper.findByPage(commentId))
    }
}