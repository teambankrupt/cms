package com.example.cms.domains.sites.models.dtos

import com.example.coreweb.domains.base.models.dtos.BaseDto
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty
import javax.validation.constraints.NotBlank

class SiteDto : BaseDto() {

    @ApiModelProperty(required = true)
    @NotBlank
    @JsonProperty("title")
    lateinit var title: String

    @JsonProperty("domain")
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

    /*
    READONLY
     */
    @ApiModelProperty(readOnly = true)
    @JsonProperty("owner_id")
    var ownerId: Long? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("owner_name")
    var ownerName: String? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("home_page_title")
    var homePageTitle: String? = null

    @ApiModelProperty(readOnly = true)
    @JsonProperty("content_page_title")
    var contentPageTitle: String? = null


}
