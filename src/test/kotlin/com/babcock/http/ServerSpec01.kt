package com.babcock.http

import com.babcock.testHelpers.Generator
import com.babcock.http.HttpStatusCode
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val httpParser = HttpParser()

internal class ServerSpec01 {

    @Test
    fun method_not_allowed() {
        val request = httpParser.parseHttpReq(Generator.getRequestToHead())
        val response = HttpRes(request)
        /*
            Given I make an GET request to "/head_request"
            Then my response should have status code 405
            And my response should have allowed headers of HEAD, OPTIONS
            And my response should have an empty body
        */

        assertEquals("", response.body)
        assertThat(response.responseHeadersAndBody, CoreMatchers.containsString("HEAD, OPTIONS"))
        assertEquals("405", response.statusNumber)
    }

    @Test
    fun page_not_found() {
        val request = httpParser.parseHttpReq(Generator.validParseTestCaseTo404())
        val response = HttpRes(request)
        /*
            Given I make a GET request to a page that does not exist
            Then my response should have status code 404
        */
        assertEquals("${response.statusCode}", HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.toString())
    }

    @Test

    fun simple_get() {
        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGet())
        val response = HttpRes(request)
        /*
            Given I make a GET request to "/simple_get"
            Then my response should have status code 200
            And my response should have an empty body
         */
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response.statusCode.toString())
        assertEquals("", response.body.toString())

        val request2 = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
        val response2 = HttpRes(request2)
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response2.statusCode.toString())
        assertEquals("Hello world", response2.body)
        println(response2.toString())


    }

    @Test
    fun simple_head() {
        /*
            Given I make a HEAD request to "/simple_get"
            Then my response should have status code 200
            And my response should have an empty body
         */
        val request = httpParser.parseHttpReq(Generator.headRequestToSimpleGet())
        val response = HttpRes(request)

        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response.statusCode.toString())
        assertEquals("", response.body)

        /*
            Given I make a HEAD request to "/head_request"
            Then my response should have status code 200
            And my response should have an empty body
         */
        val request2 = httpParser.parseHttpReq(Generator.requestToHead())
        val response2 = HttpRes(request2)

        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response2.statusCode.toString())
        assertEquals("", response2.body)
    }

    @Test
    fun simple_option() {
        /*
            Given I make an OPTIONS request to "/method_options"
            Then my response should have status code 200
            And my response should have allowed headers of GET, HEAD, and OPTIONS
            And my response should have an empty body
         */
        val request = httpParser.parseHttpReq(Generator.requestToOptions())
        val response = HttpRes(request)
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response.statusCode.toString())
        assertThat(response.responseHeadersAndBody, CoreMatchers.containsString("GET, HEAD, OPTIONS"))
        assertEquals("", response.body)


        /*
            Given I make an OPTIONS request to "/method_options2"
            Then my response should have status code 200
            And my response should have allowed headers of GET, HEAD, OPTIONS, PUT, and POST
            And my response should have an empty body
         */

        val request2 = httpParser.parseHttpReq(Generator.requestToOptions2())
        val response2 = HttpRes(request2)
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response2.statusCode.toString())
        assertThat(response2.responseHeadersAndBody, CoreMatchers.containsString("GET, HEAD, OPTIONS, PUT, POST"))
        assertEquals("", response2.body)
    }

    @Test
    fun simple_post() {
        /*
        Given I make a POST with a body to "/echo_body"
        Then my response should have status code 200
        And my response body should equal the body of my request
         */

        val request = httpParser.parseHttpReq(Generator.postRequestWithBody())
        val response = HttpRes(request)
        assertEquals("200", response.statusCode.STATUS_CODE.toString())
        assertEquals(response.body, request.body)

    }

    @Test
    fun simple_redirect(){
        /*
        Given I make an GET request to "/redirect"
        Then my response should have status code 301
        And my response should have a location header with the "localhost:5000/simple_get"
        And my response should have an empty body

         */

        val request = httpParser.parseHttpReq(Generator.requestToRedirect())
        val response = HttpRes(request)
        assertEquals("301", response.statusCode.STATUS_CODE.toString())
        assertThat(response.responseHeadersAndBody, CoreMatchers.containsString("localhost:5000/simple_get"))
    }
}