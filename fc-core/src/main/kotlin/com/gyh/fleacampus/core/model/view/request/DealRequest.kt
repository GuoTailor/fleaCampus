package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Deal

/**
 * Created by GYH on 2021/6/28
 */
data class DealRequest(val images: List<String>) : Deal(imgs = images.joinToString(separator = " "))