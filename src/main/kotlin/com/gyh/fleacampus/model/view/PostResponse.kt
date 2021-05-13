package com.gyh.fleacampus.model.view

import com.gyh.fleacampus.model.Post
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/5/13
 */
data class PostResponse(
    @Schema(description = "用户名")
    val username: String,
    @Schema(description = "用户头像")
    val photo: String,
): Post()
