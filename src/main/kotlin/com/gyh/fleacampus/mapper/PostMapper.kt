package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Post

/**
 * @Entity com.gyh.fleacampus.model.FcPost
 */
interface PostMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Post): Int
    fun insertSelective(record: Post): Int
    fun findAll(): List<Post>
    fun selectByPrimaryKey(id: Int): Post?
    fun updateByPrimaryKeySelective(record: Post): Int
    fun updateByPrimaryKey(record: Post): Int
}