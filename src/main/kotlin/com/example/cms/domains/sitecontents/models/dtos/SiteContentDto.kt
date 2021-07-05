package com.example.cms.domains.sitecontents.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import java.time.Instant
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

class SiteContentDto : BaseDto() {

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("slug")
    var slug: String? = null

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("content")
    lateinit var content: String

    @JsonProperty("published_on")
    var publishedOn: Instant? = null

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("published")
    var published: Boolean = false

    @ApiModelProperty(required = true)
    @JsonProperty("site_id")
    @Min(1)
    var siteId: Long = 0

}
