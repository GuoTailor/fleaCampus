package com.gyh.fleacampus.core.controller

import com.gyh.fleacampus.core.common.getCurrentUser
import com.gyh.fleacampus.core.model.ResponseInfo
import com.gyh.fleacampus.core.model.User
import com.gyh.fleacampus.core.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
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
            logger.info(dest.path)
            return ResponseInfo.ok(dest.path)
        }
        return ResponseInfo.failed("文件为空")
    }

    @Operation(summary = "删除文件", security = [SecurityRequirement(name = "Authorization")])
    @DeleteMapping("/file")
    fun deleteFile(@RequestParam path: String): ResponseInfo<Unit> {
        val dest = File(path)
        dest.delete()
        return ResponseInfo.ok()
    }

    @Operation(summary = "注册")
    @PostMapping("/common/register")
    fun register(@RequestBody user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}