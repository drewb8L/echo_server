package com.babcock.http




class HttpReq: HttpMsg() {
    lateinit var method:HttpMethod
    var requestTarget:String = ""
        set(value) {
            if (value.isEmpty()){
                throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            } else {
                field = value
            }
        }
    lateinit var httpVersion:String






}