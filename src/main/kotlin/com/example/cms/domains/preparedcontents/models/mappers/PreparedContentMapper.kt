package com.example.cms.domains.preparedcontents.models.mappers

import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.cms.domains.preparedcontents.models.dtos.PreparedContentDto
import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.*

@Component
class PreparedContentMapper @Autowired constructor(
    private val templateRepository: ContentTemplateRepository
) : BaseMapper<PreparedContent, PreparedContentDto> {

    override fun map(entity: PreparedContent): PreparedContentDto {
        val dto = PreparedContentDto()

        dto.apply {
            this.id = entity.id
            this.createdAt = entity.createdAt
            this.updatedAt = entity.updatedAt

            this.title = entity.title
            this.templateId = entity.template.id
            this.placeholderValues = entity.placeholderValues

            this.status = entity.status
            this.resolvedContent = entity.resolvedContent
            this.templateTitle = entity.template.title
            this.templatePlaceholders = entity.template.placeholders
        }

        return dto
    }

    override fun map(dto: PreparedContentDto, exEntity: PreparedContent?): PreparedContent {
        val entity = exEntity ?: PreparedContent()

        entity.apply {
            this.title = dto.title
            this.status = ContentStatuses.DRAFT
            this.placeholderValues = dto.placeholderValues
            this.template = templateRepository.find(dto.templateId)
                .orElseThrow { ExceptionUtil.notFound(PreparedContent::class.java, dto.templateId) }
            this.resolvedContent = parseContent(dto.placeholderValues, this.template.content)
        }

        return entity
    }

    private fun parseContent(placeholderValues: MutableMap<String, String>, content: String): String {
        var text: String = content
        for (ph in placeholderValues.entries) {
            // replace placeholders with capture groups
            text = text.replace("[" + ph.key + "]", ph.value)
        }
        return text
    }
}
