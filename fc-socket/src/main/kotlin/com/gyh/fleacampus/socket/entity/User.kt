package com.gyh.fleacampus.socket.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/6
 */
@Table("sc_user")
data class User(@Id var id: Int?, var username: String, var password: String, var avatarUrl: String?, var createTime: LocalDateTime?)

