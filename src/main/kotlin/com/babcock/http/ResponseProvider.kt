package com.babcock.http



class ResponseProvider(request: HttpReq) {

    init {
        when(request.method){
            HttpMethod.GET -> getResponse()
            HttpMethod.HEAD -> headResponse()
            HttpMethod.POST ->postResponse()
            HttpMethod.OPTIONS -> optionsResponse()
            else -> {
                notImplementedResponse()
            }
        }
    }

    fun getResponse(){

    }

    fun postResponse(){

    }

    fun headResponse(){

    }

    fun optionsResponse(){

    }

    fun notImplementedResponse(){

    }
}