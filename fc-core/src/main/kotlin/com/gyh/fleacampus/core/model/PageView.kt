package com.gyh.fleacampus.core.model

import com.github.pagehelper.Page
import com.github.pagehelper.PageInfo
import org.springframework.beans.BeanUtils

/**
 * Created by gyh on 2018/10/19.
 * @apiDefine PageView
 * @apiSuccess (返回) {Long} pageNum 当前页号
 * @apiSuccess (返回) {Long} pageSize 每页条数
 * @apiSuccess (返回) {Long} pages 总页数
 * @apiSuccess (返回) {Long} total 总条数
 */
class PageView<T>(pojo: List<T>) {
    var pageNum = 0
    var pageSize = 0
    var total: Long = 0
    var list: List<T>? = null
    val pages: Int
        get() = getPages(total, pageSize)

    companion object {
        @JvmStatic
        fun <K, P : List<K>> build(pojo: P): PageView<K> {
            return PageView(pojo)
        }

        @JvmStatic
        fun getPages(total: Long, pageSize: Int): Int {
            return if (total == 0L || pageSize == 0) {
                0
            } else (if (total % pageSize == 0L) total / pageSize else total / pageSize + 1).toInt()
        }
    }

    init {
        if (pojo is Page<*>) {
            BeanUtils.copyProperties((pojo as Page<*>).toPageInfo(), this)
        } else {
            BeanUtils.copyProperties(PageInfo(pojo), this)
        }
    }
}