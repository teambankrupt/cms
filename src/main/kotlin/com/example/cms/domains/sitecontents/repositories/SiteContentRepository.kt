package com.example.cms.domains.sitecontents.repositories

import com.example.cms.domains.sitecontents.models.entities.SiteContent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SiteContentRepository : JpaRepository<SiteContent, Long> {

    @Query("SELECT e FROM SiteContent e WHERE (:q IS NULL OR LOWER(e.createdBy) LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String?, pageable: Pageable): Page<SiteContent>

    @Query("SELECT e FROM SiteContent e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<SiteContent>

    @Query("SELECT e FROM SiteContent e WHERE e.slug=:slug AND e.deleted=FALSE")
    fun findBySlug(@Param("slug") slug: String): Optional<SiteContent>

    @Query("SELECT e FROM SiteContent e WHERE e.slug=:slug")
    fun findBySlugIncludingDeleted(@Param("slug") slug: String): Optional<SiteContent>

}
