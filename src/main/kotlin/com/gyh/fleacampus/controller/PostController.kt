package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.Post
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by GYH on 2021/5/10
 */
@Tag(name = "帖子")
@RestController
@RequestMapping("/post")
class PostController {
    @Autowired
    lateinit var postService: PostService

    @Operation(summary = "创建帖子", security = [SecurityRequirement(name = "basicScheme")])
    @PostMapping
    fun createPost(@RequestBody post: Post): ResponseInfo<Int> {
        return ResponseInfo.ok(postService.createPost(post))
    }
}