package com.gyh.fleacampus.core.service

import com.gyh.fleacampus.core.mapper.PostMapper
import com.gyh.fleacampus.core.mapper.PostMapperInterface
import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.model.view.response.PostResponse
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/6
 */
@Service
class PostService: PostServiceAbstract<Post, PostResponse>() {
    @Resource
    lateinit var postMapper: PostMapper

    override fun getMapper(): PostMapperInterface<Post, PostResponse> {
        return postMapper
    }
}
