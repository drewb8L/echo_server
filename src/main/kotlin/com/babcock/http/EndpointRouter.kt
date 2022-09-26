package com.babcock.http

import java.nio.file.Path


class EndpointRouter(endpoints: MutableMap<String, List<HttpMethod>> = EndpointMethodProvider.endpointList) {
    private var endpoints: MutableMap<String, List<HttpMethod>>

    init{
        this.endpoints = endpoints
    }


    fun provideResource(request: HttpReq, target: String = request.requestTarget): String{
        val webRoot = "src/main/resources/web_files"
        val allowed = request.method
        var allowedMethods: List<HttpMethod>? = endpoints[target]
        this.endpoints[target]?.let { (it.contains(allowed)).also { allowedMethods = this.endpoints[target]!! }  }


            if (allowedMethods?.contains(request.method)  == true  && allowed == HttpMethod.GET) {
                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Path.of("$webRoot${target}").toString()
                return ResponseProvider(request).handleResponseByMethod()

            }else if(allowedMethods?.contains(request.method) == true && allowed == HttpMethod.HEAD) {

                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Path.of("$webRoot${target}").toString()
                return ResponseProvider(request).handleResponseByMethod()

            }else if(allowedMethods?.contains(request.method) == true && allowed == HttpMethod.OPTIONS){
                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Path.of("$webRoot${target}").toString()
                return ResponseProvider(request).handleResponseByMethod()

            } else {
                ResponseStatus().setStatus(request,HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
                request.fullFilePath = Path.of("src/main/resources/web_files/400/405.html").toString()
                return ResponseProvider(request).notAllowedResponse()

            }
    }

}

