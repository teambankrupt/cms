package com.example.cms.domains.preparedcontents.repositories

import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PreparedContentRepository : JpaRepository<PreparedContent, Long> {

    @Query("SELECT e FROM PreparedContent e WHERE (:q IS NULL OR LOWER(e.title) LIKE %:q%)" +
            " AND (:templateId IS NULL OR e.template.id=:templateId) AND e.deleted=FALSE")
    fun search(
        @Param("q") query: String?,
        @Param("templateId") templateId: Long?,
        pageable: Pageable
    ): Page<PreparedContent>

    @Query("SELECT e FROM PreparedContent e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<PreparedContent>

}
