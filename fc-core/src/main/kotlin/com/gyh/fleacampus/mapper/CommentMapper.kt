package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Comment
import com.gyh.fleacampus.model.view.response.CommentResponse

interface CommentMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Comment): Int
    fun insertSelective(record: Comment): Int
    fun selectByPrimaryKey(id: Int): Comment?
    fun findByPage(postId: Int): List<CommentResponse>
    fun incrReplys(id: Int): Int
    fun updateByPrimaryKeySelective(record: Comment): Int
    fun updateByPrimaryKey(record: Comment): Int
}