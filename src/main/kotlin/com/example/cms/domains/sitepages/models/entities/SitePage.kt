package com.example.cms.domains.sitepages.models.entities

import com.example.cms.domains.sites.models.entities.Site
import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "site_pages", schema = "cms")
class SitePage : BaseEntity() {

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "slug", nullable = false)
    lateinit var slug: String

    @Column(name = "description")
    var description: String? = null

    @JoinColumn(name = "content", nullable = false)
    lateinit var content: String

    @ManyToOne(optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    lateinit var site: Site
}
