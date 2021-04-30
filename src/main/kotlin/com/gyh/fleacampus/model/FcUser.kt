package com.gyh.fleacampus.model

import java.time.LocalDateTime

/**
 *
 * @TableName fc_user
 */
data class FcUser(
    /**
     *
     */
    var id: Int? = null,
    /**
     * 用户名
     */
    var username: String? = null,
    /**
     * 个性签名
     */
    var signature: String? = null,
    /**
     * 头像url地址
     */
    var photo: String? = null,
    /**
     * 手机
     */
    var phone: String? = null,
    /**
     * 性别：0：未知，1：男，2：女
     */
    var sex: Short? = null,
    /**
     * 经验
     */
    var exp: Int? = null,
    /**
     * 积分
     */
    var score: Int? = null,
    /**
     * 星座
     */
    var horoscope: String? = null,
    /**
     * 学校区域id
     */
    var schoolAreaId: Int? = null,
    /**
     * 年纪
     */
    var grade: String? = null,
    /**
     * 专业
     */
    var specialty: String? = null,
    /**
     * 创建时间
     */
    var createTime: LocalDateTime? = null)
