package com.gyh.fleacampus.core.service

import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.UserMapper
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


    /**
     * 注册用户，并添加默认角色[Role.USER]
     * @param user user
     * @return user
     */
    fun register(user: User): User {
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userMapper.insertSelective(user)
        roleService.addRoleToUser(user.id!!, Role.USER)
        return user
    }

    fun update(user: User): Int {
        val id: Int = getCurrentUser().id!!
        // 如果修改的用户是自己或者自己是超级管理员就允许修改
        return if (id == user.id || id == 1) {
            user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
            userMapper.updateByPrimaryKeySelective(user)
        } else throw IllegalStateException("不能修改")
    }

}