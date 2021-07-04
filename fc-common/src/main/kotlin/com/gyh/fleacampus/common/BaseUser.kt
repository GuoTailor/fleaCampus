package com.gyh.fleacampus.common

/**
 * Created by gyh on 2021/7/3
 */
interface BaseUser {
    fun getId(): Int?

    fun setId(id: Int)

    fun getUsername(): String?

    fun setUsername(username: String)

    fun setRoles(roles: Collection<String>)

    fun getRoles(): Collection<String>?
}