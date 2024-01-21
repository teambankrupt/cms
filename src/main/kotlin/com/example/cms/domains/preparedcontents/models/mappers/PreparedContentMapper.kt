package com.example.cms.domains.preparedcontents.models.mappers

import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.cms.domains.preparedcontents.models.dtos.PreparedContentDto
import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.domains.base.models.mappers.BaseMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

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
            this.placeholderValues = HashMap()
            entity.placeholderValues.forEach {
                this.placeholderValues[it.key] = it.value
            }

            this.status = entity.status
            this.resolvedContent = entity.resolvedContent
            this.templateTitle = entity.template.title

            if (this.placeholderValues.isNullOrEmpty()) {
                entity.template.placeholders.forEach {
                    this.placeholderValues[it] = ""
                }
            }
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
        }

        return entity
    }

}
