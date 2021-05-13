package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Post
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.view.PostResponse
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

    @Operation(summary = "创建帖子", security = [SecurityRequirement(name = "basicScheme")])
    @PostMapping
    fun createPost(@RequestBody post: Post): ResponseInfo<Int> {
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
        @Parameter(description = "第几页，默认从1开始") @RequestParam pageNum: Int = 1,
        @Parameter(description = "每页数量，默认30") @RequestParam pageSize: Int = 30
    ): ResponseInfo<PageView<PostResponse>> {
        return ResponseInfo.ok(postService.findByPage(pageNum, pageSize))
    }

    @Operation(summary = "更新铁子", security = [SecurityRequirement(name = "basicScheme")])
    @PutMapping
    fun updatePost(@RequestBody post: Post): ResponseInfo<Int> {
        return ResponseInfo.ok(postService.updatePost(post))
    }

    @Operation(summary = "删除帖子", security = [SecurityRequirement(name = "basicScheme")])
    @DeleteMapping
    fun deletePost(@Parameter(description = "帖子id", required = true) @RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(postService.deletePost(id))
    }
}