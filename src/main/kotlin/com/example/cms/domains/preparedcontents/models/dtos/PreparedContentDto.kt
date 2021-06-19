package com.example.cms.domains.preparedcontents.models.dtos

import com.example.cms.domains.preparedcontents.models.ContentStatuses
import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class PreparedContentDto : BaseDto() {

    @NotBlank
    @JsonProperty("title")
    @ApiModelProperty(required = true)
    lateinit var title: String

    @Min(1)
    @JsonProperty("template_id")
    @ApiModelProperty(required = true)
    var templateId: Long = 0

    @NotNull
    @NotEmpty
    @JsonProperty("placeholder_values")
    @ApiModelProperty(required = true)
    lateinit var placeholderValues: MutableMap<String, String>

    /*
    READONLY
     */
    @ApiModelProperty(readOnly = true)
    @JsonProperty("status")
    var status: ContentStatuses? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("resolved_content")
    var resolvedContent: String? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("template_title")
    var templateTitle: String? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("template_placeholders")
    var templatePlaceholders: Set<String>? = null


}
