package com.gyh.fleacampus.core.controller

import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Post
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.view.request.PostRequest
import com.gyh.fleacampus.model.view.response.PostResponse
import com.gyh.fleacampus.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by GYH on 2021/5/10
 */
@Tag(name = "帖子")
@RestController
@RequestMapping("/post")
class PostController {
    @Autowired
    lateinit var postService: PostService

    @Operation(summary = "创建帖子", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun createPost(@RequestBody post: PostRequest): ResponseInfo<Post> {
        return ResponseInfo.ok(postService.createPost(post))
    }

    @Operation(summary = "根据id获取帖子")
    @GetMapping
    fun findById(@Parameter(description = "帖子id", required = true) @RequestParam id: Int): ResponseInfo<PostResponse> {
        return ResponseInfo.ok(postService.findById(id))
    }

    @Operation(summary = "分页获取帖子")
    @GetMapping("/all")
    fun findAll(
        @Parameter(description = "第几页，默认从1开始") @RequestParam(required = false) pageNum: Int?,
        @Parameter(description = "每页数量，默认30") @RequestParam(required = false) pageSize: Int?
    ): ResponseInfo<PageView<PostResponse>> {
        return ResponseInfo.ok(postService.findByPage(pageNum ?: 1, pageSize ?: 30))
    }

    @Operation(summary = "更新铁子", security = [SecurityRequirement(name = "Authorization")])
    @PutMapping
    fun updatePost(@RequestBody post: PostRequest): ResponseInfo<Int> {
        return ResponseInfo.ok(postService.updatePost(post))
    }

    @Operation(summary = "添加一个点赞", security = [SecurityRequirement(name = "Authorization")])
    @PutMapping("/like")
    fun addLike(@Parameter(description = "帖子id", required = true) @RequestParam id: Int): ResponseInfo<Unit> {
        postService.addLike(id)
        return ResponseInfo.ok()
    }

    @Operation(summary = "删除帖子", security = [SecurityRequirement(name = "Authorization")])
    @DeleteMapping
    fun deletePost(@Parameter(description = "帖子id", required = true) @RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(postService.deletePost(id))
    }
}