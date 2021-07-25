package com.gyh.fleacampus.core.model

import io.swagger.v3.oas.annotations.media.Schema

/**
 * Created by gyh on 2021/2/4
 */
@Schema(description = "通用响应")
class ResponseInfo<T> {
    @Schema(description = "响应状态码，$OK_CODE:成功响应，$FAILED_CODE:失败响应码")
    var code = OK_CODE
    @Schema(description = "状态码详情")
    var msg: String? = null
    @Schema(description = "响应数据")
    var data: T? = null

    constructor(code: Int, msg: String?, data: T) {
        this.code = code
        this.msg = msg
        this.data = data
    }

    constructor(code: Int, msg: String?) {
        this.code = code
        this.msg = msg
    }

    constructor()

    companion object {
        const val ACCESS_TOKEN_EXPIRES = 4001
        const val REFRESH_TOKEN_EXPIRES = 4002
        const val OK_CODE = 0
        const val FAILED_CODE = 1
        @JvmStatic
        fun <T> ok(): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, "成功")
        }

        @JvmStatic
        fun <T> ok(msg: String?, data: T): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, msg, data)
        }

        @JvmStatic
        fun <T> ok(data: T): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, "成功", data)
        }

        @JvmStatic
        fun <T> failed(): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, "失败")
        }

        @JvmStatic
        fun <T> failed(msg: String?): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, msg)
        }

        @JvmStatic
        fun <T> failed(msg: String?, data: T): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, msg, data)
        }
    }
}