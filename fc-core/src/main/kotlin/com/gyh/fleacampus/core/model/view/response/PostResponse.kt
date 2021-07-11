package com.gyh.fleacampus.core.model.view.response

import com.gyh.fleacampus.core.model.Post
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/5/13
 */
data class PostResponse(
    @Schema(description = "用户名")
    var username: String? = null,
    @Schema(description = "用户头像")
    var photo: String? = null,
    @Schema(description = "图片列表")
    var images: List<String>? = null,
): Post() {

    fun imgToImageList(): PostResponse {
        images = imgs?.split(" ") ?: emptyList()
        return this
    }
}
