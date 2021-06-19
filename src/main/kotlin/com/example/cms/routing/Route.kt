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
        }
    }
}
