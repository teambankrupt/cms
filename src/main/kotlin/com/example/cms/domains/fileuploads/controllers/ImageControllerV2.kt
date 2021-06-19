package com.example.cms.domains.fileuploads.controllers

import com.example.auth.config.security.SecurityContext
import com.example.cms.routing.Route
import com.example.cms.utils.ImageValidator
import com.example.common.exceptions.invalid.ImageInvalidException
import com.example.cms.domains.fileuploads.models.dtos.ImageUploadResponse
import com.example.cms.domains.fileuploads.services.FileUploadService
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

@Api(tags = ["Uploads"], description = "Handle File Uploads including images")
@RestController
class ImageControllerV2 @Autowired constructor(
    private val uploadService: FileUploadService
) {
    @Value("\${app.base-url-image}")
    lateinit var baseUrlImages: String

    // UPLOAD IMAGES
    @PostMapping(Route.V1.UPLOAD_IMAGE)
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam(value = "namespace") namespace: String
    ): ResponseEntity<ImageUploadResponse> {
        val response = upload(file, namespace, SecurityContext.getLoggedInUsername())
        return ResponseEntity.ok(response)
    }

    // UPLOAD IMAGES
    @PostMapping(Route.V1.UPLOAD_IMAGE_BULK)
    fun uploadImages(
        @RequestParam("files") files: Array<MultipartFile>,
        @RequestParam(value = "namespace") namespace: String
    ): ResponseEntity<*> {
        if (files.isEmpty()) return ResponseEntity.badRequest().body("At least one image is expected!")
        val responses: MutableList<ImageUploadResponse> = ArrayList()
        for (file in files) responses.add(upload(file, namespace, SecurityContext.getLoggedInUsername()))
        return ResponseEntity.ok(responses)
    }

    private fun upload(file: MultipartFile, namespace: String, username: String): ImageUploadResponse {
        if (!ImageValidator.isImageValid(file)) throw ImageInvalidException("Invalid Image")
        val response = ImageUploadResponse()
        var uploadProperties = uploadService.uploadImage(file, namespace, username, 1200)
        response.imageUrl = baseUrlImages + uploadProperties.fileUrl
        uploadProperties = uploadService.uploadImage(file, namespace, username + File.separator + "thumbs", 600)
        response.thumbUrl = baseUrlImages + uploadProperties.fileUrl
        return response
    }
}
