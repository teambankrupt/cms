package com.example.cms.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val VERSION_V2 = "/v2"
            private const val ADMIN = "/admin"

            const val UPLOAD_IMAGE = "$API$VERSION_V2/images"
            const val UPLOAD_IMAGE_BULK = "$API$VERSION_V2/images/bulk"

            // ContentTemplates (Admin)
            const val ADMIN_SEARCH_CONTENTTEMPLATES = "$ADMIN/content-templates"
            const val ADMIN_CREATE_CONTENTTEMPLATE_PAGE = "$ADMIN/content-templates/create"
            const val ADMIN_CREATE_CONTENTTEMPLATE = "$ADMIN/content-templates"
            const val ADMIN_FIND_CONTENTTEMPLATE = "$ADMIN/content-templates/{id}"
            const val ADMIN_UPDATE_CONTENTTEMPLATE_PAGE = "$ADMIN/content-templates/{id}/update"
            const val ADMIN_UPDATE_CONTENTTEMPLATE = "$ADMIN/content-templates/{id}"
            const val ADMIN_DELETE_CONTENTTEMPLATE = "$ADMIN/content-templates/{id}/delete"

            // ContentTemplates
            const val SEARCH_CONTENTTEMPLATES = "$API$VERSION/content-templates"
            const val CREATE_CONTENTTEMPLATE = "$API$VERSION/content-templates"
            const val FIND_CONTENTTEMPLATE = "$API$VERSION/content-templates/{id}"
            const val UPDATE_CONTENTTEMPLATE = "$API$VERSION/content-templates/{id}"
            const val DELETE_CONTENTTEMPLATE = "$API$VERSION/content-templates/{id}"

            // PreparedContents (Admin)
            const val ADMIN_SEARCH_PREPAREDCONTENTS = "$ADMIN/prepared-contents"
            const val ADMIN_CREATE_PREPAREDCONTENT_PAGE = "$ADMIN/prepared-contents/create"
            const val ADMIN_CREATE_PREPAREDCONTENT = "$ADMIN/prepared-contents"
            const val ADMIN_FIND_PREPAREDCONTENT = "$ADMIN/prepared-contents/{id}"
            const val ADMIN_UPDATE_PREPAREDCONTENT_PAGE = "$ADMIN/prepared-contents/{id}/update"
            const val ADMIN_UPDATE_PREPAREDCONTENT = "$ADMIN/prepared-contents/{id}"
            const val ADMIN_DELETE_PREPAREDCONTENT = "$ADMIN/prepared-contents/{id}/delete"
            const val ADMIN_PREPAREDCONTENT_CHANGE_STATUS = "$ADMIN/prepared-contents/{id}/change-status"

            const val WEB_PREPAREDCONTENT_CONTENT_HTML = "/p/prepared-contents/{id}/html"
            const val WEB_PREPAREDCONTENT_CONTENT_PDF = "/prepared-contents/{id}/pdf"
            const val WEB_PREPAREDCONTENT_CONTENT_IMG = "/prepared-contents/{id}/img"

            // PreparedContents
            const val SEARCH_PREPAREDCONTENTS = "$API$VERSION/prepared-contents"
            const val CREATE_PREPAREDCONTENT = "$API$VERSION/prepared-contents"
            const val FIND_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"
            const val UPDATE_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"
            const val DELETE_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"

            const val PREPAREDCONTENT_CONTENT_HTML = "$API$VERSION/prepared-contents/{id}/html"
            const val PREPAREDCONTENT_CONTENT_PDF = "$API$VERSION/prepared-contents/{id}/pdf"
            const val PREPAREDCONTENT_CONTENT_IMG = "$API$VERSION/prepared-contents/{id}/img"
        }
    }
}
