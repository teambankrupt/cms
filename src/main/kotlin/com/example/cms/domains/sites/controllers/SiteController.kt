package com.example.cms.domains.sites.controllers

import com.example.cms.domains.sites.services.SiteService
import com.example.cms.domains.sites.models.dtos.SiteDto
import com.example.cms.domains.sites.models.mappers.SiteMapper
import com.example.cms.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudControllerV3
import com.example.coreweb.domains.base.models.enums.SortByFields
import com.example.coreweb.utils.PageableParams
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@Api(tags = ["Sites"], description = "Description about Sites")
class SiteController @Autowired constructor(
    private val siteService: SiteService,
    private val siteMapper: SiteMapper
) : CrudControllerV3<SiteDto> {

    @GetMapping(Route.V1.SEARCH_SITES)
    override fun search(
        @RequestParam("q", required = false) query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction
    ): ResponseEntity<Page<SiteDto>> {
        val entities = this.siteService.search(PageableParams.of(query, page, size, sortBy, direction))
        return ResponseEntity.ok(entities.map { this.siteMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_SITE)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<SiteDto> {
        val entity = this.siteService.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
        return ResponseEntity.ok(this.siteMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_SITE)
    override fun create(@Valid @RequestBody dto: SiteDto): ResponseEntity<SiteDto> {
        val entity = this.siteService.save(this.siteMapper.map(dto, null))
        return ResponseEntity.ok(this.siteMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_SITE)
    override fun update(
        @PathVariable("id") id: Long,
        @Valid @RequestBody dto: SiteDto
    ): ResponseEntity<SiteDto> {
        var entity = this.siteService.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
        entity = this.siteService.save(this.siteMapper.map(dto, entity))
        return ResponseEntity.ok(this.siteMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_SITE)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.siteService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
