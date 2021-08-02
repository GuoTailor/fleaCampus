package com.gyh.fleacampus.core.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.CommentMapper
import com.gyh.fleacampus.core.mapper.LikeMapper
import com.gyh.fleacampus.core.mapper.PostMapper
import com.gyh.fleacampus.core.mapper.ReplyMapper
import com.gyh.fleacampus.core.model.Comment
import com.gyh.fleacampus.core.model.Like
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.Reply
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
    lateinit var likeMapper: LikeMapper

    fun addComment(comment: Comment): Comment {
        comment.postId ?: error("帖子id不能为空")
        val userId = getCurrentUser().id
        comment.userId = userId
        comment.replys = 0
        comment.likes = 0
        comment.topOrder = 0
        comment.flag = Comment.SHOW
        commentMapper.insertSelective(comment)
        // TODO 无法给二手添加评论
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
        // TODO 无法给二手添加评论
        postMapper.incrComments(reply.postId!!)
        commentMapper.incrReplys(reply.commentId!!)
        return reply
    }

    fun updateFlag(id: Int, flag: Int): Int {
        return commentMapper.updateByPrimaryKeySelective(Comment(id = id, flag = flag))
    }

    fun deleteComment(id: Int, postId: Int): Int {
        postMapper.minusComments(id, postId)
        val comment = Comment(id = id, postId = postId, flag = Comment.DELETE)
        // TODO 删除详细继续实现
        TODO("删除详细继续实现")
        return commentMapper.deleteByPrimaryKey(id)
    }

    fun deleteReply(id: Int, postId: Int): Int {
        postMapper.decrComments(postId)
        commentMapper.decrReplys(id)
        return replyMapper.deleteByPrimaryKey(id)
    }

    @Transactional
    fun like(id: Int) {
        val userId = getCurrentUser().id
        val like = Like(userId = userId, postId = id)
        val oldLike = likeMapper.findSelectiveForUpdate(like)
        // 如果还没有点过赞
        if (oldLike == null) {
            like.status = Like.VALID
            likeMapper.insertSelective(like)
            getMapper().incrLikes(id)
        } else if (oldLike.status == Like.INVALID) {    // 如果点过赞，但是取消了
            oldLike.status = Like.VALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            getMapper().incrLikes(id)
        } else if (oldLike.status == Like.VALID) {      // 如果点过赞，也没有取消，就取消掉
            oldLike.status = Like.INVALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            getMapper().decrLikes(id)
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
