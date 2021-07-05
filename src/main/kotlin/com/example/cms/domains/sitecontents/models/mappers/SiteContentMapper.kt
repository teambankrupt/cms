package com.example.cms.domains.sitecontents.models.mappers

import com.example.cms.domains.sitecontents.models.dtos.SiteContentDto
import com.example.cms.domains.sitecontents.models.entities.SiteContent
import com.example.cms.domains.sites.models.entities.Site
import com.example.cms.domains.sites.repositories.SiteRepository
import com.example.common.utils.ExceptionUtil
import com.example.common.utils.TextUtility
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SiteContentMapper @Autowired constructor(
    private val siteRepository: SiteRepository
) : BaseMapper<SiteContent, SiteContentDto> {

    override fun map(entity: SiteContent): SiteContentDto {
        val dto = SiteContentDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.title = entity.title
            this.slug = entity.slug
            this.content = entity.content
            this.published = entity.published
            this.publishedOn = entity.publishedOn
            this.siteId = entity.site.id
        }

        return dto
    }

    override fun map(dto: SiteContentDto, exEntity: SiteContent?): SiteContent {
        val entity = exEntity ?: SiteContent()

        entity.apply {
            this.title = dto.title
            this.slug = TextUtility.removeSpecialCharacters(
                if (!dto.slug.isNullOrBlank()) dto.slug!! else
                    dto.title.trim().lowercase().replace(" ", "-")
            )
            this.content = dto.content
            this.published = dto.published
            this.publishedOn = if (dto.published) dto.publishedOn ?: Instant.now() else null
            this.site = siteRepository.find(dto.siteId)
                .orElseThrow { ExceptionUtil.notFound(Site::class.java, dto.siteId) }
        }

        return entity
    }
}
