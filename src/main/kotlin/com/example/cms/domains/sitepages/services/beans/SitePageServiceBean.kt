package com.example.cms.domains.sitepages.services.beans

import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sitepages.repositories.SitePageRepository
import com.example.cms.domains.sitepages.services.SitePageService
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.SessionIdentifierGenerator
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class SitePageServiceBean @Autowired constructor(
    private val sitePageRepository: SitePageRepository
) : SitePageService {

    override fun searchForSite(siteId: Long, params: PageableParams): Page<SitePage> {
        return this.sitePageRepository.search(siteId, params.query, PageAttr.getPageRequest(params))
    }

    override fun search(params: PageableParams): Page<SitePage> {
        return this.sitePageRepository.search(null, params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: SitePage): SitePage {
        this.validate(entity)
        entity.slug = this.checkAndRegenerateSlug(entity.slug)
        return this.sitePageRepository.save(entity)
    }

    override fun find(id: Long): Optional<SitePage> {
        return this.sitePageRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("SitePage", id) }
            entity.isDeleted = true
            this.sitePageRepository.save(entity)
            return
        }
        this.sitePageRepository.deleteById(id)
    }

    override fun validate(entity: SitePage) {
        if (entity.isNew) {
            val content = this.sitePageRepository.findBySlugIncludingDeleted(entity.slug)
            if (content.isPresent) throw ExceptionUtil.exists("Page slug isn't available. Please use a different one!")
        }
        if (!entity.site.canAccess()) throw ExceptionUtil.forbidden("You aren't authorized to access this resource!")
    }

    private fun checkAndRegenerateSlug(slug: String): String {
        val content = this.sitePageRepository.findBySlugIncludingDeleted(slug)
        if (!content.isPresent) return slug
        return checkAndRegenerateSlug("${slug}-${SessionIdentifierGenerator.alphanumeric(6).lowercase()}")
    }
}
