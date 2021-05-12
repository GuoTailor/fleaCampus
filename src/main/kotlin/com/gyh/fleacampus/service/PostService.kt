package com.gyh.fleacampus.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.mapper.PostMapper
import com.gyh.fleacampus.model.PageView
import com.gyh.fleacampus.model.Post
import com.gyh.fleacampus.model.Role
import org.springframework.stereotype.Service
import javax.annotation.Resource

/**
 * Created by GYH on 2021/5/6
 */
@Service
class PostService {
    @Resource
    lateinit var postMapper: PostMapper

    fun createPost(post: Post): Int {
        post.checkStatus()
        val userId = getCurrentUser().id
        post.userId = userId
        post.type = post.type?.toLowerCase()
        // TODO 定时发布时间
        return postMapper.insertSelective(post)
    }

    fun findById(id: Int): Post {
        return postMapper.selectByPrimaryKey(id) ?: error("帖子id不存在")
    }

    fun findPost(pageNum: Int, pageSize: Int): PageView<Post> {
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
        post.type = post.type?.toLowerCase()
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