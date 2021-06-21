package com.gyh.fleacampus.socket.mapper

import com.gyh.fleacampus.socket.entity.Room
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

/**
 * Created by gyh on 2021/1/8
 */
interface RoomDao: CoroutineCrudRepository<Room, Int> {

    suspend fun findByNameLike(name: String): Flow<Room>

}