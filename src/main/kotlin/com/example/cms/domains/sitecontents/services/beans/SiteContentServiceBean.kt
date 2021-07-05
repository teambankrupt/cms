package com.example.cms.domains.sitecontents.services.beans

import com.example.cms.domains.sitecontents.models.entities.SiteContent
import com.example.cms.domains.sitecontents.repositories.SiteContentRepository
import com.example.cms.domains.sitecontents.services.SiteContentService
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.SessionIdentifierGenerator
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class SiteContentServiceBean @Autowired constructor(
    private val siteContentRepository: SiteContentRepository
) : SiteContentService {

    override fun search(params: PageableParams): Page<SiteContent> {
        return this.siteContentRepository.search(params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: SiteContent): SiteContent {
        this.validate(entity)
        // regenerate slug in case not unique
        entity.slug = this.checkAndRegenerateSlug(entity.slug)

        return this.siteContentRepository.save(entity)
    }

    override fun find(id: Long): Optional<SiteContent> {
        return this.siteContentRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("SiteContent", id) }
            entity.isDeleted = true
            this.siteContentRepository.save(entity)
        }
        this.siteContentRepository.deleteById(id)
    }

    override fun validate(entity: SiteContent) {
        if (entity.isNew) {
            val content = this.siteContentRepository.findBySlugIncludingDeleted(entity.slug)
            if (content.isPresent) throw ExceptionUtil.exists("Slug already exists: ${entity.slug}")
        }
        if (!entity.site.canAccess())
            throw ExceptionUtil.forbidden("You're not authorized to create content to this site!")
    }

    private fun checkAndRegenerateSlug(slug: String): String {
        val content = this.siteContentRepository.findBySlugIncludingDeleted(slug)
        if (!content.isPresent) return slug
        return checkAndRegenerateSlug("${slug}-${SessionIdentifierGenerator.alphanumeric(6)}")
    }
}
