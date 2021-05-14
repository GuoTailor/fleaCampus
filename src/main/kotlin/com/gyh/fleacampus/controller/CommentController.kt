package com.gyh.fleacampus.controller

import com.gyh.fleacampus.model.Comment
import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Reply
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.view.CommentResponse
import com.gyh.fleacampus.model.view.ReplyResponse
import com.gyh.fleacampus.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by GYH on 2021/5/14
 */
@Tag(name = "评论")
@RestController
@RequestMapping("/comment")
class CommentController {
    @Autowired
    lateinit var commentService: CommentService

    @Operation(summary = "分页查询评论", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/page")
    fun findByPage(
        @Parameter(description = "第几页，默认从1开始") @RequestParam(required = false) pageNum: Int?,
        @Parameter(description = "每页数量，默认30") @RequestParam(required = false) pageSize: Int?,
        @Parameter(description = "帖子id", required = true) @RequestParam postId: Int
    ): ResponseInfo<PageView<CommentResponse>> {
        return ResponseInfo.ok(commentService.findCommentByPage(pageNum ?: 1, pageSize ?: 30, postId))
    }

    @Operation(summary = "分页查询评论的回复", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/reply")
    fun findReplyByPage(
        @Parameter(description = "第几页，默认从1开始") @RequestParam(required = false) pageNum: Int?,
        @Parameter(description = "每页数量，默认30") @RequestParam(required = false) pageSize: Int?,
        @Parameter(description = "评论id", required = true) @RequestParam commentId: Int
    ): ResponseInfo<PageView<ReplyResponse>> {
        return ResponseInfo.ok(commentService.findReplyByPage(pageNum ?: 1, pageSize ?: 30, commentId))
    }

    @Operation(summary = "创建评论", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun addComment(@RequestBody comment: Comment): ResponseInfo<Comment> {
        return ResponseInfo.ok(commentService.addComment(comment))
    }

    @Operation(summary = "创建回复", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/reply")
    fun addReply(@RequestBody reply: Reply): ResponseInfo<Reply> {
        return ResponseInfo.ok(commentService.addReply(reply))
    }

}