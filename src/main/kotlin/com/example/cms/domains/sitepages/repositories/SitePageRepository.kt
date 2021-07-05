package com.example.cms.domains.sitepages.repositories

import com.example.cms.domains.sitepages.models.entities.SitePage
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SitePageRepository : JpaRepository<SitePage, Long> {

    @Query("SELECT e FROM SitePage e WHERE (:q IS NULL OR LOWER(e.createdBy) LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String?, pageable: Pageable): Page<SitePage>

    @Query("SELECT e FROM SitePage e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<SitePage>

}
