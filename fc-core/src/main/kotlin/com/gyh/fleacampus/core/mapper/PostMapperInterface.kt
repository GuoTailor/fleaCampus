package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.model.view.response.PostResponse

/**
 * Created by gyh on 2021/7/12
 */
interface PostMapperInterface<I: Post, O: PostResponse> {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: I): Int
    fun insertSelective(record: I): Int
    fun findAll(): List<O>
    fun selectByPrimaryKey(id: Int): O?
    fun incrComments(id: Int): Int
    fun incrBrowses(id: Int): Int
    fun incrLikes(id: Int): Int
    fun incrCollects(id: Int): Int
    fun decrCollects(id: Int): Int
    fun decrComments(id: Int): Int
    fun minusComments(id: Int, postId: Int): Int
    fun updateByPrimaryKeySelective(record: I): Int
    fun updateByPrimaryKey(record: I): Int
}