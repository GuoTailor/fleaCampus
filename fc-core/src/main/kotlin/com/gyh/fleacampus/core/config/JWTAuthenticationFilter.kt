package com.gyh.fleacampus.core.config

import com.gyh.fleacampus.common.JwtUtil
import com.gyh.fleacampus.core.model.User
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.util.ObjectUtils
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by gyh on 2019/8/18.
 */
class JWTAuthenticationFilter(authenticationManager: AuthenticationManager) :
    BasicAuthenticationFilter(authenticationManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        response.contentType = "application/json;charset=utf-8"
        if (!ObjectUtils.isEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
            val authToken = authHeader.replaceFirst("Bearer ", "")
            val user = JwtUtil.parseToken(authToken, User())
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            logger.info("authenticated user " + user.username + ", setting security context")
            SecurityContextHolder.getContext().authentication = authentication
        }
        chain.doFilter(request, response)
    }
}