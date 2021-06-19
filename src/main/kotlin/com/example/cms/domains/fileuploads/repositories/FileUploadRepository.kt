package com.example.cms.domains.fileuploads.repositories

import com.example.cms.domains.fileuploads.models.entities.UploadProperties
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface FileUploadRepository : JpaRepository<UploadProperties?, Long?> {

    @Query("SELECT p FROM UploadProperties p WHERE p.uuid=:uuid AND p.deleted=FALSE")
    fun findByUuid(@Param("uuid") uuid: String): Optional<UploadProperties>

}
