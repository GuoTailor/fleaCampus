package com.gyh.fleacampus.socket.mapper

import com.gyh.fleacampus.socket.entity.User
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import reactor.core.publisher.Mono

/**
 * Created by GYH on 2021/7/5
 */
interface UserDao : ReactiveSortingRepository<User, Int> {
    fun findUserById(id: Int): Mono<User>
}
