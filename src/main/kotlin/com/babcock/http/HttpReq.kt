package com.babcock.http


class HttpReq : HttpMsg() {
    lateinit var method: HttpMethod
    var httpVersion: String = ""

    var compatibleHttpVersion: String = ""
        set(value) {
            httpVersion = value
            val temp = HttpVersion.getCompatibleVersion(value)
            if (temp != null) {
                if (temp.MAJOR == 0){
                    println("Temp ${temp.VERSION}")
                    this.statusCode = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR
                    this.statusNumber = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR.STATUS_CODE
                    this.statusMsg = HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR.MESSAGE
                } else{
                    field = temp.toString()
                    println("Field: $field")
                }


            }
        }
    var requestTarget: String = ""
        set(value) {
            if (value.isEmpty()) {
                this.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                this.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE
                this.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE
                //throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            } else {
                field = value
            }
        }
    var headers = HashMap<String, String>()
    var statusCode:HttpStatusCode = HttpStatusCode.SUCCESS_200_OK
    var statusNumber: Int = 200
    var statusMsg:String = "OK"


    val body: String? = null

    fun requestToString(): String { // add headers
        return """
        METHOD: $method,
        TARGET: $requestTarget,
        
        COMPATIBLE VERSION: $compatibleHttpVersion,
        INPUT VERSION: $httpVersion
        
        
    """.trimIndent()

    }


}