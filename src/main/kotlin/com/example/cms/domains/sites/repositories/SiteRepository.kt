package com.example.cms.domains.sites.repositories

import com.example.cms.domains.sites.models.entities.Site
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SiteRepository : JpaRepository<Site, Long> {

    @Query("SELECT e FROM Site e WHERE (:q IS NULL OR LOWER(e.createdBy) LIKE %:q%) AND e.deleted=FALSE")
    fun search(@Param("q") query: String?, pageable: Pageable): Page<Site>

    @Query("SELECT e FROM Site e WHERE e.id=:id AND e.deleted=FALSE")
    fun find(@Param("id") id: Long): Optional<Site>

}
