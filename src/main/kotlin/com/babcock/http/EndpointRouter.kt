package com.babcock.http

import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.properties.Delegates

/*
Endpoint Router will accept incoming targets to /some_endpoint, any target that does not specify a file considered an
endpoint request.
The Endpoint Router class will map requests to directories and if they don't exist will return 404. If they do exist will
return an index page.

Endpoints will have an allowed methods property that will dictate which methods can reach the endpoint. Endpoints will
have an attached response that corresponds with its allowed method and request type.

If an endpoint allows GET and HEAD it will respond appropriately based on the method


 */

class EndpointRouter(endpoints: MutableMap<String, List<HttpMethod>> = EndpointMethodProvider.endpointList2) {
    private var endpoints: MutableMap<String, List<HttpMethod>>

    init{
        this.endpoints = endpoints
    }
    // need a request/target/method, list of endpoints, allowed methods, build a response, set a status code

    //list of endpoints  (path.let { Files.isDirectory(it) })

    fun isValidEndpoint(target: String): Boolean {
        val pattern = Regex(pattern = "^/.*$")// fix regex to exclude /word.ext
        val webRoot = "src/main/resources/web_files"

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
            // set response
            ResponseStatus().setStatus(request,HttpStatusCode.SUCCESS_200_OK)
            ResponseProvider(request).handleResponseByMethod()

        } else {
            ResponseStatus().setStatus(request,HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
            request.fullFilePath = Paths.get("src/main/resources/web_files/400/405.html")
            ResponseProvider(request).notAllowedResponse()
        }

    }

}

