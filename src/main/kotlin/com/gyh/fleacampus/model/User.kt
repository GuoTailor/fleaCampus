package com.gyh.fleacampus.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.function.Function
import java.util.stream.Collectors

/**
 * Created by gyh on 2021/2/3
 * @apiDefine User
 * @apiParam {Integer} id 用户id
 * @apiParam {String} telephone 电话
 * @apiParam {String} username 用户名
 * @apiParam {String} name 姓名
 * @apiParam {String} password 密码
 * @apiParam {Date} [createTime] 注册日期
 */
@Schema(name = "用户")
class User : UserDetails {
    @Schema(name = "客户字段分组ID")
    var id: Int? = null
    private var username: String? = null
    private var password: String? = null
    private var roles: Set<Role?>? = null
    var name: String? = null
    var telephone: String? = null
    var createTime: LocalDateTime? = null

    override fun getUsername(): String {
        return username!!
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String?) {
        this.password = password
    }

    @JsonIgnore
    fun getRoles(): Set<String?> {
        return (if (roles == null) emptySet<Role>() else roles)!!.stream()
            .map { obj: Role? -> obj?.name }.collect(Collectors.toSet())
    }

    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return roles!!
    }

    fun setRoles(roles: Collection<String?>?) {
        if (roles != null) {
            this.roles = roles.stream().map { name: String? -> Role(name) }.collect(Collectors.toSet())
        }
    }

    fun setRoles(roles: Set<Role?>?) {
        this.roles = roles
    }

    @JsonIgnore
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    @JsonIgnore
    override fun isEnabled(): Boolean {
        return true
    }
}