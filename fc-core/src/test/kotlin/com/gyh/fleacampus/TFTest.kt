package com.gyh.fleacampus

import com.gyh.fleacampus.common.ThreadManager
import com.gyh.fleacampus.mapper.PostMapper
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource


/**
 * Created by GYH on 2021/4/27
 */
@SpringBootTest
class TFTest {
    @Resource
    lateinit var postMapper: PostMapper

    @Test
    fun nmka() {
        for (i in 1..10000) {
            ThreadManager.getInstance().execute { postMapper.incrComments(2) }
        }
        Thread.sleep(10_000)
    }
}