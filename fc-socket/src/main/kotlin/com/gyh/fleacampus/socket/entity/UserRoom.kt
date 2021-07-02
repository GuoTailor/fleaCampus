package com.gyh.fleacampus.socket.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

/**
 * Created by gyh on 2021/1/8
 */
@Table("sc_user_room")
data class UserRoom(@Id var id: Int?, val userId: Int, val roomId: Int)