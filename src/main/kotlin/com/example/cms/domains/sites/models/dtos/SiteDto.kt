package com.example.cms.domains.sites.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class SiteDto : BaseDto() {

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("title")
    var domain: String? = null

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("tagline")
    lateinit var tagline: String

    @JsonProperty("description")
    var description: String? = null

    @JsonProperty("home_page_id")
    var homePageId: Long? = null

    @JsonProperty("content_page_id")
    var contentPageId: Long? = null

    @ApiModelProperty(required = true)
    @Min(1)
    @JsonProperty("owner_id")
    var ownerId: Long = 0
}
