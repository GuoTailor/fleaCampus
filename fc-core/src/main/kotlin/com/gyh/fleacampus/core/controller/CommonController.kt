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
    val imgPath = "imgs"
    val directory: String by lazy { fileUploadPath + File.separator + imgPath }

    @Operation(summary = "文件上传", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFile(@RequestPart("file") file: MultipartFile): ResponseInfo<String> {
        if (saveFile(file).second != "failed") return ResponseInfo.ok(saveFile(file).second)
        return ResponseInfo.failed("文件为空")
    }

    @Operation(summary = "批量文件上传", security = [SecurityRequirement(name = "Authorization")])
    @PostMapping("/uploads", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadFiles(@RequestPart("file") file: Array<MultipartFile>): ResponseInfo<List<Pair<String?, String>>> {
        if (file.isNotEmpty()) {
            val map = file.map { f -> saveFile(f) }
            return ResponseInfo.ok(map)
        }
        return ResponseInfo.failed("文件为空")
    }

    fun saveFile(file: MultipartFile): Pair<String?, String> {
        val userId = getCurrentUser().id
        if (!file.isEmpty) {
            val suffix = file.originalFilename?.split(".")?.let {
                if (it.lastIndex > 0) "." + it[it.lastIndex] else null
            }
            val fileName = userId.toString() + File.separator + UUID.randomUUID().toString() + (suffix ?: "")
            val dest = File("$directory${File.separator}$fileName")
            if (!dest.parentFile.exists()) {
                val result = dest.parentFile.mkdirs()  //新建文件夹
                if (!result) file.originalFilename to "failed"
            }
            file.transferTo(dest.toPath())
            logger.info(dest.path)
            return file.originalFilename to (imgPath + File.separator + fileName)
        }
        return file.originalFilename to "failed"
    }

    @Operation(summary = "删除文件", security = [SecurityRequirement(name = "Authorization")])
    @DeleteMapping("/file")
    fun deleteFile(@RequestParam path: String): ResponseInfo<Unit> {
        val dest = File(fileUploadPath + File.separator + path)
        dest.delete()
        return ResponseInfo.ok()
    }

    @Operation(summary = "注册")
    @PostMapping("/common/register")
    fun register(@RequestBody user: User): ResponseInfo<User> {
        return ResponseInfo.ok(userService.register(user))
    }
}