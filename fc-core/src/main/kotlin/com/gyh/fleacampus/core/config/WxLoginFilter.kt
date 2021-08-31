package com.gyh.fleacampus.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gyh.fleacampus.common.JwtUtil
import com.gyh.fleacampus.common.getJavaTimeModule
import com.gyh.fleacampus.core.model.ResponseInfo
import com.gyh.fleacampus.core.model.view.request.UnifyLoginRequest
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by GYH on 2021/5/7
 */
class WxLoginFilter(private val authManager: AuthenticationManager) :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/login", HttpMethod.POST.name)) {
    private val json: ObjectMapper = jacksonObjectMapper().registerModule(getJavaTimeModule())

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val loginRequest = json.readValue<UnifyLoginRequest>(request.inputStream)
        return authManager.authenticate(WxIdAuthenticationToken(loginRequest))
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        //super.successfulAuthentication(request, response, chain, authResult)
        val user = (authResult as WxIdAuthenticationToken).user!!
        val accessToken = JwtUtil.generateToken(user)
        val expiresTime = System.currentTimeMillis() + JwtUtil.ttlMillis
        val data = mapOf("accessToken" to accessToken, "expiresTime" to expiresTime, "userInfo" to user)
        response.contentType = "application/json;charset=utf-8"
        response.writer.write(json.writeValueAsString(ResponseInfo.ok("成功", data)))
    }

    override fun unsuccessfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        failed: AuthenticationException
    ) {
        SecurityContextHolder.clearContext()
        rememberMeServices.loginFail(request, response)
        response.contentType = "application/json;charset=utf-8"
        response.writer.write(json.writeValueAsString(ResponseInfo.failed<String>("登录失败")))
    }
}