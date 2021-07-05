package com.example.cms.domains.sitecontents.models.entities

import com.example.cms.domains.sites.models.entities.Site
import com.example.coreweb.domains.base.entities.BaseEntity
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "site_contents", schema = "cms")
class SiteContent : BaseEntity() {

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "slug", nullable = false)
    lateinit var slug: String

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    lateinit var content: String

    @Column(name = "published_on")
    var publishedOn: Instant? = null

    @Column(name = "published")
    var published: Boolean = false

    @ManyToOne(optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    lateinit var site: Site

}
