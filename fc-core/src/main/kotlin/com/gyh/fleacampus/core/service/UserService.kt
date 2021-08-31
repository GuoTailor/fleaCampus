package com.gyh.fleacampus.core.service

import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.UserMapper
import com.gyh.fleacampus.core.model.Role
import com.gyh.fleacampus.core.model.User
import com.gyh.fleacampus.core.model.view.request.UnifyLoginRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
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

    fun login(principal: UnifyLoginRequest): User {
        val user = User()
        if (principal.type == UnifyLoginRequest.PHONE) {
            val oldUser = userMapper.selectByPhone(principal.phone!!)
            if (oldUser == null) {
                user.setUsername(UUID.randomUUID().toString())
                user.phone = principal.phone
                user.sex = 0
            } else {

            }
        } else if (principal.type == UnifyLoginRequest.WX) {
            user.unionid = principal.unionid
            user.setUsername(principal.nickname ?: UUID.randomUUID().toString())
            user.sex = principal.sex?.toShort()
            user.headimgurl = principal.headimgurl
        }
        userMapper.insertSelective(user)
        return user
    }

    /**
     * 注册用户，并添加默认角色[Role.USER]
     * @param user user
     * @return user
     */
    fun register(user: User): User {
        // TODO 用户名判重
        user.password?.let { user.setPassword(passwordEncoder.encode(it)) }
        userMapper.insertSelective(user)
        roleService.addRoleToUser(user.id!!, Role.USER)
        user.setPassword("")
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