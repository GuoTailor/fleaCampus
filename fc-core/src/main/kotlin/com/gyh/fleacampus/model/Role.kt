package com.gyh.fleacampus.model

import org.springframework.security.core.GrantedAuthority
import java.util.*

/**
 * Created by gyh on 2021/2/4
 */
class Role(name: String) : GrantedAuthority {
    var id: Int? = null
    var name: String? = name
    var nameZh: String? = null

    override fun getAuthority(): String {
        return name!!
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val role = other as Role
        return name == role.name
    }

    override fun hashCode(): Int {
        return Objects.hash(name)
    }

    companion object {
        // 超级管理员
        const val SUPER_ADMIN = "ROLE_SUPER_ADMIN"

        // 管理员
        const val ADMIN = "ROLE_ADMIN"

        // 用户
        const val USER = "ROLE_USER"
    }
}