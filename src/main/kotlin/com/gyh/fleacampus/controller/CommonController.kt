package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.User
import com.gyh.fleacampus.service.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by gyh on 2021/2/4
 */
@Api("通用")
@RestController
@RequestMapping("/common")
class CommonController(val userService: UserService) {
    @ApiOperation(value = "test")
    @GetMapping
    fun test(): ResponseInfo<String> {
        return ResponseInfo.ok()
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    fun register(user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}