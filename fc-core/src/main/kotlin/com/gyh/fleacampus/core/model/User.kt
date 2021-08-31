package com.gyh.fleacampus.core.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gyh.fleacampus.common.BaseUser
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

/**
 *
 * @TableName fc_user
 */
@Schema(description = "用户")
data class User(

    @Schema(description = "id")
    override var id: Int? = null,

    /**
     * 用户名
     */
    @Schema(description = "用户名", required = true)
    private var username: String? = null,

    private var password: String? = null,

    /**
     * 角色
     */
    @Schema(description = "角色")
    private var roles: Set<Role>? = null,

    /**
     * 个性签名
     */
    @Schema(description = "个性签名")
    var signature: String? = null,

    /**
     * 头像url地址
     */
    @Schema(description = "头像url地址")
    var headimgurl: String? = null,

    /**
     * 手机
     */
    @Schema(description = "手机")
    var phone: String? = null,

    /**
     * 性别：0：未知，1：男，2：女
     */
    @Schema(description = "性别：0：未知，1：男，2：女", example = "0")
    var sex: Short? = null,

    /**
     * 经验
     */
    @Schema(description = "经验")
    var exp: Int? = null,

    /**
     * 积分
     */
    @Schema(description = "积分")
    var score: Int? = null,

    /**
     * 星座
     */
    @Schema(description = "星座")
    var horoscope: String? = null,

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    var createTime: Date? = null,

    /**
     * 微信统一标识
     */
    @Schema(description = "微信统一标识")
    var unionid: String? = null,

    /**
     * 真实姓名
     */
    @Schema(description = "真实姓名")
    var realname: String? = null,

    /**
     * qq唯一标识
     */
    @Schema(description = "qq唯一标识")
    var qqOpenid: String? = null,

    /**
     * 生日
     */
    @Schema(description = "生日")
    var birthday: LocalDate? = null,

    /**
     * 感情状态，0：保密，1：单身，2：恋爱中
     */
    @Schema(description = "感情状态，0：保密，1：单身，2：恋爱中", example = "0")
    var affective: String? = null,

    /**
     * 家乡
     */
    @Schema(description = "家乡")
    var hometown: String? = null,

    /**
     * 学校
     */
    @Schema(description = "学校")
    var school: String? = null,) : BaseUser, UserDetails {

    @JsonIgnore
    override fun getRoles(): Set<String> {
        return (roles ?: emptySet()).stream()
            .map { obj: Role -> obj.name!! }
            .collect(Collectors.toSet())
    }

    override fun getAuthorities() = roles ?: emptyList()

    override fun getPassword() = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun setRoles(roles: Collection<String>) {
        this.roles = roles.stream()
            .map { name: String -> Role(name) }
            .collect(Collectors.toSet())
    }

    override fun getUsername() = username

    override fun setUsername(username: String) {
        this.username = username
    }

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    @JsonIgnore
    override fun isEnabled() = true
}
