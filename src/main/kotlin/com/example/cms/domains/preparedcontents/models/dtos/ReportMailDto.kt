package com.example.cms.domains.preparedcontents.models.dtos

import com.example.cms.domains.preparedcontents.models.enums.ReportTypes
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ReportMailDto {
    @NotNull
    lateinit var reportType: ReportTypes

    @NotNull
    @NotEmpty
    lateinit var to: Array<String>

    @NotBlank
    lateinit var subject: String

    @NotBlank
    lateinit var body: String
}
