package com.gyh.fleacampus.socket.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/1/8
 */
@Table("sc_room")
data class Room(@Id val id: Int?, val name: String, val description: String, val createTime: LocalDateTime?)