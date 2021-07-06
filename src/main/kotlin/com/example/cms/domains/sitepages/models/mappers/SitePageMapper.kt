package com.example.cms.domains.sitepages.models.mappers

import com.example.cms.domains.sitepages.models.dtos.SitePageDto
import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sites.models.entities.Site
import com.example.cms.domains.sites.repositories.SiteRepository
import com.example.common.misc.Commons
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.TextUtility
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SitePageMapper @Autowired constructor(
    private val siteRepository: SiteRepository
) : BaseMapper<SitePage, SitePageDto> {

    override fun map(entity: SitePage): SitePageDto {
        val dto = SitePageDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.title = entity.title
            this.slug = entity.slug
            this.description = entity.description

            this.content = entity.content
            this.siteId = entity.site.id

            this.summary = Commons.summary(entity.content,200)
        }

        return dto
    }

    override fun map(dto: SitePageDto, exEntity: SitePage?): SitePage {
        val entity = exEntity ?: SitePage()

        entity.apply {
            this.title = dto.title
            this.slug = TextUtility.removeSpecialCharacters(
                if (!dto.slug.isNullOrBlank()) dto.slug!! else
                    dto.title.trim().lowercase().replace(" ", "-")
            )
            this.description = dto.description
            this.content = dto.content
            this.site = siteRepository.find(dto.siteId)
                .orElseThrow { ExceptionUtil.notFound(Site::class.java, dto.siteId) }
        }

        return entity
    }
}
