package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Comment
import com.gyh.fleacampus.core.model.view.response.CommentResponse

interface CommentMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Comment): Int
    fun insertSelective(record: Comment): Int
    fun selectByPrimaryKey(id: Int, userId: Int): Comment?
    fun findByPage(postId: Int, userId: Int): List<CommentResponse>
    fun incrReplys(id: Int): Int
    fun updateByPrimaryKeySelective(record: Comment): Int
    fun updateByPrimaryKey(record: Comment): Int
}