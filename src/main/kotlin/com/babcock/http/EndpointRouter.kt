package com.babcock.http

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class EndpointRouter(endpoints: MutableMap<String, List<HttpMethod>> = EndpointMethodProvider.endpointList) {
    private var endpoints: MutableMap<String, List<HttpMethod>>

    init{
        this.endpoints = endpoints
    }

    fun isValidEndpoint(target: String): Boolean {
        val pattern = Regex(pattern = "^/.*$")// fix regex to exclude /word.ext
        val webRoot = "src/main/resources/web_files"

        if (target == "/"){
            return true
        }

        val path: Path = Paths.get("$webRoot$target")
        return when (Files.isDirectory(path)){
            true -> true
            else -> false
        }
    }


    fun provideResource(request: HttpReq, target: String = request.requestTarget): String{
        val webRoot = "src/main/resources/web_files"
        val allowed = request.method
        var allowedMethods: List<HttpMethod>? = endpoints[target]
        this.endpoints[target]?.let { (it.contains(allowed)).also { allowedMethods = this.endpoints[target]!! }  }


            if (allowedMethods?.contains(request.method)  == true  && allowed == HttpMethod.GET) {
                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Paths.get("$webRoot${target}/index.html")
                return ResponseProvider(request).handleResponseByMethod()

            }else if(allowedMethods?.contains(request.method) == true && allowed == HttpMethod.HEAD){

                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Paths.get("$webRoot${target}/index.html")
                return ResponseProvider(request).handleResponseByMethod()
            } else {
                ResponseStatus().setStatus(request,HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
                request.fullFilePath = Paths.get("src/main/resources/web_files/400/405.html")
                return ResponseProvider(request).notAllowedResponse()

            }
    }

}

