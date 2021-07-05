package com.example.cms.domains.sites.models.entities

import com.example.auth.config.security.SecurityContext
import com.example.auth.entities.User
import com.example.cms.domains.sitepages.models.entities.SitePage
import com.example.coreweb.domains.base.entities.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "sites", schema = "cms")
class Site : BaseEntity() {

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "domain", nullable = false)
    lateinit var domain: String

    @Column(name = "tagline", nullable = false)
    lateinit var tagline: String

    @Column(name = "description")
    var description: String? = null

    @OneToOne(optional = true)
    @JoinColumn(name = "home_page_id")
    var homePage: SitePage? = null

    @OneToOne(optional = true)
    @JoinColumn(name = "content_page_id")
    var contentPage: SitePage? = null

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    lateinit var owner: User

    fun canAccess(): Boolean {
        val auth = SecurityContext.getCurrentUser()
        return auth.isAdmin
                || this.owner.id == auth.id
    }
}
