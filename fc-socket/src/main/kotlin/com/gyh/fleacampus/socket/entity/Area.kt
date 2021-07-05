package com.gyh.fleacampus.socket.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

/**
 * Created by GYH on 2021/7/5
 */
@Table("fc_area")
data class Area(
    @Id var id: Int? = null,
    var areaName: String? = null,
    var userId: Int? = null,
    var geom: String? = null,
    var population: Int? = null,
    var createTime: LocalDateTime? = null,
)