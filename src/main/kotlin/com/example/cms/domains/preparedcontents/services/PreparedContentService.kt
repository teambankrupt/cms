package com.example.cms.domains.preparedcontents.services

import com.example.cms.domains.contenttemplates.models.entities.DynamicContent
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.coreweb.domains.base.services.CrudServiceV3
import com.example.coreweb.utils.PageableParams
import org.springframework.data.domain.Page

interface PreparedContentService : CrudServiceV3<PreparedContent> {
    fun search(templateId: Long?, params: PageableParams): Page<PreparedContent>
    fun generateHtmlForTemplate(
        template: String,
        title: String,
        placeholderValues: LinkedHashMap<String, String>,
        dynamicContent: DynamicContent
    ): String

    fun changeStatus(contentId: Long, status: ContentStatuses): PreparedContent
}
