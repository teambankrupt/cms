package com.example.cms.domains.preparedcontents.models.entities

import com.example.cms.domains.contenttemplates.models.entities.ContentTemplate
import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.coreweb.domains.base.entities.BaseEntity
import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import javax.persistence.*

@Entity
@Table(name = "prepared_contents", schema = "cms")
class PreparedContent : BaseEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    lateinit var template: ContentTemplate

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @MapKeyColumn(name = "placeholder")
    @Column(name = "ph_value", nullable = false)
    @JoinTable(name = "content_ph_values", schema = "cms")
    @JoinColumn(name = "prepare_content_id", nullable = false)
    lateinit var placeholderValues: MutableMap<String, String>

    @Column(name = "resolved_content", nullable = false)
    lateinit var resolvedContent: String

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    lateinit var status: ContentStatuses

}
