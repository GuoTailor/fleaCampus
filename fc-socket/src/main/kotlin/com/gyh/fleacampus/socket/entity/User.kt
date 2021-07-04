package com.gyh.fleacampus.socket.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.gyh.fleacampus.common.BaseUser
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.stream.Collectors

/**
 * Created by gyh on 2021/1/6
 */
@Table("fc_user")
data class User(
    @Id private var id: Int? = null,
    private var username: String? = null,
    private var roles: Collection<String>? = null,
    var photo: String? = null,
    var createTime: LocalDateTime? = null
) : BaseUser {
    override fun getId() = id

    override fun setId(id: Int) {
        this.id = id
    }

    @JsonIgnore
    override fun getRoles() = roles

    override fun setRoles(roles: Collection<String>) {
        this.roles = roles
    }

    override fun getUsername() = username

    override fun setUsername(username: String) {
        this.username = username
    }
}

