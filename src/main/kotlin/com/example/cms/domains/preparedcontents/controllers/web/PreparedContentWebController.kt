package com.example.cms.domains.preparedcontents.controllers.web

import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.cms.domains.contenttemplates.services.ContentTemplateService
import com.example.cms.domains.preparedcontents.models.dtos.PreparedContentDto
import com.example.cms.domains.preparedcontents.models.mappers.PreparedContentMapper
import com.example.cms.domains.preparedcontents.services.PreparedContentService
import com.example.cms.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudWebControllerV3
import com.example.coreweb.domains.base.models.enums.SortByFields
import com.example.coreweb.utils.PageAttr
import org.springframework.data.domain.Sort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid
import com.example.coreweb.utils.PageableParams

@Controller
class PreparedContentWebController @Autowired constructor(
    private val preparedContentService: PreparedContentService,
    private val preparedContentMapper: PreparedContentMapper,
    private val contentTemplateService: ContentTemplateService
) : CrudWebControllerV3<PreparedContentDto> {

    //    @GetMapping(Route.V1.ADMIN_SEARCH_PREPAREDCONTENTS)
    override fun search(
        @RequestParam("q", required = false) query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
        model: Model
    ): String {
        val entities = this.preparedContentService.search(PageableParams.of(query, page, size, sortBy, direction))
        model.addAttribute("preparedcontents", entities.map { this.preparedContentMapper.map(it) })
        return "preparedcontents/fragments/all"
    }

    @GetMapping(Route.V1.ADMIN_SEARCH_PREPAREDCONTENTS)
    fun searchContent(
        @RequestParam("q", required = false) query: String?,
        @RequestParam("template_id", required = false) templateId: Long?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
        model: Model
    ): String {
        val entities = this.preparedContentService.search(
            templateId,
            PageableParams.of(query, page, size, sortBy, direction)
        )
        val templates = this.contentTemplateService.search(
            PageableParams.of(null, 0, 100, SortByFields.ID, Sort.Direction.DESC)
        )
        model.addAttribute("templateId", templateId)
        model.addAttribute("templates", templates)
        model.addAttribute("preparedcontents", entities.map { this.preparedContentMapper.map(it) })
        return "preparedcontents/fragments/all"
    }

    @GetMapping(Route.V1.ADMIN_FIND_PREPAREDCONTENT)
    override fun find(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        val entity = this.preparedContentService.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
        model.addAttribute("preparedcontent", this.preparedContentMapper.map(entity))
        return "preparedcontents/fragments/details"
    }

    //    @GetMapping(Route.V1.ADMIN_CREATE_PREPAREDCONTENT_PAGE)
    override fun createPage(model: Model): String {
        val templates = this.contentTemplateService.search(
            PageableParams.of(
                null, 0, 100, SortByFields.ID, Sort.Direction.DESC
            )
        )
        model.addAttribute("templates", templates)
        return "preparedcontents/fragments/create"
    }

    @GetMapping(Route.V1.ADMIN_CREATE_PREPAREDCONTENT_PAGE)
    fun createPag(
        model: Model,
        @RequestParam("template_id") templateId: Long
    ): String {
        val templates = this.contentTemplateService.search(
            PageableParams.of(
                null, 0, 100, SortByFields.ID, Sort.Direction.DESC
            )
        )
        val selectedTemplate = this.contentTemplateService.find(templateId)
            .orElseThrow { ExceptionUtil.notFound(ContentTemplate::class.java, templateId) }
        model.addAttribute("templates", templates)
        model.addAttribute("selectedTemplate", selectedTemplate)
        return "preparedcontents/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_CREATE_PREPAREDCONTENT)
    override fun create(
        @Valid @ModelAttribute dto: PreparedContentDto,
        redirectAttributes: RedirectAttributes
    ): String {
        val entity = this.preparedContentService.save(this.preparedContentMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_PREPAREDCONTENT.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.ADMIN_UPDATE_PREPAREDCONTENT_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.preparedContentService.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
        val templates = this.contentTemplateService.search(
            PageableParams.of(
                null, 0, 100, SortByFields.ID, Sort.Direction.DESC
            )
        )
        model.addAttribute("templates", templates)
        model.addAttribute("preparedcontent", this.preparedContentMapper.map(entity))
        return "preparedcontents/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_UPDATE_PREPAREDCONTENT)
    override fun update(
        @PathVariable("id") id: Long,
        @Valid @ModelAttribute dto: PreparedContentDto,
        redirectAttributes: RedirectAttributes
    ): String {
        var entity = this.preparedContentService.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
        entity = this.preparedContentService.save(this.preparedContentMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_PREPAREDCONTENT.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.ADMIN_DELETE_PREPAREDCONTENT)
    override fun delete(
        @PathVariable("id") id: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        this.preparedContentService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.ADMIN_SEARCH_PREPAREDCONTENTS}";
    }

}
