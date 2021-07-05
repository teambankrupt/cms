package com.example.cms.domains.sitepages.controllers

import com.example.cms.domains.sitepages.models.dtos.SitePageDto
import com.example.cms.domains.sitepages.models.mappers.SitePageMapper
import com.example.cms.domains.sitepages.services.SitePageService
import com.example.cms.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudControllerV3
import com.example.coreweb.domains.base.models.enums.SortByFields
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import com.example.coreweb.utils.PageableParams
import org.springframework.data.domain.Sort

@RestController
@Api(tags = ["SitePages"], description = "Description about SitePages")
class SitePageController @Autowired constructor(
    private val sitePageService: SitePageService,
    private val sitePageMapper: SitePageMapper
) : CrudControllerV3<SitePageDto> {

    @GetMapping(Route.V1.SEARCH_SITEPAGES)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction): ResponseEntity<Page<SitePageDto>> {
        val entities = this.sitePageService.search(PageableParams.of(query, page, size, sortBy, direction))
        return ResponseEntity.ok(entities.map { this.sitePageMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_SITEPAGE)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<SitePageDto> {
        val entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        return ResponseEntity.ok(this.sitePageMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_SITEPAGE)
    override fun create(@Valid @RequestBody dto: SitePageDto): ResponseEntity<SitePageDto> {
        val entity = this.sitePageService.save(this.sitePageMapper.map(dto, null))
        return ResponseEntity.ok(this.sitePageMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_SITEPAGE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: SitePageDto
    ): ResponseEntity<SitePageDto> {
        var entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        entity = this.sitePageService.save(this.sitePageMapper.map(dto, entity))
        return ResponseEntity.ok(this.sitePageMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_SITEPAGE)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.sitePageService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
