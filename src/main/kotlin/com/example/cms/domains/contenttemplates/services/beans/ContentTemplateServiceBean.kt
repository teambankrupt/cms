package com.example.cms.domains.contenttemplates.services.beans

import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.cms.domains.contenttemplates.services.ContentTemplateService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import com.example.coreweb.utils.PageableParams

@Service
class ContentTemplateServiceBean @Autowired constructor(
    private val contentTemplateRepository: ContentTemplateRepository
) : ContentTemplateService {

    override fun search(params: PageableParams): Page<ContentTemplate> {
        return this.contentTemplateRepository.search(params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: ContentTemplate): ContentTemplate {
        this.validate(entity)
        return this.contentTemplateRepository.save(entity)
    }

    override fun find(id: Long): Optional<ContentTemplate> {
        return this.contentTemplateRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("ContentTemplate", id) }
            entity.isDeleted = true
            this.contentTemplateRepository.save(entity)
            return
        }
        this.contentTemplateRepository.deleteById(id)
    }

    override fun validate(entity: ContentTemplate) {
    }
}
