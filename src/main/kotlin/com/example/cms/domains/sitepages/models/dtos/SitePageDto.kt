package com.example.cms.domains.sitepages.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

class SitePageDto : BaseDto() {

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("slug")
    var slug: String? = null

    @JsonProperty("description")
    var description: String? = null

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("content")
    lateinit var content: String

    @ApiModelProperty(required = true)
    @JsonProperty("site_id")
    @NotNull
    @Min(1)
    var siteId: Long = 0

    /*
    READONLY
     */

    @ApiModelProperty(readOnly = true)
    @JsonProperty("summary")
    var summary: String? = null
}
