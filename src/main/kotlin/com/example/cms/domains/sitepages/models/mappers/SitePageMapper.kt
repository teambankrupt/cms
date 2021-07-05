package com.example.cms.domains.sitepages.models.mappers

import com.example.cms.domains.sitepages.models.dtos.SitePageDto
import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.stereotype.Component

@Component
class SitePageMapper : BaseMapper<SitePage, SitePageDto> {

    override fun map(entity: SitePage): SitePageDto {
        val dto = SitePageDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.title = entity.title
            this.slug = entity.slug
            this.description = entity.description
        }

        return dto
    }

    override fun map(dto: SitePageDto, exEntity: SitePage?): SitePage {
        val entity = exEntity ?: SitePage()

        entity.apply {
            this.title = dto.title
            this.slug = if (!dto.slug.isNullOrBlank()) dto.slug!! else
                dto.title.trim().lowercase().replace(" ", "-")
            this.description = dto.description
        }

        return entity
    }
}
