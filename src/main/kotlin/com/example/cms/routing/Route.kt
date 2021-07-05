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
            const val WEB_PREPAREDCONTENT_CONTENT_SEND_MAIL = "/prepared-contents/{id}/send-email"

            // PreparedContents
            const val SEARCH_PREPAREDCONTENTS = "$API$VERSION/prepared-contents"
            const val CREATE_PREPAREDCONTENT = "$API$VERSION/prepared-contents"
            const val FIND_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"
            const val UPDATE_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"
            const val DELETE_PREPAREDCONTENT = "$API$VERSION/prepared-contents/{id}"

            const val PREPAREDCONTENT_CONTENT_HTML = "$API$VERSION/prepared-contents/{id}/html"
            const val PREPAREDCONTENT_CONTENT_PDF = "$API$VERSION/prepared-contents/{id}/pdf"
            const val PREPAREDCONTENT_CONTENT_IMG = "$API$VERSION/prepared-contents/{id}/img"


            // SitePages (Web)
            const val WEB_SEARCH_SITEPAGES = "/site-pages"
            const val WEB_CREATE_SITEPAGE_PAGE = "/site-pages/create"
            const val WEB_CREATE_SITEPAGE = "/site-pages"
            const val WEB_FIND_SITEPAGE = "/site-pages/{id}"
            const val WEB_UPDATE_SITEPAGE_PAGE = "/site-pages/{id}/update"
            const val WEB_UPDATE_SITEPAGE = "/site-pages/{id}"
            const val WEB_DELETE_SITEPAGE = "/site-pages/{id}/delete"

            // SitePages
            const val SEARCH_SITEPAGES = "$API$VERSION/site-pages"
            const val CREATE_SITEPAGE = "$API$VERSION/site-pages"
            const val FIND_SITEPAGE = "$API$VERSION/site-pages/{id}"
            const val UPDATE_SITEPAGE = "$API$VERSION/site-pages/{id}"
            const val DELETE_SITEPAGE = "$API$VERSION/site-pages/{id}"

            // Sites (Web)
            const val WEB_SEARCH_SITES = "/sites"
            const val WEB_CREATE_SITE_PAGE = "/sites/create"
            const val WEB_CREATE_SITE = "/sites"
            const val WEB_FIND_SITE = "/sites/{id}"
            const val WEB_UPDATE_SITE_PAGE = "/sites/{id}/update"
            const val WEB_UPDATE_SITE = "/sites/{id}"
            const val WEB_DELETE_SITE = "/sites/{id}/delete"

            // Sites
            const val SEARCH_SITES = "$API$VERSION/sites"
            const val CREATE_SITE = "$API$VERSION/sites"
            const val FIND_SITE = "$API$VERSION/sites/{id}"
            const val UPDATE_SITE = "$API$VERSION/sites/{id}"
            const val DELETE_SITE = "$API$VERSION/sites/{id}"

            // SiteContents
            const val SEARCH_SITECONTENTS = "$API$VERSION/site-contents"
            const val CREATE_SITECONTENT = "$API$VERSION/site-contents"
            const val FIND_SITECONTENT = "$API$VERSION/site-contents/{id}"
            const val UPDATE_SITECONTENT = "$API$VERSION/site-contents/{id}"
            const val DELETE_SITECONTENT = "$API$VERSION/site-contents/{id}"

            // SiteContents (Web)
            const val WEB_SEARCH_SITECONTENTS = "/site-contents"
            const val WEB_CREATE_SITECONTENT_PAGE = "/site-contents/create"
            const val WEB_CREATE_SITECONTENT = "/site-contents"
            const val WEB_FIND_SITECONTENT = "/site-contents/{id}"
            const val WEB_UPDATE_SITECONTENT_PAGE = "/site-contents/{id}/update"
            const val WEB_UPDATE_SITECONTENT = "/site-contents/{id}"
            const val WEB_DELETE_SITECONTENT = "/site-contents/{id}/delete"
        }
    }
}
