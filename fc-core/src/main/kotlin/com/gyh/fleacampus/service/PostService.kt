package com.gyh.fleacampus.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.PostMapper
import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Post
import com.gyh.fleacampus.model.Role
import com.gyh.fleacampus.model.view.PostResponse
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/6
 */
@Service
class PostService {
    @Resource
    lateinit var postMapper: PostMapper

    fun createPost(post: Post): Post {
        post.checkStatus()
        val userId = getCurrentUser().id
        post.userId = userId
        post.type = post.type?.lowercase(Locale.getDefault())
        // TODO 定时发布时间
        // TODO https://blog.csdn.net/lovely960823/article/details/110046111
        postMapper.insertSelective(post)
        return post
    }

    fun findById(id: Int): PostResponse {
        return postMapper.selectByPrimaryKey(id) ?: error("帖子id不存在")
    }

    fun findByPage(pageNum: Int, pageSize: Int): PageView<PostResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(postMapper.findAll())
    }

    /**
     * 更新铁子
     */
    fun updatePost(post: Post): Int {
        post.id ?: error("帖子id为必传项")
        if (post.state != null) {
            post.checkStatus()
        }
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != postMapper.selectByPrimaryKey(post.id!!)?.userId && !user.getRoles().contains(Role.SUPER_ADMIN)) {
            error("帖子的创建者才能修改该帖子")
        }
        post.userId = null
        post.type = post.type?.lowercase(Locale.getDefault())
        AtomicInteger().incrementAndGet()
        return postMapper.updateByPrimaryKeySelective(post)
    }

    fun deletePost(id: Int): Int {
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != postMapper.selectByPrimaryKey(id)?.userId && !user.getRoles().contains(Role.SUPER_ADMIN)) {
            error("帖子的创建者才能删除该帖子")
        }
        return postMapper.deleteByPrimaryKey(id)
    }
}