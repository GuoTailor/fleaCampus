package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.User
import com.gyh.fleacampus.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.*
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * Created by gyh on 2021/2/4
 */
@Tag(name = "通用")
@RestController
@RequestMapping("/common")
class CommonController(val userService: UserService) {
    @Operation(summary = "test", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping
    fun test(): ResponseInfo<User> {
        val user = User()
        user.createTime = LocalDateTime.now()
        return ResponseInfo.ok(user)
    }

    @Operation(summary = "注册")
    @PostMapping("/register")
    fun register(@RequestBody user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}