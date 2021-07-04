package com.gyh.fleacampus.socket.mapper

import com.gyh.fleacampus.socket.entity.Room
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

/**
 * Created by gyh on 2021/1/8
 */
interface RoomDao : CoroutineCrudRepository<Room, Int> {

    @Query("select * from fc_area where geom @> point '(0.5,0.5)'")
    suspend fun findByNameLike(name: String): Flow<Room>

}