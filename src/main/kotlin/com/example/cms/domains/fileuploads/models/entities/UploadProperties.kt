package com.example.cms.domains.fileuploads.models.entities

import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.entities.BaseEntity
import java.io.File
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "uploaded_files", schema = "cms")
class UploadProperties() : BaseEntity() {

    @Column(name = "namespace")
    lateinit var namespace: String

    @Column(name = "unique_property")
    lateinit var uniqueProperty: String

    @Column(name = "root_path")
    lateinit var rootPath: String

    @Column(name = "file_name")
    lateinit var fileName: String

    @Column(name = "file_type")
    lateinit var fileType: String

    constructor(rootPath: String, namespace: String, uniqueProperty: String, fileType: String) : this() {
        this.rootPath = rootPath
        this.namespace = namespace
        this.uniqueProperty = uniqueProperty
        this.fileType = fileType
    }

    enum class NameSpaces(var value: String) {
        USERS("users"), PROMOTIONS("promotions"), PRODUCTS("products");
    }

    private fun dataDir(): String {
        return "$rootPath${File.separator}AppData"
    }

    fun dirPath(): String {
        return "${this.dataDir()}${File.separator}$namespace${File.separator}$uniqueProperty${File.separator}$fileType"
    }

    fun filePath(): String {
        return "${this.dirPath()}${File.separator}${fileName}"
    }

    val fileUrl: String
        get() {
            if (fileName.isBlank()) throw ExceptionUtil.invalid("Filename can not be null or empty!")
            return "${File.separator}$namespace${File.separator}$uniqueProperty" +
                    "${File.separator}$fileType${File.separator}$fileName"
        }
}
