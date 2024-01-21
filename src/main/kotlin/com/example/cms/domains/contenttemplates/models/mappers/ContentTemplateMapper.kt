package com.example.cms.domains.contenttemplates.models.mappers

import com.example.cms.domains.contenttemplates.models.dtos.ContentTemplateDto
import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.common.misc.Commons
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import com.example.coreweb.domains.base.models.mappers.BaseMapperV2
import org.springframework.stereotype.Component
import java.util.regex.Matcher
import java.util.regex.Pattern


@Component
class ContentTemplateMapper : BaseMapperV2<ContentTemplate, ContentTemplateDto> {

    override fun map(entity: ContentTemplate): ContentTemplateDto {
        val dto = ContentTemplateDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.type = entity.type
            this.title = entity.title
            this.code = entity.code
            this.content = entity.content
            this.cssClasses = entity.cssClasses
            this.placeholders = entity.placeholders
        }

        return dto
    }

    override fun map(dto: ContentTemplateDto, exEntity: ContentTemplate?): ContentTemplate {
        val entity = exEntity ?: ContentTemplate()

        entity.apply {
            this.type = dto.type
            this.title = dto.title
            this.code = dto.code
            this.content = dto.content
            this.cssClasses = dto.cssClasses
            this.placeholders = Commons.matchPlaceholders(this.content)
        }

        return entity
    }
}
