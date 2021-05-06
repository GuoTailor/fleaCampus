package com.gyh.fleacampus.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.stream.Collectors

/**
 *
 * @TableName fc_user
 */
data class FcUser(
    /**
     *
     */
    var id: Int? = null,
    /**
     * 用户名
     */
    private var username: String? = null,
    private var password: String? = null,
    /**
     * 角色
     */
    private var roles: Set<Role>? = null,
    /**
     * 个性签名
     */
    var signature: String? = null,
    /**
     * 头像url地址
     */
    var photo: String? = null,
    /**
     * 手机
     */
    var phone: String? = null,
    /**
     * 性别：0：未知，1：男，2：女
     */
    var sex: Short? = null,
    /**
     * 经验
     */
    var exp: Int? = null,
    /**
     * 积分
     */
    var score: Int? = null,
    /**
     * 星座
     */
    var horoscope: String? = null,
    /**
     * 学校区域id
     */
    var schoolAreaId: Int? = null,
    /**
     * 年纪
     */
    var grade: String? = null,
    /**
     * 专业
     */
    var specialty: String? = null,
    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null,
) : UserDetails {

    @JsonIgnore
    fun getRoles(): Set<String> {
        return (roles ?: emptySet()).stream()
            .map { obj: Role -> obj.name!! }
            .collect(Collectors.toSet())
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return roles ?: emptyList()
    }

    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun setRoles(roles: Collection<String>) {
        this.roles = roles.stream()
            .map { name: String -> Role(name) }
            .collect(Collectors.toSet())
    }

    override fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
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
