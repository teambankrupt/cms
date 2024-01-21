package com.example.cms.domains.preparedcontents.services.beans

import com.example.cms.domains.contenttemplates.models.entities.DynamicContent
import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.cms.domains.preparedcontents.repositories.PreparedContentRepository
import com.example.cms.domains.preparedcontents.services.PreparedContentService
import com.example.common.misc.Commons
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import dev.sayem.jsontotable.HtmlTable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*

@Service
class PreparedContentServiceBean @Autowired constructor(
    private val preparedContentRepository: PreparedContentRepository,
    private val templateRepository: ContentTemplateRepository
) : PreparedContentService {

    @Value("\${app.base-url-api}")
    lateinit var baseUrl: String

    override fun search(templateId: Long?, params: PageableParams): Page<PreparedContent> {
        return this.preparedContentRepository.search(params.query, templateId, PageAttr.getPageRequest(params))
    }

    override fun search(params: PageableParams): Page<PreparedContent> {
        return this.preparedContentRepository.search(params.query, null, PageAttr.getPageRequest(params))
    }

    override fun generateHtmlForTemplate(
        template: String,
        title: String,
        placeholderValues: LinkedHashMap<String, String>,
        dynamicContent: DynamicContent
    ): String {
        val cTemplate = this.templateRepository.findByCode(template)
            .orElseThrow { ExceptionUtil.notFound("Couldn't find template with code: $template") }
        var content = PreparedContent()
        content.template = cTemplate
        content.title = title
        content.placeholderValues = placeholderValues.toMutableMap()
        when(dynamicContent) {
            is DynamicContent.Json -> {
                content.placeholderValues[dynamicContent.key] = HtmlTable.fromJson(dynamicContent.json, cTemplate.cssClasses.split(" "))
            }
            is DynamicContent.ListType -> {
                content.placeholderValues[dynamicContent.key] = HtmlTable.toHtml(dynamicContent.headers, dynamicContent.list, cTemplate.cssClasses)
            }
        }
        content.resolvedContent = Commons.replacePlaceholders(cTemplate.content, content.placeholderValues)
        content.status = ContentStatuses.FINALIZED
        content = this.save(content)
        return content.resolvedContent
    }

    override fun changeStatus(contentId: Long, status: ContentStatuses): PreparedContent {
        val content =
            this.find(contentId).orElseThrow { ExceptionUtil.notFound(PreparedContent::class.java, contentId) }
        content.status = status
        return this.save(content)
    }

    override fun save(entity: PreparedContent): PreparedContent {
        this.validate(entity)
        val placeholders = entity.placeholderValues.mapValues {
            if (it.key == DynamicContent.Json::key.name) {
                HtmlTable.fromJson(it.value, entity.template.cssClasses.split(" "))
            } else {
                it.value
            }
        }
        entity.resolvedContent = Commons.replacePlaceholders(entity.template.content, placeholders)
        return this.preparedContentRepository.save(entity)
    }

    override fun find(id: Long): Optional<PreparedContent> {
        return this.preparedContentRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
            entity.isDeleted = true
            this.preparedContentRepository.save(entity)
            return
        }
        this.preparedContentRepository.deleteById(id)
    }

    override fun validate(entity: PreparedContent) {
    }
}
