package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Comment

interface CommentMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Comment): Int
    fun insertSelective(record: Comment): Int
    fun selectByPrimaryKey(id: Int): Comment?
    fun updateByPrimaryKeySelective(record: Comment): Int
    fun updateByPrimaryKey(record: Comment): Int
}