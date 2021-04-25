package com.gyh.fleacampus.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.UserMapper
import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Role
import com.gyh.fleacampus.model.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by gyh on 2021/2/3
 */
@Service
class UserService(val passwordEncoder: PasswordEncoder, val roleService: RoleService) : UserDetailsService {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)

    @Resource
    lateinit var userMapper: UserMapper

    override fun loadUserByUsername(s: String): UserDetails {
        return userMapper.loadUserByUsername(s)
            ?: throw UsernameNotFoundException("用户：" + s + "不存在")
    }

    fun findUserById(id: Int?): User? {
        return userMapper.findUserRoleById(id ?: getCurrentUser().id!!)
    }

    fun getAllUser(page: Int, offset: Int): PageView<User> {
        PageHelper.startPage<Any>(page, offset)
        return PageView.build<User, List<User>>(userMapper.findAll())
    }

    fun deleteUserById(id: Int): Int {
        check(id != 1) { "不能删除超级管理员" }
        return userMapper.deleteUserById(id)
    }

    /**
     * 注册用户，并添加默认角色[Role.ADMIN]
     * @param user user
     * @return user
     */
    fun register(user: User): User {
        userMapper.save(user)
        roleService.addRoleToUser(user.id!!, Role.USER, null)
        return user
    }

    fun update(user: User): Int {
        val id: Int = getCurrentUser().id!!
        // 如果修改的用户是自己或者自己是超级管理员就允许修改
        return if (id == user.id || id == 1) {
            userMapper.update(user)
        } else throw IllegalStateException("不能修改")
    }

}