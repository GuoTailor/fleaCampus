package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.User

interface UserMapper {
    fun deleteByPrimaryKey(id: Int): Int
    fun insert(record: User): Int
    fun insertSelective(record: User): Int
    fun selectByPrimaryKey(id: Int): User?
    fun updateByPrimaryKeySelective(record: User): Int
    fun updateByPrimaryKey(record: User): Int
    fun loadUserByUsername(username: String): User?
    fun selectByPhone(phone: String): User?
    fun selectByUnionid(unionid: String): User?
    fun selectByQQOpenId(qqOpenid: String): User?
}