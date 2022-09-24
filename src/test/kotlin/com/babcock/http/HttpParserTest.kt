package com.babcock.http

import com.babcock.testHelpers.Generator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.containsString
import kotlin.io.path.Path
import com.babcock.testHelpers.Utils
import io.ktor.util.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

internal class HttpParserTest {

    val httpParser = HttpParser()

    @Test
    fun parseHttpReqNotNull() {
        assertNotNull(httpParser)
    }

    @Test
    fun parseRequest() {
        val request = httpParser.parseHttpReq(Generator.validParseTestCase())
        log.logSuccess("Method returned: ${request.method}")
        assertEquals(request.method, HttpMethod.GET)
        assertEquals("/", request.requestTarget)
        assertEquals(request.compatibleHttpVersion, "HTTP_1_1")

    }

    @Test
    fun parseMethodHead() {
        val request = httpParser.parseHttpReq(Generator.validParseTestCaseHead())
        log.logSuccess("Method returned: ${request.method}")
        assertEquals(request.method, HttpMethod.HEAD)
        assertEquals("/", request.requestTarget)
        assertEquals(request.compatibleHttpVersion, "HTTP_1_1")

    }

    @Test
    fun parseInvalidRequest() {
        val req = httpParser.parseHttpReq(Generator.invalidMethodLowerCaseTestCase())
        assertEquals("400", req.statusNumber)
    }

    @Test
    fun parseInvalidMethod() {
        val req = httpParser.parseHttpReq(Generator.invalidMethodTestCase())
        assertEquals("400", req.statusNumber)
    }

    @Test
    fun parseInvalidRequestEmptyReqLine() {
        val req = httpParser.parseHttpReq(Generator.invalidParseTestCaseEmptyRequestLine())
        assertEquals("400", req.statusNumber)
    }

    @Test
    fun parseInvalidRequestEmptyLineFeed() {
        val req = httpParser.parseHttpReq(Generator.invalidParseTestCaseEmptyLineFeed())
        assertEquals("400", req.statusNumber)
    }

    @Test
    fun parseCompatibleHttpVersion() {
        val version: HttpVersion? = HttpVersion.getCompatibleVersion("HTTP/1.1")
        assertNotNull(version)
        assertEquals(version, HttpVersion.HTTP_1_1)
    }

    @Test
    fun parseHigherCompatibleHttpVersion() {
        val version: HttpVersion? = HttpVersion.getCompatibleVersion("HTTP/1.2")
        assertNotNull(version)
        assertEquals(HttpVersion.HTTP_1_1, version)
    }

    @Test
    fun parseInvalidHttpVersion() {
        val req = httpParser.parseHttpReq(Generator.invalidHttpVersionRequest())
        assertEquals("505", req.statusNumber)

    }

    @Test
    fun invalidHttpReqBadVersion() {
        val req = httpParser.parseHttpReq(Generator.invalidHttpVersionRequest())
        assertEquals("505", req.statusNumber)


    }

    @Test
    fun invalidHttpReqBadMajorVersion() {
        var req = httpParser.parseHttpReq(Generator.unsupportedHttpVersionRequest())
        println(req.httpVersion)
        assertEquals("505", req.statusNumber)

    }

    @Test
    fun validSupportedHttpVersion() {
        val request = httpParser.parseHttpReq(Generator.supportedHttpVersionRequest())
        assertEquals(HttpVersion.HTTP_1_1.toString(), request.compatibleHttpVersion)

    }


    @Test
    fun files() {
        val target:String = "/test.html".lowercase().trim()
        val webRoot = Paths.get("src/main/resources/web_files")
        var filePath: Path? = null

        val pattern = Regex(".html")
        val match = pattern.containsMatchIn(target)

        if (target == "/") {
            filePath = Paths.get("src/main/resources/web_files/index.html")
        }else if (match){
            filePath = Paths.get(webRoot.toString(), target)
            log.logSuccess(filePath.toString())
        }else if (!match){
            filePath = Paths.get(webRoot.toString(),"$target.html")
            log.logSuccess(filePath.toString())
        }

        when(filePath?.let { Files.exists(it) }){
            true -> log.logWarning(filePath.toString())
            else -> log.logError("not found")
        }

        assertEquals("src/main/resources/web_files/test.html", filePath.toString())
    }



}