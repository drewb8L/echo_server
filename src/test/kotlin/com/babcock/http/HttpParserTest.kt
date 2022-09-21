package com.babcock.http

import com.babcock.testHelpers.Generator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.containsString
import kotlin.io.path.Path
import com.babcock.testHelpers.Utils
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
        assertFailsWith<HttpParseException>(
            message = "Not Implemented",
            block = {
                httpParser.parseHttpReq(Generator.invalidParseTestCase())
            }
        )
    }

    @Test
    fun parseInvalidMethod() {
        assertFailsWith<HttpParseException>(
            message = "Not Implemented",
            block = {
                httpParser.parseHttpReq(Generator.invalidMethodTestCase())
            }
        )
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
    fun parseValidHeaders() {
        val request = httpParser.parseHttpReq(Generator.supportedHttpVersionRequest())
        //assertNotNull(request.headers)
    }

    @Test
    fun status200OK() {
        val request = httpParser.parseHttpReq(Generator.supportedHttpVersionRequest())

    }

    @Test
    fun status400() {
        val request = httpParser.parseHttpReq(Generator.invalidParseTestCaseEmptyRequestLine())
        val response = HttpRes(request)

        assertEquals("404",response.statusNumber)
    }


    @Test
    fun routingToIndex() {
        val file = Path("src/main/resources/web_files/index.html") // size = 530 bytes
        val req = HttpParser().parseHttpReq(Generator.validParseTestCase())
        val target = Router().handleTarget(req)

        assertEquals("200", req.statusNumber.toString() )

        assert(target.available() >= 530)
        val output = Utils.getFileStream(file.toString())
        println(output)

        assertThat(output, containsString("Kotlin HTTP Server"))
    }

    @Test
    fun routingTo404() {
        val file = Path("src/main/resources/web_files/400/404.html") //size = 124
        val req = HttpParser().parseHttpReq(Generator.validParseTestCaseTo404())
        val target = Router().handleTarget(req)

        assertEquals("404", req.statusNumber.toString() )

        assert(target.available() >= 100)
        val output = Utils.getFileStream(file.toString())
        println(output)

        assertThat(output, containsString("404 Not Found"))

    }
    @Test
    fun routerFindPathByTarget(){

        val req = HttpParser().parseHttpReq(Generator.validParseTestCase())
        val res = HttpRes(req)
        val responseHeadersAndBody = res.responseHeadersAndBody
        assertThat(responseHeadersAndBody, containsString("Welcome to Kotlin HTTP Server"))
        assertEquals("200",res.statusNumber)

        val req2 = HttpParser().parseHttpReq(Generator.validParseTestCaseTo404())
        val res2 = HttpRes(req2)
        val responseHeadersAndBody2 = res2.responseHeadersAndBody
        assertThat(responseHeadersAndBody2, containsString("404 Not Found"))
        assertEquals("404", res2.statusNumber)
    }

    @Test
    fun routerFindByFullFileName(): Unit {
        val req = HttpParser().parseHttpReq(Generator.fullFileName())
        val res = HttpRes(req)
        val responseHeadersAndBody = res.responseHeadersAndBody
        assertThat(responseHeadersAndBody, containsString("Welcome to Kotlin HTTP Server"))


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

    @Test
    fun filePath() {
        val req = HttpParser().parseHttpReq(Generator.fullFileName())
        val res = HttpRes(req)
        assertNotNull(res.path)
        assertThat(res.path.toString(), containsString("src/main/resources/web_files/index.html"))
    }


}