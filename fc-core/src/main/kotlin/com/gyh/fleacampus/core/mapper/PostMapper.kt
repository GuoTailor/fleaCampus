package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.model.view.response.PostResponse

/**
 * @Entity com.gyh.fleacampus.core.model.FcPost
 */
interface PostMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Post): Int
    fun insertSelective(record: Post): Int
    fun findAll(): List<PostResponse>
    fun selectByPrimaryKey(id: Int): PostResponse?
    fun incrComments(id: Int): Int
    fun incrBrowses(id: Int): Int
    fun incrLikes(id: Int): Int
    fun incrCollects(id: Int): Int
    fun decrCollects(id: Int): Int
    fun decrComments(id: Int): Int
    fun minusComments(id: Int, postId: Int): Int
    fun updateByPrimaryKeySelective(record: Post): Int
    fun updateByPrimaryKey(record: Post): Int
}