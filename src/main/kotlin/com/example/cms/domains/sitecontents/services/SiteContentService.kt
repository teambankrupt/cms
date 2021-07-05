package com.example.cms.domains.sitecontents.services

import com.example.cms.domains.sitecontents.models.entities.SiteContent
import com.example.coreweb.domains.base.services.CrudServiceV3
import com.example.coreweb.utils.PageableParams
import org.springframework.data.domain.Page

interface SiteContentService : CrudServiceV3<SiteContent> {
    fun searchForSite(siteId: Long, params: PageableParams): Page<SiteContent>
}
