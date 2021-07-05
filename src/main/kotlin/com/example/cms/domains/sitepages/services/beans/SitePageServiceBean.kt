package com.example.cms.domains.sitepages.services.beans

import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sitepages.repositories.SitePageRepository
import com.example.cms.domains.sitepages.services.SitePageService
import com.example.common.utils.ExceptionUtil
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

    override fun search(params: PageableParams): Page<SitePage> {
        return this.sitePageRepository.search(params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: SitePage): SitePage {
        this.validate(entity)
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
        }
        this.sitePageRepository.deleteById(id)
    }

    override fun validate(entity: SitePage) {
        TODO("Not yet implemented")
    }
}
