package com.gyh.fleacampus.core.model.view.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by GYH on 2021/7/20
 */
@Schema(description = "区域请求实体")
class AreaRequest(
    /**
     * 区域名字
     */
    @Schema(description = "区域名字")
    var areaName: String? = null,
    /**
     * 经度
     */
    @Schema(description = "经度")
    var longitude: String? = null,
    /**
     * 纬度
     */
    @Schema(description = "纬度")
    var latitude: String? = null,
)