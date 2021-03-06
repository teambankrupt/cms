package com.example.cms.domains.contenttemplates.models.entities

import com.example.cms.domains.contenttemplates.models.enums.TemplateTypes
import com.example.coreweb.domains.base.entities.BaseEntity
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "content_templates", schema = "cms")
class ContentTemplate : BaseEntity() {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    lateinit var type: TemplateTypes

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    lateinit var content: String

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "c_template_placeholders", schema = "cms")
    lateinit var placeholders: Set<String>
}
