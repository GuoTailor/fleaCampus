package com.gyh.fleacampus

import com.gyh.fleacampus.common.toEpochMilli
import com.gyh.fleacampus.model.Post
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.ZoneOffset

/**
 * Created by GYH on 2021/4/27
 */
class TFTest {
    @Test
    fun nmka() {
        val post = Post()
        post.state = "dsasdUIO"
        println(post.state)
        println(LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond())
        println(LocalDateTime.now().toEpochMilli())
    }
}