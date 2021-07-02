package com.gyh.fleacampus.core

import com.gyh.fleacampus.core.mapper.PostMapper
import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.service.lucene.Document
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource


/**
 * Created by GYH on 2021/4/27
 */
@SpringBootTest
class TFTest {
    @Resource
    lateinit var postMapper: PostMapper
    @Autowired
    lateinit var document: Document

    @Test
    fun nmka() {
        document.createIndex(Post(id = 1, title = "我爱总共", content = "中国是我家"))
        document.createIndex(Post(id = 1, title = "我爱总共", content = "中国是我家"))
        document.createIndex(Post(id = 3, title = "我爱中国", content = "总共是我家"))
        document.createIndex(Post(id = 4, title = "我爱中国2", content = "中国是我家2"))
        println(document.searchText("中国"))
        println(document.searchText("3"))
    }
}