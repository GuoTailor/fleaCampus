package com.gyh.fleacampus.core.model

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

/**
 * fc_area
 * @author
 */
@Schema(description = "区域")
data class Area (
    var id: Int? = null,

    /**
     * 区域名字
     */
    var areaName: String? = null,

    /**
     * 创建时间
     */
    var createTime: Date? = null,

    /**
     * 创建者id
     */
    var userId: Int? = null,

    /**
     * 几何类型
     */
    var geom: Any? = null,

    /**
     * 人数
     */
    var population: Int? = null,

)