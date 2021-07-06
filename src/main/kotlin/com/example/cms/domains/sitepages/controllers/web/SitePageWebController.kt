package com.example.cms.domains.sitepages.controllers.web

import com.example.cms.domains.sitepages.models.dtos.SitePageDto
import com.example.cms.domains.sitepages.models.mappers.SitePageMapper
import com.example.cms.domains.sitepages.services.SitePageService
import com.example.cms.domains.sites.models.entities.Site
import com.example.cms.domains.sites.models.mappers.SiteMapper
import com.example.cms.domains.sites.services.SiteService
import com.example.cms.routing.Route
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.controllers.CrudWebControllerV3
import com.example.coreweb.domains.base.models.enums.SortByFields
import com.example.coreweb.utils.PageableParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid

@Controller
class SitePageWebController @Autowired constructor(
    private val sitePageService: SitePageService,
    private val sitePageMapper: SitePageMapper,
    private val siteService: SiteService,
    private val siteMapper: SiteMapper
) : CrudWebControllerV3<SitePageDto> {


    @GetMapping(Route.V1.WEB_SEARCH_SITEPAGES)
    override fun search(
        @RequestParam("q", required = false) query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
        model: Model
    ): String {
        val entities = this.sitePageService.search(PageableParams.of(query, page, size, sortBy, direction))
        model.addAttribute("sitepages", entities.map { this.sitePageMapper.map(it) })
        return "sitepages/fragments/all"
    }

    @GetMapping(Route.V1.WEB_FIND_SITEPAGE)
    override fun find(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        val entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        model.addAttribute("sitepage", this.sitePageMapper.map(entity))
        return "sitepages/fragments/details"
    }

    @GetMapping(Route.V1.WEB_PREVIEW_SITEPAGE)
    fun preview(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        val entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        model.addAttribute("content", entity.content)
        return "sitepages/fragments/preview"
    }

    //    @GetMapping(Route.V1.WEB_CREATE_SITEPAGE_PAGE)
    override fun createPage(model: Model): String {
        return "sitepages/fragments/create"
    }

    @GetMapping(Route.V1.WEB_CREATE_SITEPAGE_PAGE)
    fun createSitePage(
        @RequestParam("site_id") siteId: Long,
        model: Model
    ): String {
        val site = this.siteService.find(siteId).orElseThrow { ExceptionUtil.notFound(Site::class.java, siteId) }
        model.addAttribute("site", this.siteMapper.map(site))
        return "sitepages/fragments/create"
    }

    @PostMapping(Route.V1.WEB_CREATE_SITEPAGE)
    override fun create(
        @Valid @ModelAttribute dto: SitePageDto,
        redirectAttributes: RedirectAttributes
    ): String {
        val entity = this.sitePageService.save(this.sitePageMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITEPAGE.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.WEB_UPDATE_SITEPAGE_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        model.addAttribute("sitepage", this.sitePageMapper.map(entity))
        model.addAttribute("site", this.siteMapper.map(entity.site))
        return "sitepages/fragments/create"
    }

    @PostMapping(Route.V1.WEB_UPDATE_SITEPAGE)
    override fun update(
        @PathVariable("id") id: Long,
        @Valid @ModelAttribute dto: SitePageDto,
        redirectAttributes: RedirectAttributes
    ): String {
        var entity = this.sitePageService.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
        entity = this.sitePageService.save(this.sitePageMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITEPAGE.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.WEB_DELETE_SITEPAGE)
    override fun delete(
        @PathVariable("id") id: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        this.sitePageService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.WEB_SEARCH_SITEPAGES}";
    }

}
