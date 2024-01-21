package com.example.cms.domains.preparedcontents.models.dtos

import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class PreparedContentDto : BaseDto() {

    @field:NotBlank
    @field:JsonProperty("title")
    @ApiModelProperty(required = true)
    lateinit var title: String

    @field:Min(1)
    @field:JsonProperty("template_id")
    @ApiModelProperty(required = true)
    var templateId: Long = 0

    @field:NotNull
    @field:JsonProperty("placeholder_values")
    @ApiModelProperty(required = true)
    var placeholderValues: HashMap<String, String> = HashMap()

    /*
    READONLY
     */
    @ApiModelProperty(readOnly = true)
    @field:JsonProperty("status")
    var status: ContentStatuses? = null

    @ApiModelProperty(readOnly = true)
    @field:JsonProperty("resolved_content")
    var resolvedContent: String? = null

    @ApiModelProperty(readOnly = true)
    @field:JsonProperty("template_title")
    var templateTitle: String? = null

    fun isFinalized(): Boolean {
        return this.status == ContentStatuses.FINALIZED
    }
}
