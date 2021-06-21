package com.gyh.fleacampus.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by GYH on 2021/5/10
 */
@Tag(name = "通用")
@RestController
class LoginApi {
    @Operation(summary = "登录",description = "用户名密码，当使用用户名密码登录的时候该字段必传。微信Id，当使用微信登录的时候该字段必传")
    @PostMapping("/login", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun login(
        @Parameter(description = "用户名") username: String,
        @Parameter(description = "密码") password: String,
        @Parameter(description = "微信id") wxId: String,
    ) {
    }
}