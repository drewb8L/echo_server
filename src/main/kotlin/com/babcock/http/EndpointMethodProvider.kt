package com.babcock.http


//may be better as an object
object EndpointMethodProvider {


    var endpointList:MutableMap<String, List<HttpMethod>>  = mutableMapOf(
        "/index.html" to listOf<HttpMethod>(HttpMethod.GET),
        "/about.html" to listOf<HttpMethod>(HttpMethod.GET),
        "/test.html" to listOf<HttpMethod>(HttpMethod.GET),
        "/" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD),
        "/simple_get" to listOf<HttpMethod>(HttpMethod.GET, HttpMethod.HEAD),
        "/simple_get_with_body" to listOf<HttpMethod>(HttpMethod.GET),
        "/head_request" to listOf<HttpMethod>(HttpMethod.HEAD, HttpMethod.OPTIONS),
        "/method_options" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD,HttpMethod.OPTIONS),
        "/method_options2" to listOf<HttpMethod>(HttpMethod.GET,HttpMethod.HEAD,HttpMethod.OPTIONS,HttpMethod.PUT,HttpMethod.POST),
        "/echo_body" to listOf<HttpMethod>(HttpMethod.POST),
        "/redirect" to listOf<HttpMethod>(HttpMethod.GET)


    )
}