package com.gyh.fleacampus.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.gyh.fleacampus.common.JwtUtil
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * APP环境下认证成功处理器
 *
 * @author gyh
 */
class AppAuthenticationSuccessFailureHandler : AuthenticationSuccessHandler, AuthenticationFailureHandler {
    private val json = ObjectMapper()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val user = authentication as UsernamePasswordAuthenticationToken
        val token = JwtUtil.generateToken(user.principal as User)
        response.contentType = "application/json;charset=utf-8"
        response.writer.write(json.writeValueAsString(ResponseInfo.ok("成功", token)))
    }

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException
    ) {
        response.contentType = "application/json;charset=utf-8"
        response.writer.write(json.writeValueAsString(ResponseInfo.failed<String>("用户名或密码错误")))
    }
}