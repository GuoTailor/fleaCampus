package com.gyh.fleacampus.mapper

import com.gyh.fleacampus.model.Role
import com.gyh.fleacampus.model.UserRole

/**
 * Created by gyh on 2021/2/4
 */
interface RoleMapper {
    fun findRoleByUserId(userId: Int): List<Role>
    fun insert(userId: Int, roleId: Int): Int
    fun findAll(): List<Role>
    fun findIdByName(name: String): Int
    fun findByUserIdAndRoleId(userId: Int, roleId: Int): List<UserRole>
    fun removeRoleToUser(userId: Int, roleId: Int): Int
    fun deleteById(id: Int): Int
    fun updateRoleById(id: Int, roleId: Int): Int
    fun updateRoleByUserIdAndUnitId(userId: Int, roleId: Int): Int
}