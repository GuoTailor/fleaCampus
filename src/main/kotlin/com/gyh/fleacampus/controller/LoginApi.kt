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
    @Operation(summary = "登录")
    @PostMapping("/login1", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun login(
        @Parameter(description = "用户名", required = true) username: String,
        @Parameter(description = "密码", required = true) password: String
    ) {
    }
}