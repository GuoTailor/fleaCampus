package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Reply
import com.gyh.fleacampus.model.view.ReplyResponse

interface ReplyMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Reply): Int
    fun insertSelective(record: Reply): Int
    fun selectByPrimaryKey(id: Int): Reply?
    fun updateByPrimaryKeySelective(record: Reply): Int
    fun updateByPrimaryKey(record: Reply): Int
    fun findTopThree(id: Int): List<ReplyResponse>
    fun findByPage(id: Int): List<ReplyResponse>
}