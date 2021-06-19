package com.example.cms.domains.contenttemplates.controllers.web

import com.example.cms.domains.contenttemplates.models.dtos.ContentTemplateDto
import com.example.cms.domains.contenttemplates.models.mappers.ContentTemplateMapper
import com.example.cms.domains.contenttemplates.services.ContentTemplateService
import com.example.cms.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudWebControllerV3
import com.example.coreweb.domains.base.models.enums.SortByFields
import org.springframework.data.domain.Sort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid
import com.example.coreweb.utils.PageableParams

@Controller
class ContentTemplateWebController @Autowired constructor(
    private val contentTemplateService: ContentTemplateService,
    private val contentTemplateMapper: ContentTemplateMapper
) : CrudWebControllerV3<ContentTemplateDto> {

    @GetMapping(Route.V1.ADMIN_SEARCH_CONTENTTEMPLATES)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
                        model: Model): String {
        val entities = this.contentTemplateService.search(PageableParams.of(query, page, size, sortBy, direction))
        model.addAttribute("contenttemplates", entities.map { this.contentTemplateMapper.map(it) })
        return "contenttemplates/fragments/all"
    }

    @GetMapping(Route.V1.ADMIN_FIND_CONTENTTEMPLATE)
    override fun find(@PathVariable("id") id: Long,
                      model: Model): String {
        val entity = this.contentTemplateService.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
        model.addAttribute("contenttemplate", this.contentTemplateMapper.map(entity))
        return "contenttemplates/fragments/details"
    }

    @GetMapping(Route.V1.ADMIN_CREATE_CONTENTTEMPLATE_PAGE)
    override fun createPage(model: Model): String {
        return "contenttemplates/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_CREATE_CONTENTTEMPLATE)
    override fun create(@Valid @ModelAttribute dto: ContentTemplateDto,
                        redirectAttributes: RedirectAttributes): String {
        val entity = this.contentTemplateService.save(this.contentTemplateMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_CONTENTTEMPLATE.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.ADMIN_UPDATE_CONTENTTEMPLATE_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.contentTemplateService.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
        model.addAttribute("contenttemplate", this.contentTemplateMapper.map(entity))
        return "contenttemplates/fragments/create"
    }

    @PostMapping(Route.V1.ADMIN_UPDATE_CONTENTTEMPLATE)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @ModelAttribute dto: ContentTemplateDto,
                        redirectAttributes: RedirectAttributes): String {
        var entity = this.contentTemplateService.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
        entity = this.contentTemplateService.save(this.contentTemplateMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.ADMIN_FIND_CONTENTTEMPLATE.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.ADMIN_DELETE_CONTENTTEMPLATE)
    override fun delete(@PathVariable("id") id: Long,
                        redirectAttributes: RedirectAttributes): String {
        this.contentTemplateService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.ADMIN_SEARCH_CONTENTTEMPLATES}";
    }

}
