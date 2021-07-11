package com.gyh.fleacampus.core.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.gyh.fleacampus.common.getJavaTimeModule
import com.gyh.fleacampus.core.model.User
import org.springframework.security.core.context.SecurityContextHolder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Created by gyh on 2021/2/4
 */

fun getCurrentUser() = SecurityContextHolder.getContext().authentication.principal as User

fun LocalDateTime.toEpochMilli(): Long = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())

val json: ObjectMapper = jacksonObjectMapper().registerModule(getJavaTimeModule())