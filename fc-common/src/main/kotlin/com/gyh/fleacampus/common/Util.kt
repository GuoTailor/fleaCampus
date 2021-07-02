package com.gyh.fleacampus.common

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.Future

/**
 * Created by gyh on 2021/2/4
 */

fun async(task: () -> Unit) = ThreadManager.getInstance().execute(task)

fun <T> asyncResult(task: () -> T): Future<T> = ThreadManager.getInstance().submit(task)

fun LocalDateTime.toEpochMilli(): Long = this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDateTime(): LocalDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneId.systemDefault())