package com.gyh.fleacampus.core.service

import com.gyh.fleacampus.core.mapper.RoleMapper
import com.gyh.fleacampus.core.model.Role
import com.gyh.fleacampus.core.model.UserRole
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/7
 */
@Service
class RoleService {
    @Resource
    lateinit var roleMapper: RoleMapper
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
                    roles.addAll(roleMapper.findAll())
                }
            }
        }
        return roles
    }

    fun getRoleIdByName(roleName: String): Int {
        return getRoles().stream()
            .filter { r: Role -> r.name.equals(roleName) }
            .findFirst()
            .orElseThrow { IllegalStateException("不存在该角色名:$roleName") }
            .id!!
    }

    /**
     * 给用户添加角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    fun addRoleToUser(userId: Int, roleName: String): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper.insert(userId, roleId)
    }

    fun updateRoleById(roleName: String, id: Int): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper.updateRoleById(id, roleId)
    }

    fun updateRoleByUserIdAndUnitId(roleName: String, userId: Int): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper.updateRoleByUserIdAndUnitId(userId, roleId)
    }

    /**
     * 给用户移除角色
     *
     * @param userId   用户id
     * @param roleName 角色id
     * @return 受影响行数
     */
    fun removeRoleToUser(userId: Int, roleName: String): Int {
        val roleId = getRoleIdByName(roleName)
        return roleMapper.removeRoleToUser(userId, roleId)
    }

    fun findByUserIdAndRoleId(userId: Int, roleName: String): List<UserRole> {
        val userRoleId = getRoleIdByName(roleName)
        return roleMapper.findByUserIdAndRoleId(userId, userRoleId)
    }
}