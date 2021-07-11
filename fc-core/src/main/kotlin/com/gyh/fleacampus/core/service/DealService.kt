package com.gyh.fleacampus.core.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.async
import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.DealMapper
import com.gyh.fleacampus.core.mapper.LikeMapper
import com.gyh.fleacampus.core.model.Like
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.Deal
import com.gyh.fleacampus.core.model.Role
import com.gyh.fleacampus.core.model.view.response.DealResponse
import com.gyh.fleacampus.core.service.lucene.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.Resource

/**
 * Created by gyh on 2021/7/11
 */
@Service
class DealService {
    @Resource
    lateinit var dealMapper: DealMapper

    @Resource
    lateinit var likeMapper: LikeMapper

    @Autowired
    lateinit var document: Document

    fun createDeal(post: Deal): Deal {
        post.checkStatus()
        val userId = getCurrentUser().id
        post.userId = userId
        post.type = post.type?.lowercase(Locale.getDefault())
        // TODO 定时发布时间
        // TODO https://blog.csdn.net/lovely960823/article/details/110046111
        // TODO 坐标转换
        dealMapper.insertSelective(post)
        async {
            document.createIndex(post)
            // TODO 图片鉴黄
        }
        return post
    }

    /**
     * 获取帖子详情，并添加一个观看量
     */
    fun findById(id: Int): DealResponse {
        val result = dealMapper.selectByPrimaryKey(id) ?: error("帖子id不存在")
        dealMapper.incrBrowses(id)
        result.imgToImageList()
        return result
    }

    fun findByPage(pageNum: Int, pageSize: Int): PageView<DealResponse> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(dealMapper.findAll().map(DealResponse::imgToImageList))
    }

    /**
     * 更新帖子
     */
    fun updateDeal(post: Deal): Int {
        post.id ?: error("帖子id为必传项")
        if (post.state != null) {
            post.checkStatus()
        }
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != dealMapper.selectByPrimaryKey(post.id!!)?.userId
            && !user.getRoles().contains(Role.SUPER_ADMIN)
        ) {
            error("帖子的创建者才能修改该帖子")
        }
        post.userId = null
        post.type = post.type?.lowercase(Locale.getDefault())
        AtomicInteger().incrementAndGet()
        async {
            document.updateIndex(post)
            // TODO 图片鉴黄
        }
        return dealMapper.updateByPrimaryKeySelective(post)
    }

    /**
     * 添加帖子赞
     */
    fun addLike(id: Int) {
        val userId = getCurrentUser().id
        likeMapper.insertSelective(Like(userId = userId, postId = id, status = Like.VALID))
        dealMapper.incrLikes(id)
    }

    fun deleteDeal(id: Int): Int {
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != dealMapper.selectByPrimaryKey(id)?.userId && !user.getRoles().contains(Role.SUPER_ADMIN)) {
            error("帖子的创建者才能删除该帖子")
        }
        async { document.deleteIndex(id) }
        return dealMapper.deleteByPrimaryKey(id)
    }
}