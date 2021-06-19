package com.example.cms.domains.preparedcontents.controllers

import com.example.cms.domains.preparedcontents.models.dtos.PreparedContentDto
import com.example.cms.domains.preparedcontents.models.mappers.PreparedContentMapper
import com.example.cms.domains.preparedcontents.services.PreparedContentService
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
@Api(tags = ["Prepared Contents"], description = "Description about PreparedContents")
class PreparedContentController @Autowired constructor(
    private val preparedContentService: PreparedContentService,
    private val preparedContentMapper: PreparedContentMapper
) : CrudControllerV3<PreparedContentDto> {

    @GetMapping(Route.V1.SEARCH_PREPAREDCONTENTS)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction): ResponseEntity<Page<PreparedContentDto>> {
        val entities = this.preparedContentService.search(PageableParams.of(query, page, size, sortBy, direction))
        return ResponseEntity.ok(entities.map { this.preparedContentMapper.map(it) })
    }

    @GetMapping(Route.V1.FIND_PREPAREDCONTENT)
    override fun find(@PathVariable("id") id: Long): ResponseEntity<PreparedContentDto> {
        val entity = this.preparedContentService.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
        return ResponseEntity.ok(this.preparedContentMapper.map(entity))
    }

    @PostMapping(Route.V1.CREATE_PREPAREDCONTENT)
    override fun create(@Valid @RequestBody dto: PreparedContentDto): ResponseEntity<PreparedContentDto> {
        val entity = this.preparedContentService.save(this.preparedContentMapper.map(dto, null))
        return ResponseEntity.ok(this.preparedContentMapper.map(entity))
    }

    @PatchMapping(Route.V1.UPDATE_PREPAREDCONTENT)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @RequestBody dto: PreparedContentDto
    ): ResponseEntity<PreparedContentDto> {
        var entity = this.preparedContentService.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
        entity = this.preparedContentService.save(this.preparedContentMapper.map(dto, entity))
        return ResponseEntity.ok(this.preparedContentMapper.map(entity))
    }

    @DeleteMapping(Route.V1.DELETE_PREPAREDCONTENT)
    override fun delete(@PathVariable("id") id: Long): ResponseEntity<Any> {
        this.preparedContentService.delete(id, true)
        return ResponseEntity.ok().build()
    }

}