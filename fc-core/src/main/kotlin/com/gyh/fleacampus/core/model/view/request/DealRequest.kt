package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Deal
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/6/28
 */
@Schema(description = "二手请求实体")
data class DealRequest(val images: List<String>) : Deal(imgs = images.joinToString(separator = " "))