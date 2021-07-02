package com.gyh.fleacampus.core.model.view.request

import com.gyh.fleacampus.core.model.Post

/**
 * Created by GYH on 2021/6/28
 */
data class PostRequest(val images: List<String>): Post(imgs = images.joinToString(separator = " "))