package com.gyh.fleacampus.core.controller

import com.gyh.fleacampus.common.JwtUtil
import com.gyh.fleacampus.core.model.ResponseInfo
import com.gyh.fleacampus.core.model.User
import com.gyh.fleacampus.core.model.view.request.UnifyLoginRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created by GYH on 2021/5/10
 */
@Tag(name = "通用")
@RestController
class LoginApi {
    @Operation(summary = "登录", description = "用户名密码，当使用用户名密码登录的时候该字段必传。微信Id，当使用微信登录的时候该字段必传")
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: UnifyLoginRequest) {
    }

    @Operation(summary = "刷新toket", description = "传入过期的token，获取新的token，只有在token过期的30天内有效")
    @GetMapping("/get/accesstoken")
    fun refreshToken(@Parameter(description = "refreshToken") refreshToken: String): ResponseInfo<Map<String, Any?>> {
        val user = User()
        val accessToken = JwtUtil.generateRefreshToken(user, refreshToken)
        val expiresTime = System.currentTimeMillis() + JwtUtil.ttlMillis
        return ResponseInfo(
            if (accessToken == null) ResponseInfo.REFRESH_TOKEN_EXPIRES else ResponseInfo.OK_CODE,
            "",
            mapOf("accessToken" to accessToken, "expiresTime" to expiresTime)
        )
    }
}