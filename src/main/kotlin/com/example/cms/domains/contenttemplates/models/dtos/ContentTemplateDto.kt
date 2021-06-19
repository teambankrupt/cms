package com.example.cms.domains.contenttemplates.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class ContentTemplateDto : BaseDto() {

    @NotBlank
    @JsonProperty("title")
    @ApiModelProperty(required = true)
    lateinit var title: String

    @NotBlank
    @JsonProperty("content")
    @ApiModelProperty(required = true)
    lateinit var content: String

    /*
    READONLY PROPERTIES
     */

    @JsonProperty("placeholders")
    @ApiModelProperty(readOnly = true)
    var placeholders: Set<String> = HashSet()

}
