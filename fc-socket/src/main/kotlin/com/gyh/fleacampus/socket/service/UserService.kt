package com.gyh.fleacampus.socket.service

import com.gyh.fleacampus.socket.common.Util
import com.gyh.fleacampus.socket.entity.Area
import com.gyh.fleacampus.socket.entity.User
import com.gyh.fleacampus.socket.mapper.AreaDao
import com.gyh.fleacampus.socket.mapper.UserDao
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import javax.annotation.Resource

/**
 * Created by gyh on 2021/7/3
 */
@Service
class UserService {
    @Resource
    lateinit var userDao: UserDao

    @Resource
    lateinit var areaDao: AreaDao

    fun loadUser(): Mono<User> {
        return Util.getcurrentUser()
            .flatMap { userDao.findById(it.id!!) }
    }

    fun findPhotoById(): Mono<String> {
        return loadUser()
            .map { it.photo ?: "" }
    }

    fun findArea(id: Int): Mono<Area> {
        return areaDao.findById(id).switchIfEmpty(Mono.error(IllegalAccessException("该用户没有加入任圈子")))
    }

}