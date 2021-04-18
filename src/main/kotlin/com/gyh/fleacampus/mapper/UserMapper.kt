package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.User

interface UserMapper {
    fun loadUserByUsername(username: String): User?
    fun findUserRoleById(id: Int): User?
    fun findUserById(id: Int): User?
    fun findAll(): List<User>
    fun save(user: User): Int
    fun deleteUserById(id: Int): Int
    fun update(user: User): Int
}