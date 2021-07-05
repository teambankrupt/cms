package com.example.cms.domains.sitepages.services

import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.cms.domains.sites.models.entities.Site
import com.example.coreweb.domains.base.services.CrudServiceV3
import com.example.coreweb.utils.PageableParams
import org.springframework.data.domain.Page

interface SitePageService : CrudServiceV3<SitePage> {
    fun searchForSite(siteId: Long, params: PageableParams): Page<SitePage>
}
