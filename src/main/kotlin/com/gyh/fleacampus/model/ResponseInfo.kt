package com.gyh.fleacampus.model

/**
 * Created by gyh on 2021/2/4
 */
class ResponseInfo<T> {
    var code = 0
    var msg: String? = null
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

    constructor() {}

    companion object {
        const val OK_CODE = 0
        const val FAILED_CODE = 1
        fun <T> ok(): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, "成功")
        }

        fun <T> ok(msg: String?, data: T): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, msg, data)
        }

        fun <T> ok(data: T): ResponseInfo<T> {
            return ResponseInfo(OK_CODE, "成功", data)
        }

        fun <T> failed(): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, "失败")
        }

        fun <T> failed(msg: String?): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, msg)
        }

        fun <T> failed(msg: String?, data: T): ResponseInfo<T> {
            return ResponseInfo(FAILED_CODE, msg, data)
        }
    }
}