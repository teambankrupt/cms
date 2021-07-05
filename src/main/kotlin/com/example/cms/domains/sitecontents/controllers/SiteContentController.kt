package com.example.cms.domains.sitecontents.controllers

import com.example.cms.domains.sitecontents.models.dtos.SiteContentDto
import com.example.cms.domains.sitecontents.models.mappers.SiteContentMapper
import com.example.cms.domains.sitecontents.services.SiteContentService
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
@Api(tags = ["SiteContents"], description = "Description about SiteContents")
class SiteContentController @Autowired constructor(
    private val siteContentService: SiteContentService,
    private val siteContentMapper: SiteContentMapper
) : CrudControllerV3<SiteContentDto> {

    @GetMapping(Route.V1.SEARCH_SITECONTENTS)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction): ResponseEntity<Page<SiteContentDto>> {
        val entities = this.siteContentService.search(PageableParams.of(query, page, size, sortBy, direction))
        return ResponseEntity.ok(entities.map { this.siteContentMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_SITECONTENT)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<SiteContentDto> {
        val entity = this.siteContentService.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
        return ResponseEntity.ok(this.siteContentMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_SITECONTENT)
    override fun create(@Valid @RequestBody dto: SiteContentDto): ResponseEntity<SiteContentDto> {
        val entity = this.siteContentService.save(this.siteContentMapper.map(dto, null))
        return ResponseEntity.ok(this.siteContentMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_SITECONTENT)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: SiteContentDto): ResponseEntity<SiteContentDto> {
        var entity = this.siteContentService.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
        entity = this.siteContentService.save(this.siteContentMapper.map(dto, entity))
        return ResponseEntity.ok(this.siteContentMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_SITECONTENT)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.siteContentService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
