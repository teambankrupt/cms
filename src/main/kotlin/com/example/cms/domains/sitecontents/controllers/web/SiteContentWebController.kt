package com.example.cms.domains.sitecontents.controllers.web

import com.example.cms.domains.sitecontents.models.dtos.SiteContentDto
import com.example.cms.domains.sitecontents.models.mappers.SiteContentMapper
import com.example.cms.domains.sitecontents.services.SiteContentService
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
class SiteContentWebController @Autowired constructor(
    private val siteContentService: SiteContentService,
    private val siteContentMapper: SiteContentMapper
) : CrudWebControllerV3<SiteContentDto> {

    /*
        COPY THESE URLS TO ROUTE FILE AND ADJUST
        ------------------------------------------------------
        // SiteContents (Web)
        const val WEB_SEARCH_SITECONTENTS = "/sitecontents"
        const val WEB_CREATE_SITECONTENT_PAGE = "/sitecontents/create"
        const val WEB_CREATE_SITECONTENT = "/sitecontents"
        const val WEB_FIND_SITECONTENT = "/sitecontents/{id}"
        const val WEB_UPDATE_SITECONTENT_PAGE = "/sitecontents/{id}/update"
        const val WEB_UPDATE_SITECONTENT = "/sitecontents/{id}"
        const val WEB_DELETE_SITECONTENT = "/sitecontents/{id}/delete"
        ------------------------------------------------------
    */

    @GetMapping(Route.V1.WEB_SEARCH_SITECONTENTS)
    override fun search(@RequestParam("q", required = false) query: String?,
                        @RequestParam("page", defaultValue = "0") page: Int,
                        @RequestParam("size", defaultValue = "10") size: Int,
                        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
                        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
                        model: Model): String {
        val entities = this.siteContentService.search(PageableParams.of(query, page, size, sortBy, direction))
        model.addAttribute("sitecontents", entities.map { this.siteContentMapper.map(it) })
        return "sitecontents/fragments/all"
    }

    @GetMapping(Route.V1.WEB_FIND_SITECONTENT)
    override fun find(@PathVariable("id") id: Long,
                      model: Model): String {
        val entity = this.siteContentService.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
        model.addAttribute("sitecontent", this.siteContentMapper.map(entity))
        return "sitecontents/fragments/details"
    }

    @GetMapping(Route.V1.WEB_CREATE_SITECONTENT_PAGE)
    override fun createPage(model: Model): String {
        return "sitecontents/fragments/create"
    }

    @PostMapping(Route.V1.WEB_CREATE_SITECONTENT)
    override fun create(@Valid @ModelAttribute dto: SiteContentDto,
                        redirectAttributes: RedirectAttributes): String {
        val entity = this.siteContentService.save(this.siteContentMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITECONTENT.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.WEB_UPDATE_SITECONTENT_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.siteContentService.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
        model.addAttribute("sitecontent", this.siteContentMapper.map(entity))
        return "sitecontents/fragments/create"
    }

    @PostMapping(Route.V1.WEB_UPDATE_SITECONTENT)
    override fun update(@PathVariable("id") id: Long,
                        @Valid @ModelAttribute dto: SiteContentDto,
                        redirectAttributes: RedirectAttributes): String {
        var entity = this.siteContentService.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
        entity = this.siteContentService.save(this.siteContentMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITECONTENT.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.WEB_DELETE_SITECONTENT)
    override fun delete(@PathVariable("id") id: Long,
                        redirectAttributes: RedirectAttributes): String {
        this.siteContentService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.WEB_SEARCH_SITECONTENTS}";
    }

}
