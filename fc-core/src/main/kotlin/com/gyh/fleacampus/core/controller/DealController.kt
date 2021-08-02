package com.gyh.fleacampus.core.controller

import com.gyh.fleacampus.core.model.Deal
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.ResponseInfo
import com.gyh.fleacampus.core.model.view.request.DealRequest
import com.gyh.fleacampus.core.model.view.response.DealResponse
import com.gyh.fleacampus.core.service.DealService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

/**
 * Created by GYH on 2021/7/12
 */
@Tag(name = "二手")
@RestController
@RequestMapping("/deal")
class DealController {
    @Autowired
    lateinit var dealService: DealService

    @Operation(summary = "创建二手", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping
    fun createPost(@RequestBody deal: DealRequest): ResponseInfo<Deal> {
        return ResponseInfo.ok(dealService.createPost(deal))
    }

    @Operation(summary = "根据id获取二手")
    @GetMapping
    fun findById(@Parameter(description = "二手id", required = true) @RequestParam id: Int): ResponseInfo<DealResponse> {
        return ResponseInfo.ok(dealService.findById(id))
    }

    @Operation(summary = "分页获取二手")
    @GetMapping("/all")
    fun findAll(
        @Parameter(description = "第几页，默认从1开始") @RequestParam(required = false) pageNum: Int?,
        @Parameter(description = "每页数量，默认30") @RequestParam(required = false) pageSize: Int?
    ): ResponseInfo<PageView<in DealResponse>> {
        return ResponseInfo.ok(dealService.findByPage(pageNum ?: 1, pageSize ?: 30))
    }

    @Operation(summary = "更新铁子", security = [SecurityRequirement(name = "Authorization")])
    @PutMapping
    fun updateDeal(@RequestBody deal: DealRequest): ResponseInfo<Int> {
        return ResponseInfo.ok(dealService.updatePost(deal))
    }

    @Operation(summary = "添加一个点赞，再次点击取消", security = [SecurityRequirement(name = "Authorization")])
    @PutMapping("/like")
    fun addLike(@Parameter(description = "二手id", required = true) @RequestParam id: Int): ResponseInfo<Unit> {
        dealService.addLike(id)
        return ResponseInfo.ok()
    }

    @Operation(summary = "删除二手", security = [SecurityRequirement(name = "Authorization")])
    @DeleteMapping
    fun deleteDeal(@Parameter(description = "二手id", required = true) @RequestParam id: Int): ResponseInfo<Int> {
        return ResponseInfo.ok(dealService.deletePost(id))
    }
}