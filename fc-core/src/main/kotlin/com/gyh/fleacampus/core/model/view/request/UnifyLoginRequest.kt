package com.gyh.fleacampus.core.model.view.request

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by gyh on 2021/8/30
 */
@Schema(description = "统一登录请求")
data class UnifyLoginRequest(
    @Schema(description = "登录类型 'PHONE'：手机号登录，'WX'：微信登录，'QQ'：QQ登录")
    var type: String? = null,
    @Schema(description = "手机号")
    var phone: String? = null,
    @Schema(description = "QQ微信的openid")
    var openid: String? = null,
    @Schema(description = "微信的unionid")
    var unionid: String? = null,
    @Schema(description = "昵称")
    var nickname: String? = null,
    @Schema(description = "性别，1 为男性，2 为女性")
    var sex: Int? = null,
    @Schema(description = "国家")
    var country: String? = null,
    @Schema(description = "省")
    var province: String? = null,
    @Schema(description = "市")
    var city: String? = null,
    @Schema(description = "用户头像")
    var headimgurl: String? = null,
)