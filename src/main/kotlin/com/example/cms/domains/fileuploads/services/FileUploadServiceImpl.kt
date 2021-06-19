package com.example.cms.domains.fileuploads.services

import com.example.cms.domains.fileuploads.models.entities.UploadProperties
import com.example.common.exceptions.notfound.NotFoundException
import com.example.common.utils.ImageUtils
import com.example.coreweb.utils.FileIO
import com.example.cms.domains.fileuploads.repositories.FileUploadRepository
import org.apache.commons.io.FilenameUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.*

@Service
class FileUploadServiceImpl @Autowired constructor(
    private val fileUploadRepo: FileUploadRepository
) : FileUploadService {
    @Value("\${storage.path}")
    lateinit var rootPath: String

    @Value("\${applicationName}")
    lateinit var waterMarkText: String

    @Throws(IOException::class)
    override fun uploadImage(
        multipartFile: MultipartFile,
        namespace: String,
        uniqueProperty: String,
        height: Int
    ): UploadProperties {
        val ext = FilenameUtils.getExtension(multipartFile.originalFilename)
        var bytes = multipartFile.bytes
        if (height > 0) bytes = FileIO.scaleImage(bytes, ext, height)
        println("EXT: $ext")

        val uploadProperties = uploadFile(bytes, ext, namespace, uniqueProperty)

        if ("img".equals(ext, ignoreCase = true) || "png".equals(ext, ignoreCase = true)) {
            if (UploadProperties.NameSpaces.PRODUCTS.value.equals(namespace, ignoreCase = true))
                applyWatermark(File(uploadProperties.filePath()), waterMarkText)
        }
        return uploadProperties
    }

    @Throws(IOException::class)
    override fun uploadFile(
        bytes: ByteArray,
        ext: String,
        namespace: String,
        uniqueProperty: String
    ): UploadProperties {
        var uploadProperties = UploadProperties(rootPath, namespace, uniqueProperty, ext)
        val directory = File(uploadProperties.dirPath())
        if (!directory.exists()) {
            if (!directory.mkdirs()) throw IOException("Could not create necessary directory: " + directory.absolutePath)
        }
        val imageFile = File.createTempFile("upload_", ".$ext", directory)
        val outputStream: OutputStream = BufferedOutputStream(FileOutputStream(imageFile))
        outputStream.write(bytes)
        outputStream.close()
        uploadProperties.fileName = imageFile.name
        uploadProperties = this.fileUploadRepo.save(uploadProperties)
        return uploadProperties
    }

    @Throws(NotFoundException::class)
    override fun find(id: Long): UploadProperties {
        return this.fileUploadRepo.findById(id).orElse(null)
            ?: throw NotFoundException("Could not find file with id: $id")
    }

    @Throws(NotFoundException::class)
    override fun findByUuid(uuid: String): UploadProperties {
        return this.fileUploadRepo.findByUuid(uuid).orElseThrow { NotFoundException("Could not find requested file!") }
    }

    private fun applyWatermark(imageFile: File, text: String?) {
        val output = File(imageFile.absolutePath)
        try {
            ImageUtils.addTextWatermark(text, "png", imageFile, output)
        } catch (e: IOException) {
            println("Could not watermark image: " + e.message)
        }
    }
}
