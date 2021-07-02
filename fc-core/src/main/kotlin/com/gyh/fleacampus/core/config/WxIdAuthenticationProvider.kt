package com.gyh.fleacampus.core.config

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication

/**
 * Created by GYH on 2021/5/7
 */
class WxIdAuthenticationProvider : AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        TODO("Not yet implemented")
    }

    override fun supports(authentication: Class<*>) =
        WxIdAuthenticationToken::class.java.isAssignableFrom(authentication)

}