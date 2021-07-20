package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Area
import com.gyh.fleacampus.core.model.view.request.AreaRequest

interface AreaMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Area): Int
    fun insertSelective(record: Area): Int
    fun selectByPrimaryKey(id: Int): Area?
    fun contains(area: AreaRequest): List<Area>
    fun updateByPrimaryKeySelective(record: Area): Int
    fun updateByPrimaryKey(record: Area): Int
}