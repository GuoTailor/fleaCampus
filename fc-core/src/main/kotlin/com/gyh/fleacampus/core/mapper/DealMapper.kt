package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Deal
import com.gyh.fleacampus.core.model.view.response.DealResponse

interface DealMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: Deal): Int
    fun insertSelective(record: Deal): Int
    fun selectByPrimaryKey(id: Int): DealResponse?
    fun updateByPrimaryKeySelective(record: Deal): Int
    fun updateByPrimaryKey(record: Deal): Int

    fun findAll(): List<DealResponse>
    fun incrComments(id: Int): Int
    fun incrBrowses(id: Int): Int
    fun incrLikes(id: Int): Int
    fun incrCollects(id: Int): Int
    fun decrCollects(id: Int): Int
    fun decrComments(id: Int): Int
    fun minusComments(id: Int, dealId: Int): Int
}