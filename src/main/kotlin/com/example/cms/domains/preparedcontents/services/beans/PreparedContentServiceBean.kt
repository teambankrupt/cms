package com.example.cms.domains.preparedcontents.services.beans

import com.example.auth.config.security.SecurityContext
import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.cms.domains.preparedcontents.models.entities.DYNAMIC_CONTENT_KEY
import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.cms.domains.preparedcontents.repositories.PreparedContentRepository
import com.example.cms.domains.preparedcontents.services.PreparedContentService
import com.example.cms.routing.Route
import com.example.common.misc.Commons
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import com.example.coreweb.utils.PageableParams
import dev.sayem.jsontotable.HtmlTable
import org.springframework.beans.factory.annotation.Value

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
        templateId: Long,
        title: String,
        placeholderValues: Map<String, String>
    ): String {
        val template = this.templateRepository.find(templateId)
            .orElseThrow { ExceptionUtil.notFound(ContentTemplate::class.java, templateId) }
        var content = PreparedContent()
        content.template = template
        content.title = title
        content.placeholderValues = placeholderValues.toMutableMap()
        content.resolvedContent = Commons.replacePlaceholders(template.content, placeholderValues)
        content.status = ContentStatuses.FINALIZED
        content = this.save(content)
        return "${this.baseUrl}${Route.V1.PREPAREDCONTENT_CONTENT_HTML.replace("{id}", content.id.toString())}" +
                "?access_token=${SecurityContext.getToken()}"
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
            if (it.key == DYNAMIC_CONTENT_KEY) {
                HtmlTable.fromJson(it.value, entity.cssClasses.split(" "))
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
