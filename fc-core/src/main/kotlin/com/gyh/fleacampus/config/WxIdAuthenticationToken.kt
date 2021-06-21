package com.gyh.fleacampus.config

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.util.Assert

/**
 * Created by GYH on 2021/5/7
 */
class WxIdAuthenticationToken : AbstractAuthenticationToken {
    private val principal: String

    constructor(principal: String) : super(null) {
        this.principal = principal
        isAuthenticated = false
    }

    constructor(principal: String, authorities: Collection<GrantedAuthority>) : super(authorities) {
        this.principal = principal
        super.setAuthenticated(true) // must use super, as we override
    }

    override fun getCredentials() = null

    override fun getPrincipal() = principal

    override fun setAuthenticated(isAuthenticated: Boolean) {
        Assert.isTrue(
            !isAuthenticated,
            "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead"
        )
        super.setAuthenticated(false)
    }

}