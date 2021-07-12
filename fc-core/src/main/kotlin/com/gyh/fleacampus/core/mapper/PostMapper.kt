package com.gyh.fleacampus.core.mapper

import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.model.view.response.PostResponse

/**
 * @Entity com.gyh.fleacampus.core.model.FcPost
 */
interface PostMapper : PostMapperInterface<Post, PostResponse>
