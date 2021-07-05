package com.example.cms.domains.sites.models.mappers

import com.example.auth.config.security.SecurityContext
import com.example.auth.entities.User
import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sitepages.repositories.SitePageRepository
import com.example.cms.domains.sites.models.dtos.SiteDto
import com.example.cms.domains.sites.models.entities.Site
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.TextUtility
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SiteMapper @Autowired constructor(
    private val pageRepository: SitePageRepository
) : BaseMapper<Site, SiteDto> {

    @Value("\${app.domain}")
    lateinit var appDomain: String

    override fun map(entity: Site): SiteDto {
        val dto = SiteDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.title = entity.title
            this.domain = entity.domain
            this.tagline = entity.tagline
            this.description = entity.description
            this.homePageId = entity.homePage?.id
            this.contentPageId = entity.contentPage?.id

            this.ownerId = entity.owner.id
            this.ownerName = entity.owner.name
            this.homePageTitle = entity.homePage?.title
            this.contentPageTitle = entity.contentPage?.title
        }

        return dto
    }

    override fun map(dto: SiteDto, exEntity: Site?): Site {
        val entity = exEntity ?: Site()

        entity.apply {
            this.title = dto.title
            this.domain = if (!dto.domain.isNullOrBlank()) dto.domain!! else {
                val subdomain = TextUtility.removeSpecialCharacters(
                    dto.title.trim().lowercase().replace(" ", "-")
                )
                subdomain + ".${appDomain}"
            }
            this.description = dto.description
            this.tagline = dto.tagline

            this.homePage = dto.homePageId?.let {
                pageRepository.find(it).orElseThrow { ExceptionUtil.notFound(SitePage::class.java, it) }
            }
            this.contentPage = dto.contentPageId?.let {
                pageRepository.find(it).orElseThrow { ExceptionUtil.notFound(SitePage::class.java, it) }
            }

            this.owner = User(SecurityContext.getCurrentUser())
        }

        return entity
    }
}
