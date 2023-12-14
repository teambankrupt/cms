package com.example.cms.domains.sites.services.beans

import com.example.auth.config.security.SecurityContext
import com.example.cms.domains.sites.models.entities.Site
import com.example.cms.domains.sites.repositories.SiteRepository
import com.example.cms.domains.sites.services.SiteService
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.SessionIdentifierGenerator
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class SiteServiceBean @Autowired constructor(
    private val siteRepository: SiteRepository
) : SiteService {

    override fun search(params: PageableParams): Page<Site> {
        val auth = SecurityContext.getCurrentUser()
        return this.siteRepository.search(auth.id, params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: Site): Site {
        this.validate(entity)
        entity.domain = this.checkAndRegenerateDomain(entity.domain)
        return this.siteRepository.save(entity)
    }

    override fun find(id: Long): Optional<Site> {
        return this.siteRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("Site", id) }
            entity.isDeleted = true
            this.siteRepository.save(entity)
            return
        }
        this.siteRepository.deleteById(id)
    }

    override fun validate(entity: Site) {
        if (!entity.canAccess()) throw ExceptionUtil.forbidden("You're not allowed to access this resource!")
    }

    private fun checkAndRegenerateDomain(domain: String): String {
        val content = this.siteRepository.findByDomainIncludingDeleted(domain)
        if (!content.isPresent) return domain
        return checkAndRegenerateDomain("${SessionIdentifierGenerator.alphanumeric(6).lowercase()}-${domain}")
    }
}
