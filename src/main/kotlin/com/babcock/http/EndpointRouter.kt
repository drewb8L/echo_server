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
        //check method "$allowed is not allowed on ${endpoints[target].toString().removePrefix("[").removeSuffix("]")}"
        val allowed = request.method
        val allowedMethods = this.endpoints[target]?.let { (it.contains(allowed)) }
        return if ((allowedMethods != null) && allowedMethods) {
            ResponseStatus().setStatus(request,HttpStatusCode.SUCCESS_200_OK)
            ResponseProvider(request).handleResponseByMethod()

        } else {
            ResponseStatus().setStatus(request,HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
            request.fullFilePath = Paths.get("src/main/resources/web_files/400/405.html")
            ResponseProvider(request).notAllowedResponse()
        }

    }

}

