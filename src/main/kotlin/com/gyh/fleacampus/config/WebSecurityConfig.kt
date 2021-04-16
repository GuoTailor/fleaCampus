package com.gyh.fleacampus.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.Role
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by gyh on 2021/2/3
 */
@EnableWebSecurity
@ControllerAdvice
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    private val logger = LoggerFactory.getLogger(this.javaClass.simpleName)
    private val objectMapper = ObjectMapper()
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        val successFailureHandler = AppAuthenticationSuccessFailureHandler()
        httpSecurity
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
            .antMatchers("/login", "/common/**").permitAll()
            .antMatchers(
                "/swagger-ui.html",
                "/swagger-ui/*",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/v3/api-docs",
                "/webjars/**"
            ).permitAll()
            .antMatchers("/**/*.html", "/**/*.js", "/**/*.css", "/**/*.png", "/**/*.ico", "/static/**").permitAll()
            .anyRequest().authenticated().and()
            .exceptionHandling()
            .authenticationEntryPoint { request: HttpServletRequest?, response: HttpServletResponse, ex: AuthenticationException ->
                response.writer.write(
                    objectMapper.writeValueAsString(
                        ResponseInfo.failed<String>(ex.message)
                    )
                )
            }
            .and()
            .formLogin()
            .successHandler(successFailureHandler)
            .failureHandler(successFailureHandler)
            .and().addFilter(JWTAuthenticationFilter(authenticationManager()))
    }

    /**
     * 解决跨域
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
     * 添加角色继承关系
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
        logger.warn("调用出错: { " + e.message + " } uri: " + request.method + ":" + request.requestURI)
        if (e !is IllegalStateException) {
            e.printStackTrace()
        }
        return ResponseInfo.failed(e.message)
    }
}