package com.babcock.http


//may be better as an object
object EndpointMethodProvider {

    fun addMethodsToEndpoint(path:String, allowedMethods:String){
        // take in a path as string
        //check if dir exist
            // true -> add to endpoint map with methods as "GET,HEAD"
            // false -> error
    }

    var endpointList: MutableMap<String, String> = mutableMapOf(
        "/" to "GET,HEAD,OPTIONS",
        "head_request" to "HEAD"
    )

    var endpointList2:MutableMap<String, List<HttpMethod>>  = mutableMapOf(
        "/" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD),
        "/simple_get" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD),
        "/simple_get_with_body" to listOf<HttpMethod>(HttpMethod.GET),
        "/head_request" to listOf<HttpMethod>(HttpMethod.HEAD),
        "/method_options" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD,HttpMethod.OPTIONS),
        "/method_options2" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD,HttpMethod.OPTIONS,HttpMethod.POST),
        "/echo_body" to listOf<HttpMethod>(HttpMethod.POST),
        "/redirect" to listOf<HttpMethod>(HttpMethod.GET)


    )
}