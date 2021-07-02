package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.model.Like

interface LikeMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Like): Int
    fun insertSelective(record: Like): Int
    fun selectByPrimaryKey(id: Int): Like?
    fun updateByPrimaryKeySelective(record: Like): Int
    fun updateByPrimaryKey(record: Like): Int
}