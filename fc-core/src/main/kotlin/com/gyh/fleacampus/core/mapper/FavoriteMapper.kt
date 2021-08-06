package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Favorite

interface FavoriteMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Favorite): Int
    fun insertSelective(record: Favorite): Int
    fun selectByPrimaryKey(id: Int): Favorite?
    fun selectByUserIdAndPostId(postId: Int, userId: Int): Favorite?
    fun updateByPrimaryKeySelective(record: Favorite): Int
    fun updateByPrimaryKey(record: Favorite): Int
}