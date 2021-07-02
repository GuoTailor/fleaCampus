package com.gyh.fleacampus.core.common

import com.gyh.fleacampus.core.model.User
import org.springframework.security.core.context.SecurityContextHolder
import java.time.*

/**
 * Created by gyh on 2021/2/4
 */

fun getCurrentUser() = SecurityContextHolder.getContext().authentication.principal as User

fun LocalDateTime.toEpochMilli(): Long = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())