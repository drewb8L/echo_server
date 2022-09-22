package com.babcock.http

enum class HttpStatusCode(val STATUS_CODE: Int, val MESSAGE: String = "Bad Request") {
    SUCCESS_200_OK(200, "OK"),

    CLIENT_ERROR_400_BAD_REQUEST(400, "Bad Request"),
    CLIENT_ERROR_404_NOT_FOUND(404, "Not Found"),
    CLIENT_ERROR_405_METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CLIENT_ERROR_414_BAD_REQUEST(414, "URI Too Long"),

    SERVER_ERROR_500_INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVER_ERROR_501_NOT_IMPLEMENTED(501, "Not Implemented"),
    SERVER_ERROR_505_INTERNAL_SERVER_ERROR(505, "Version Not Supported")


}