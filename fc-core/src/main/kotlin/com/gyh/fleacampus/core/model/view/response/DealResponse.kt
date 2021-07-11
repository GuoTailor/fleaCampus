package com.gyh.fleacampus.core.model.view.response

import com.gyh.fleacampus.core.model.Deal
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by gyh on 2021/7/11
 */
data class DealResponse (
    @Schema(description = "用户名")
    var username: String? = null,
    @Schema(description = "用户头像")
    var photo: String? = null,
    @Schema(description = "图片列表")
    var images: List<String>? = null,
): Deal() {

    fun imgToImageList(): DealResponse {
        images = imgs?.split(" ") ?: emptyList()
        return this
    }
}
