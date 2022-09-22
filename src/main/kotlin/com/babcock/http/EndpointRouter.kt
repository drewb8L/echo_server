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


    fun provideResource(request: HttpReq, target: String = request.requestTarget):String{   //FileInputStream {
        //check method
        val allowed = request.method
        val allowedMethods = this.endpoints[target]?.let { (it.contains(allowed)) }
        return if (allowedMethods != null && allowedMethods) {
            "$allowed is allowed"

        } else "$allowed is not allowed on ${endpoints[target].toString().removePrefix("[").removeSuffix("]")}"
        //set allow

        // if allowed -> build response from method
        // if not allowed -> build 405

        // build response
    }





}



    //handle method is allowed

    //handle build response

