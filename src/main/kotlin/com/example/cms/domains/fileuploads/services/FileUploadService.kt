package com.example.cms.domains.fileuploads.services

import com.example.cms.domains.fileuploads.models.entities.UploadProperties
import com.example.common.exceptions.notfound.NotFoundException
import kotlin.Throws
import java.io.IOException
import org.springframework.web.multipart.MultipartFile

interface FileUploadService {
    @Throws(IOException::class)
    fun uploadImage(
        multipartFile: MultipartFile,
        namespace: String,
        uniqueProperty: String,
        height: Int
    ): UploadProperties

    @Throws(IOException::class)
    fun uploadFile(bytes: ByteArray, ext: String, namespace: String, uniqueProperty: String): UploadProperties

    @Throws(NotFoundException::class)
    fun find(id: Long): UploadProperties

    @Throws(NotFoundException::class)
    fun findByUuid(uuid: String): UploadProperties
}
