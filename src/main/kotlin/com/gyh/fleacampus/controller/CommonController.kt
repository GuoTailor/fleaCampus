package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.User
import com.gyh.fleacampus.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by gyh on 2021/2/4
 */
@Tag(name = "通用")
@RestController
@RequestMapping("/common")
class CommonController(val userService: UserService) {
    @Operation(summary  = "test")
    @GetMapping
    fun test(): ResponseInfo<String> {
        return ResponseInfo.ok()
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    fun register(user: User?): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}