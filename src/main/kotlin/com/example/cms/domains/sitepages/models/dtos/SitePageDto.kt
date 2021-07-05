package com.example.cms.domains.sitepages.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class SitePageDto : BaseDto() {

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("slug")
    var slug: String? = null

    @JsonProperty("description")
    var description: String? = null

}
