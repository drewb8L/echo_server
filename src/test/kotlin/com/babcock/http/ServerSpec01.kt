package com.babcock.http

import com.babcock.testHelpers.Generator
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

        assertEquals(null, response.body)
        assertThat(response.responseHeadersAndBody, CoreMatchers.containsString("HEAD, OPTIONS"))
        assertEquals("405", response.statusNumber)



    }

    @Test
    fun page_not_found(){
        val request = httpParser.parseHttpReq(Generator.validParseTestCaseTo404())
        val response = HttpRes(request)

        assertEquals("${response.statusNumber}","404" )
    }

    @Test
    fun simple_get(){

    }
    @Test
    fun simple_head(){

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