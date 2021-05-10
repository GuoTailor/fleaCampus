package com.gyh.fleacampus.service

import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.PostMapper
import com.gyh.fleacampus.model.Post
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/6
 */
@Service
class PostService {
    @Resource
    lateinit var postMapper: PostMapper

    fun createPost(post: Post): Int {
        val userId = getCurrentUser().id
        post.checkStatus()
        post.userId = userId
        post.type = post.type?.toLowerCase()
        // TODO 定时发布时间
        return postMapper.insertSelective(post)
    }
}