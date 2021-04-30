package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Post

/**
 * @Entity com.gyh.fleacampus.model.FcPost
 */
interface PostMapper {
    fun deleteByPrimaryKey(id: Long?): Int
    fun insert(record: Post?): Int
    fun insertSelective(record: Post?): Int
    fun selectByPrimaryKey(id: Long?): Post?
    fun updateByPrimaryKeySelective(record: Post?): Int
    fun updateByPrimaryKey(record: Post?): Int
}