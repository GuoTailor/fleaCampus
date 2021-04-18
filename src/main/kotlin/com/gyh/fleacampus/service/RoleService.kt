package com.gyh.fleacampus.service

import com.gyh.fleacampus.mapper.RoleMapper
import com.gyh.fleacampus.model.Role
import com.gyh.fleacampus.model.UserRole
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/7
 */
@Service
class RoleService {
    @Resource
    var roleMapper: RoleMapper? = null
    private val roles: MutableSet<Role> = HashSet()

    /**
     * 获取所有角色
     *
     * @return 角色集合
     */
    fun getRoles(): Set<Role> {
        if (roles.isEmpty()) {
            synchronized(RoleService::class.java) {
                if (roles.isEmpty()) {
                    roles.addAll(roleMapper!!.findAll())
                }
            }
        }
        return roles
    }

    fun getRoleIdByName(roleName: String): Int? {
        return getRoles().stream()
            .filter { r: Role -> r.name.equals(roleName) }
            .findFirst()
            .orElseThrow { IllegalStateException("不存在该角色名:$roleName") }
            .id
    }

    /**
     * 给用户添加角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    fun addRoleToUser(userId: Int, roleName: String, unitId: Int?): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper!!.insert(userId, roleId!!, unitId!!)
    }

    fun updateRoleById(roleName: String, id: Int?, unitId: Int?): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper!!.updateRoleById(id!!, roleId!!, unitId!!)
    }

    fun updateRoleByUserIdAndUnitId(roleName: String, userId: Int?, unitId: Int?): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper!!.updateRoleByUserIdAndUnitId(userId!!, roleId!!, unitId!!)
    }

    /**
     * 给用户移除角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    fun removeRoleToUser(userId: Int, roleName: String, unitId: Int?): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper!!.removeRoleToUser(userId, roleId!!, unitId!!)
    }

    fun findByUserIdAndRoleId(userId: Int?, roleName: String): List<UserRole> {
        val userRoleId = getRoleIdByName(roleName)
        val userRoles = roleMapper!!.findByUserIdAndRoleId(userId!!, userRoleId!!)
        return userRoles ?: emptyList()
    }
}