package com.gyh.fleacampus.core.config

import com.gyh.fleacampus.core.model.User
import com.gyh.fleacampus.core.model.view.request.UnifyLoginRequest
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.util.Assert

/**
 * Created by GYH on 2021/5/7
 */
class WxIdAuthenticationToken : AbstractAuthenticationToken {
    private val principal: UnifyLoginRequest
    var user: User?

    constructor(principal: UnifyLoginRequest) : super(null) {
        this.principal = principal
        isAuthenticated = false
        user = null
    }

    constructor(principal: UnifyLoginRequest, user: User) : super(user.authorities) {
        this.principal = principal
        super.setAuthenticated(true) // must use super, as we override
        this.user = user
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