package com.gyh.fleacampus.socket.entity

/**
 * Created by gyh on 2021/3/31
 */
data class RoomMessage(
    val id: Int,
    val userId: Int,
    val roomId: Int,
    val message: String
)