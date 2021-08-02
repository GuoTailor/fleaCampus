package com.gyh.fleacampus.core.service

import com.github.pagehelper.PageHelper
import com.gyh.fleacampus.common.async
import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.mapper.CommentMapper
import com.gyh.fleacampus.core.mapper.LikeMapper
import com.gyh.fleacampus.core.mapper.PostMapperInterface
import com.gyh.fleacampus.core.mapper.ReplyMapper
import com.gyh.fleacampus.core.model.Like
import com.gyh.fleacampus.core.model.PageView
import com.gyh.fleacampus.core.model.Post
import com.gyh.fleacampus.core.model.Role
import com.gyh.fleacampus.core.model.view.response.PostResponse
import com.gyh.fleacampus.core.service.lucene.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.Resource

/**
 * Created by gyh on 2021/7/12
 */
abstract class PostServiceAbstract<I : Post, O: PostResponse> {

    @Resource
    lateinit var likeMapper: LikeMapper
    @Resource
    lateinit var commentMapper: CommentMapper
    @Resource
    lateinit var replyMapper: ReplyMapper
    @Autowired
    lateinit var document: Document

    abstract fun getMapper(): PostMapperInterface<in I, O>

    fun createPost(post: I): I {
        post.checkStatus()
        val userId = getCurrentUser().id
        post.userId = userId
        post.type = post.type?.lowercase(Locale.getDefault())
        // TODO 定时发布时间
        // TODO https://blog.csdn.net/lovely960823/article/details/110046111
        // TODO 坐标转换
        getMapper().insertSelective(post)
        async {
            document.createIndex(post)
            // TODO 图片鉴黄
        }
        return post
    }

    /**
     * 获取帖子详情，并添加一个观看量
     */
    fun findById(id: Int): O {
        val result = getMapper().selectByPrimaryKey(id) ?: error("帖子id不存在")
        getMapper().incrBrowses(id)
        result.imgToImageList()
        return result
    }

    fun findByPage(pageNum: Int, pageSize: Int): PageView<in O> {
        PageHelper.startPage<Any>(pageNum, pageSize)
        return PageView.build(getMapper().findAll().map { it.imgToImageList() })
    }

    /**
     * 更新帖子
     */
    fun updatePost(post: I): Int {
        post.id ?: error("帖子id为必传项")
        if (post.state != null) {
            post.checkStatus()
        }
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != getMapper().selectByPrimaryKey(post.id!!)?.userId
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
        return getMapper().updateByPrimaryKeySelective(post)
    }

    /**
     * 添加帖子赞,再次点击取消
     */
    @Transactional
    open fun addLike(id: Int) {
        val userId = getCurrentUser().id
        val like = Like(userId = userId, postId = id)
        val oldLike = likeMapper.findSelectiveForUpdate(like)
        // 如果还没有点过赞
        if (oldLike == null) {
            like.status = Like.VALID
            likeMapper.insertSelective(like)
            getMapper().incrLikes(id)
        } else if (oldLike.status == Like.INVALID) {    // 如果点过赞，但是取消了
            oldLike.status = Like.VALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            getMapper().incrLikes(id)
        } else if (oldLike.status == Like.VALID) {      // 如果点过赞，也没有取消，就取消掉
            oldLike.status = Like.INVALID
            likeMapper.updateByPrimaryKeySelective(oldLike)
            getMapper().decrLikes(id)
        }
    }

    fun deletePost(id: Int): Int {
        val user = getCurrentUser()
        // 判断调用者是不是帖子发布者或管理员
        if (user.id!! != getMapper().selectByPrimaryKey(id)?.userId && !user.getRoles().contains(Role.SUPER_ADMIN)) {
            error("帖子的创建者才能删除该帖子")
        }
        async { document.deleteIndex(id) }
        likeMapper.deleteByPostId(id)
        commentMapper.deleteByPostId(id)
        replyMapper.deleteByPostId(id)
        return getMapper().deleteByPrimaryKey(id)
    }
}