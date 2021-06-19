package com.example.cms.domains.contenttemplates.controllers

import com.example.cms.domains.contenttemplates.models.dtos.ContentTemplateDto
import com.example.cms.domains.contenttemplates.models.mappers.ContentTemplateMapper
import com.example.cms.domains.contenttemplates.services.ContentTemplateService
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
@Api(tags = ["Content Templates"], description = "Description about ContentTemplates")
class ContentTemplateController @Autowired constructor(
    private val contentTemplateService: ContentTemplateService,
    private val contentTemplateMapper: ContentTemplateMapper
) : CrudControllerV3<ContentTemplateDto> {

    @GetMapping(Route.V1.SEARCH_CONTENTTEMPLATES)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction): ResponseEntity<Page<ContentTemplateDto>> {
        val entities = this.contentTemplateService.search(PageableParams.of(query, page, size, sortBy, direction))
        return ResponseEntity.ok(entities.map { this.contentTemplateMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_CONTENTTEMPLATE)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<ContentTemplateDto> {
        val entity = this.contentTemplateService.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
        return ResponseEntity.ok(this.contentTemplateMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_CONTENTTEMPLATE)
    override fun create(@Valid @RequestBody dto: ContentTemplateDto): ResponseEntity<ContentTemplateDto> {
        val entity = this.contentTemplateService.save(this.contentTemplateMapper.map(dto, null))
        return ResponseEntity.ok(this.contentTemplateMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_CONTENTTEMPLATE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: ContentTemplateDto
    ): ResponseEntity<ContentTemplateDto> {
        var entity = this.contentTemplateService.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
        entity = this.contentTemplateService.save(this.contentTemplateMapper.map(dto, entity))
        return ResponseEntity.ok(this.contentTemplateMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_CONTENTTEMPLATE)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.contentTemplateService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}
