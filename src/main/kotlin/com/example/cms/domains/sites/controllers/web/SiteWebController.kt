package com.example.cms.domains.sites.controllers.web

import com.example.cms.domains.sitecontents.models.mappers.SiteContentMapper
import com.example.cms.domains.sitecontents.services.SiteContentService
import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sitepages.models.mappers.SitePageMapper
import com.example.cms.domains.sitepages.services.SitePageService
import com.example.cms.domains.sites.models.dtos.SiteDto
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
class SiteWebController @Autowired constructor(
    private val siteService: SiteService,
    private val siteMapper: SiteMapper,
    private val pageService: SitePageService,
    private val pageMapper: SitePageMapper,
    private val contentService: SiteContentService,
    private val contentMapper: SiteContentMapper
) : CrudWebControllerV3<SiteDto> {


    @GetMapping(Route.V1.WEB_SEARCH_SITES)
    override fun search(
        @RequestParam("q", required = false) query: String?,
        @RequestParam("page", defaultValue = "0") page: Int,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("sort_by", defaultValue = "ID") sortBy: SortByFields,
        @RequestParam("sort_direction", defaultValue = "DESC") direction: Sort.Direction,
        model: Model
    ): String {
        val entities = this.siteService.search(PageableParams.of(query, page, size, sortBy, direction))
        model.addAttribute("sites", entities.map { this.siteMapper.map(it) })
        return "sites/fragments/all"
    }

    @GetMapping(Route.V1.WEB_FIND_SITE)
    override fun find(
        @PathVariable("id") id: Long,
        model: Model
    ): String {
        val entity = this.siteService.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
        val pages = this.pageService.searchForSite(
            entity.id, PageableParams.of(null, 0, 20, SortByFields.ID, Sort.Direction.DESC)
        )
        val contents = this.contentService.searchForSite(
            entity.id, PageableParams.of(null, 0, 20, SortByFields.ID, Sort.Direction.DESC)
        )
        model.addAttribute("site", this.siteMapper.map(entity))
        model.addAttribute("pages", pages.map { this.pageMapper.map(it) })
        model.addAttribute("contents", contents.map { this.contentMapper.map(it) })
        return "sites/fragments/details"
    }

    @GetMapping(Route.V1.WEB_CREATE_SITE_PAGE)
    override fun createPage(model: Model): String {
        model.addAttribute("pages", emptyList<SitePage>())
        return "sites/fragments/create"
    }

    @PostMapping(Route.V1.WEB_CREATE_SITE)
    override fun create(
        @Valid @ModelAttribute dto: SiteDto,
        redirectAttributes: RedirectAttributes
    ): String {
        val entity = this.siteService.save(this.siteMapper.map(dto, null))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITE.replace("{id}", entity.id.toString())}"
    }

    @GetMapping(Route.V1.WEB_UPDATE_SITE_PAGE)
    override fun updatePage(@PathVariable("id") id: Long, model: Model): String {
        val entity = this.siteService.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
        val pages = this.pageService.searchForSite(
            entity.id, PageableParams.of(null, 0, 30, SortByFields.ID, Sort.Direction.DESC)
        )
        model.addAttribute("site", this.siteMapper.map(entity))
        model.addAttribute("pages", pages.map { this.pageMapper.map(it) })
        return "sites/fragments/create"
    }

    @PostMapping(Route.V1.WEB_UPDATE_SITE)
    override fun update(
        @PathVariable("id") id: Long,
        @Valid @ModelAttribute dto: SiteDto,
        redirectAttributes: RedirectAttributes
    ): String {
        var entity = this.siteService.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
        entity = this.siteService.save(this.siteMapper.map(dto, entity))
        redirectAttributes.addFlashAttribute("message", "Success!!")
        return "redirect:${Route.V1.WEB_FIND_SITE.replace("{id}", entity.id.toString())}"
    }

    @PostMapping(Route.V1.WEB_DELETE_SITE)
    override fun delete(
        @PathVariable("id") id: Long,
        redirectAttributes: RedirectAttributes
    ): String {
        this.siteService.delete(id, true)
        redirectAttributes.addFlashAttribute("message", "Deleted!!")
        return "redirect:${Route.V1.WEB_SEARCH_SITES}";
    }

}
