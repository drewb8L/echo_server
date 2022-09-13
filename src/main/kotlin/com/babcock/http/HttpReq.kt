package com.babcock.http

import java.util.Collections


class HttpReq: HttpMsg() {
    lateinit var method:HttpMethod
    var httpVersion: String = ""

    var compatibleHttpVersion: String = ""
        set(value) {
            httpVersion = value
            field = HttpVersion.getCompatibleVersion(value).toString()

        }
    var requestTarget:String = ""
        set(value) {
            if (value.isEmpty()){
                throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            } else {
                field = value
            }
        }
    val headers: HttpHeaders = HttpHeaders()
    val body: String? = null

fun requestToString(): String {
    return """
        METHOD: $method,
        TARGET: $requestTarget,
        COMPATIBLE VERSION: $compatibleHttpVersion,
        INPUT VERSION: $httpVersion
        
    """.trimIndent()

}






}