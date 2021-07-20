package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Post
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/6/28
 */
@Schema(description = "帖子请求实体")
data class PostRequest(val images: List<String>): Post(imgs = images.joinToString(separator = " "))