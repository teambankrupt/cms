package com.example.cms.domains.preparedcontents.services.beans

import com.example.cms.domains.preparedcontents.models.entities.PreparedContent
import com.example.cms.domains.preparedcontents.repositories.PreparedContentRepository
import com.example.cms.domains.preparedcontents.services.PreparedContentService
import com.example.common.utils.ExceptionUtil
import com.example.coreweb.utils.PageAttr
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import java.util.*
import com.example.coreweb.utils.PageableParams

@Service
class PreparedContentServiceBean @Autowired constructor(
    private val preparedContentRepository: PreparedContentRepository
) : PreparedContentService {

    override fun search(params: PageableParams): Page<PreparedContent> {
        return this.preparedContentRepository.search(params.query, PageAttr.getPageRequest(params))
    }

    override fun save(entity: PreparedContent): PreparedContent {
        this.validate(entity)
        return this.preparedContentRepository.save(entity)
    }

    override fun find(id: Long): Optional<PreparedContent> {
        return this.preparedContentRepository.find(id)
    }

    override fun delete(id: Long, softDelete: Boolean) {
        if (softDelete) {
            val entity = this.find(id).orElseThrow { ExceptionUtil.notFound("PreparedContent", id) }
            entity.isDeleted = true
            this.preparedContentRepository.save(entity)
        }
        this.preparedContentRepository.deleteById(id)
    }

    override fun validate(entity: PreparedContent) {
    }
}
