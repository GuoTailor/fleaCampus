package com.gyh.fleacampus.service

import com.gyh.fleacampus.mapper.CommentMapper
import com.gyh.fleacampus.model.Comment
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/12
 */
@Service
class CommentService {
    @Resource
    lateinit var commentMapper: CommentMapper

    fun addComment(comment: Comment): Int {
        return commentMapper.insertSelective(comment)
    }

    fun updateFlag(id: Int, flag: Int): Int {
        return commentMapper.updateByPrimaryKeySelective(Comment(id = id, flag = flag))
    }

    fun deleteComment(id: Int): Int {
        return commentMapper.deleteByPrimaryKey(id)
    }
}