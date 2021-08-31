package com.gyh.fleacampus.core.config

import com.gyh.fleacampus.core.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

/**
 * Created by GYH on 2021/5/7
 */
class WxIdAuthenticationProvider : AuthenticationProvider {
    var userDetailsService: UserService? = null

    override fun authenticate(authentication: Authentication?): Authentication {
        val unifyLoginRequest = (authentication as WxIdAuthenticationToken).principal
        val user = userDetailsService!!.login(unifyLoginRequest)
        return WxIdAuthenticationToken(unifyLoginRequest, user)
    }

    override fun supports(authentication: Class<*>) =
        WxIdAuthenticationToken::class.java.isAssignableFrom(authentication)

}