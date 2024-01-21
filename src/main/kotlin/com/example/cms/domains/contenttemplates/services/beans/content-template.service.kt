package com.example.cms.domains.contenttemplates.services.beans

import com.example.auth.entities.UserAuth
import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.cms.domains.contenttemplates.repositories.ContentTemplateRepository
import com.example.common.exceptions.toArrow
import com.example.common.validation.ValidationScope
import com.example.common.validation.ValidationV2
import com.example.common.validation.genericValidation
import com.example.coreweb.domains.base.services.CrudServiceV5
import com.example.coreweb.utils.PageAttr
import com.example.coreweb.utils.PageableParams
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service

interface ContentTemplateService : CrudServiceV5<ContentTemplate> {
    fun search(params: PageableParams): Page<ContentTemplate>
}

@Service
class ContentTemplateServiceBean @Autowired constructor(
    private val contentTemplateRepository: ContentTemplateRepository
) : ContentTemplateService {

    override fun search(params: PageableParams): Page<ContentTemplate> {
        return this.contentTemplateRepository.search(params.query, PageAttr.getPageRequest(params))
    }

    override fun validations(asUser: UserAuth): Set<ValidationV2<ContentTemplate>> = setOf(
        uniqueCodeCreateValidation, uniqueCodeUpdateValidation
    )

    private val uniqueCodeCreateValidation = genericValidation<ContentTemplate>(
        scopes = setOf(ValidationScope.Write),
        message = "Code must be unique",
    ) { template -> this.contentTemplateRepository.findByCode(template.code).isPresent.not() }

    private val uniqueCodeUpdateValidation = genericValidation<ContentTemplate>(
        scopes = setOf(ValidationScope.Modify),
        message = "Code must be unique",
    ) { template ->
        this.contentTemplateRepository.findByCode(template.code).toArrow().fold(
            { true },
            { it.id == template.id }
        )
    }

    override fun getRepository(): JpaRepository<ContentTemplate, Long> = this.contentTemplateRepository
}
