package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Reply
import com.gyh.fleacampus.core.model.view.response.ReplyResponse

interface ReplyMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun deleteByPostId(id: Int): Int
    fun insert(record: Reply): Int
    fun insertSelective(record: Reply): Int
    fun selectByPrimaryKey(id: Int): Reply?
    fun updateByPrimaryKeySelective(record: Reply): Int
    fun updateByPrimaryKey(record: Reply): Int
    fun findTopThree(id: Int, userId: Int): List<ReplyResponse>
    fun findByPage(id: Int, userId: Int): List<ReplyResponse>
}