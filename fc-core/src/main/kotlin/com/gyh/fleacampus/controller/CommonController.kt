package com.gyh.fleacampus.controller

import com.gyh.fleacampus.common.getCurrentUser
import com.gyh.fleacampus.model.ResponseInfo
import com.gyh.fleacampus.model.User
import com.gyh.fleacampus.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.LocalDateTime
import java.util.*

/**
 * Created by gyh on 2021/2/4
 */
@Tag(name = "通用")
@RestController
class CommonController(val userService: UserService) {
    @Value("\${fileUploadPath}")
    lateinit var fileUploadPath: String

    @Operation(summary = "test", security = [SecurityRequirement(name = "Authorization")])
    @GetMapping("/common")
    fun test(): ResponseInfo<User> {
        val user = User()
        user.createTime = LocalDateTime.now()
        return ResponseInfo.ok(user)
    }

    @Operation(summary = "文件上传", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestPart("file") file: MultipartFile): ResponseInfo<String> {
        val userId = getCurrentUser().id
        if (!file.isEmpty) {
            val suffix = file.originalFilename?.split(".")?.let {
                if (it.lastIndex > 0) "." + it[it.lastIndex] else null
            }
            val fileName = userId.toString() + File.separator + UUID.randomUUID().toString() + (suffix ?: "")
            val dest = File("$fileUploadPath${File.separator}$fileName")
            if (!dest.parentFile.exists()) {
                val result = dest.parentFile.mkdirs()  //新建文件夹
                if (!result) return ResponseInfo.failed("文件创建失败")
            }
            file.transferTo(dest.toPath())
            return ResponseInfo.ok(dest.path)
        }
        return ResponseInfo.failed("文件为空")
    }

    @Operation(summary = "注册")
    @PostMapping("/common/register")
    fun register(@RequestBody user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}