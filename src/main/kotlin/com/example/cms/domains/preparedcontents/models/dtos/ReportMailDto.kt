package com.example.cms.domains.preparedcontents.models.dtos

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ReportMailDto {
    @NotNull
    @NotEmpty
    lateinit var to: Array<String>

    @NotBlank
    lateinit var subject: String

    @NotBlank
    lateinit var body: String
}
