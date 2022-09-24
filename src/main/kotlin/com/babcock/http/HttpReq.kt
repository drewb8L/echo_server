package com.babcock.http

import java.nio.file.Path


class HttpReq : HttpMsg() {
    lateinit var method: HttpMethod
    var httpVersion: String = ""

    var compatibleHttpVersion: String = ""
        set(value) {
            httpVersion = value
            val temp = HttpVersion.getCompatibleVersion(value)
            if (temp != null) {
                if (temp.MAJOR == 0) {
                    this.statusCode = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR
                    this.statusNumber = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR.STATUS_CODE.toString()
                    this.statusMsg = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR.MESSAGE
                } else {
                    field = temp.toString()

                }


            }
        }
    var requestTarget: String = ""
        set(value) {
            if (value.isEmpty()) {
                this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                this.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                this.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE
                //throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            } else {
                field = value
            }
        }
    var headers = HashMap<String, String>()
    lateinit var statusCode: HttpStatusCode//lateinit  = HttpStatusCode.SUCCESS_200_OK
    lateinit var statusNumber: String//Int //= 200
    lateinit var statusMsg: String //= "OK"
    var fullFilePath: String = ""

    lateinit var body: String

    fun requestToString(): String { // add headers
        return """
        METHOD: ${this.method},
        TARGET: $requestTarget,
        BODY: $body
        
        COMPATIBLE VERSION: $compatibleHttpVersion,
        INPUT VERSION: $httpVersion
    """.trimIndent()

    }


}