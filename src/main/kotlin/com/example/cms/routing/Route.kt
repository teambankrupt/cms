package com.example.cms.routing

class Route {
    class V1 {
        companion object {
            private const val API = "/api"
            private const val VERSION = "/v1"
            private const val VERSION_V2 = "/v2"
            private const val ADMIN = "/admin"

            const val UPLOAD_IMAGE="$API$VERSION_V2/images"
            const val UPLOAD_IMAGE_BULK="$API$VERSION_V2/images/bulk"

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
            const val ADMIN_SEARCH_PREPAREDCONTENTS = "$ADMIN/preparedcontents"
            const val ADMIN_CREATE_PREPAREDCONTENT_PAGE = "$ADMIN/preparedcontents/create"
            const val ADMIN_CREATE_PREPAREDCONTENT = "$ADMIN/preparedcontents"
            const val ADMIN_FIND_PREPAREDCONTENT = "$ADMIN/preparedcontents/{id}"
            const val ADMIN_UPDATE_PREPAREDCONTENT_PAGE = "$ADMIN/preparedcontents/{id}/update"
            const val ADMIN_UPDATE_PREPAREDCONTENT = "$ADMIN/preparedcontents/{id}"
            const val ADMIN_DELETE_PREPAREDCONTENT = "$ADMIN/preparedcontents/{id}/delete"

            // PreparedContents
            const val SEARCH_PREPAREDCONTENTS = "$API$VERSION/preparedcontents"
            const val CREATE_PREPAREDCONTENT = "$API$VERSION/preparedcontents"
            const val FIND_PREPAREDCONTENT = "$API$VERSION/preparedcontents/{id}"
            const val UPDATE_PREPAREDCONTENT = "$API$VERSION/preparedcontents/{id}"
            const val DELETE_PREPAREDCONTENT = "$API$VERSION/preparedcontents/{id}"
        }
    }
}
