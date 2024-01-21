package com.example.cms.domains.contenttemplates.models.entities

import com.example.cms.domains.contenttemplates.models.enums.TemplateTypes
import com.example.coreweb.domains.base.entities.BaseEntityV2
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "content_templates", schema = "cms")
class ContentTemplate : BaseEntityV2() {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    lateinit var type: TemplateTypes

    @Column(name = "title", nullable = false)
    lateinit var title: String

    @Column(name = "code", nullable = false, unique = true)
    lateinit var code: String

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    lateinit var content: String

    @Column(name = "css_classes", nullable = true)
    var cssClasses: String = ""

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "c_template_placeholders", schema = "cms")
    lateinit var placeholders: Set<String>
}


sealed class DynamicContent(val key: String) {
    data class Json(val json: String) : DynamicContent("DYNAMIC_CONTENT_JSON")
    data class ListType(val headers: List<String>, val list: List<List<String>>) : DynamicContent("DYNAMIC_CONTENT")
}