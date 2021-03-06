package com.gyh.fleacampus.core.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.gyh.fleacampus.core.model.ResponseInfo
import com.gyh.fleacampus.core.model.Role
import com.gyh.fleacampus.core.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsUtils
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest

/**
 * Created by gyh on 2021/2/3
 */
@EnableWebSecurity
@ControllerAdvice
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)
    private val objectMapper = ObjectMapper()
    @Autowired
    lateinit var userDetailsService: UserService
    @Autowired
    lateinit var pw: PasswordEncoder

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            .antMatchers(
                "/common/**", "/login",
                "/swagger-ui/*", "/swagger-resources/**", "/v3/api-docs/**", "/webjars/**",
                "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ico", "/static/**",
                "/post/all", "/post", "/get/accesstoken", "/deal/all", "/deal", "/imgs/**",
            ).permitAll()
            .antMatchers(HttpMethod.GET, "/post").permitAll()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .anyRequest().authenticated().and()
            .exceptionHandling()
            .authenticationEntryPoint { _, response, ex ->
                response.contentType = "application/json;charset=utf-8"
                if (ex is InsufficientAuthenticationException) {
                    response.writer.write(objectMapper.writeValueAsString(ResponseInfo(ResponseInfo.ACCESS_TOKEN_EXPIRES,ex.message, null)))
                } else {
                    response.writer.write(objectMapper.writeValueAsString(ResponseInfo.failed<String>(ex.message)))
                }
            }.and()
            .addFilterBefore(WxLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter::class.java)
            .addFilter(JWTAuthenticationFilter(authenticationManager()))
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val wxIdAuthenticationProvider = WxIdAuthenticationProvider()
        wxIdAuthenticationProvider.userDetailsService = userDetailsService
        auth.authenticationProvider(wxIdAuthenticationProvider)
    }

    /**
     * ????????????
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowCredentials = true
        configuration.addAllowedOriginPattern("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    /**
     * ????????????????????????
     *
     * @return RoleHierarchy
     */
    @Bean
    fun roleHierarchy(): RoleHierarchy {
        val hierarchy = RoleHierarchyImpl()
        hierarchy.setHierarchy(Role.SUPER_ADMIN + " > " + Role.ADMIN + " > " + Role.USER)
        return hierarchy
    }

    @ExceptionHandler(value = [Exception::class])
    @ResponseBody
    fun exceptionHandler(e: Exception, request: HttpServletRequest): ResponseInfo<String> {
        logger.warn("????????????: { " + e.message + " } uri: " + request.method + ":" + request.requestURI)
        if (e !is IllegalStateException) {
            e.printStackTrace()
        }
        return ResponseInfo.failed(e.message)
    }
}