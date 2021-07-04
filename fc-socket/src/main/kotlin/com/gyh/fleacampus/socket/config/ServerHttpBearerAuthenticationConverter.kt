package com.gyh.fleacampus.socket.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.gyh.fleacampus.common.JwtUtil
import com.gyh.fleacampus.socket.entity.User
import org.slf4j.LoggerFactory
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.util.StringUtils
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import java.util.HashMap
import java.util.regex.Pattern

/**
 * Created by gyh on 2020/3/16.
 */

class ServerHttpBearerAuthenticationConverter : ServerAuthenticationConverter {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val skipAuthUrls = listOf(Pattern.compile(".*index\\.html"), Pattern.compile(".*app\\.js"))
    private val json = jacksonObjectMapper()

    fun match(input: CharSequence): Boolean {
        for (skipAuthUrl in skipAuthUrls) {
            if (skipAuthUrl.matcher(input).matches()) {
                return true
            }
        }
        return false
    }

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        val request: ServerHttpRequest = exchange.request
        return if (!match(request.path.value())) {
            Mono.justOrEmpty(exchange).map {
                val token = getQueryMap(request.uri.query)["bearer"]
                val user = JwtUtil.parseToken(token, User())
                logger.info("authenticated user " + user.getUsername() + ", setting security context")
                UsernamePasswordAuthenticationToken(user, null, user.getRoles()?.map { GrantedAuthority { it } })
            }
        } else Mono.empty()
    }

    private fun getQueryMap(queryStr: String): Map<String, String> {
        val queryMap: MutableMap<String, String> = HashMap()
        if (StringUtils.hasLength(queryStr)) {
            val queryParam = queryStr.split("&")
            queryParam.forEach { s: String ->
                val kv = s.split("=".toRegex(), 2)
                val value = if (kv.size == 2) kv[1] else ""
                queryMap[kv[0]] = value
            }
        }
        return queryMap
    }
}
