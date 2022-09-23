package com.babcock.http

import com.babcock.testHelpers.Generator
import com.babcock.http.HttpStatusCode
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val httpParser = HttpParser()
internal class ServerSpec01 {
    @Test
    fun method_not_allowed(){
        val request = httpParser.parseHttpReq(Generator.getRequestToHead())
        val response = HttpRes(request)

        /*
        Given I make an GET request to "/head_request"
        Then my response should have status code 405
        And my response should have allowed headers of HEAD, OPTIONS
        And my response should have an empty body
        */

        assertEquals( "" ,response.body.toString())
        assertThat(response.responseHeadersAndBody, CoreMatchers.containsString("HEAD, OPTIONS"))
        assertEquals("405", response.statusNumber)
    }

    @Test
    fun page_not_found(){
        val request = httpParser.parseHttpReq(Generator.validParseTestCaseTo404())
        val response = HttpRes(request)
        /*
         Given I make a GET request to a page that does not exist
         Then my response should have status code 404
        */
        assertEquals("${response.statusCode}",HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND.toString() )
    }

    @Test
    fun simple_get(){
        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGet())
        val response = HttpRes(request)
        println(response.toString())
        /*
         Given I make a GET request to "/simple_get"
        Then my response should have status code 200
        And my response should have an empty body
         */
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response.statusCode.toString())
        assertEquals("" ,response.body.toString())

        val request2 = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
        val response2 = HttpRes(request2)
        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response2.statusCode.toString())
        println(response2.path)


    }
    @Test
    fun simple_head(){
        val request = httpParser.parseHttpReq(Generator.requestToHead())
        val response = HttpRes(request)

        assertEquals(HttpStatusCode.SUCCESS_200_OK.toString(), response.statusCode.toString())
    }

    @Test
    fun simple_option(){

    }

    @Test
    fun simple_post(){

    }

    @Test
    fun simple_redirect(){

    }
}