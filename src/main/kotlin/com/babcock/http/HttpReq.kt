package com.babcock.http

import java.util.Collections
import java.util.StringJoiner


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
//    var headers: HttpHeaders? = null


    val body: String? = null

    inner class HttpHeaders(){

        fun headerMap(key: String, value: String){



        }
    }

fun requestToString(): String { // add headers
    return """
        METHOD: $method,
        TARGET: $requestTarget,
        
        COMPATIBLE VERSION: $compatibleHttpVersion,
        INPUT VERSION: $httpVersion
        
        
    """.trimIndent()

}






}