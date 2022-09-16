package com.babcock.http

import com.babcock.log
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.test.assertFailsWith
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.containsString
import kotlin.io.path.Path

internal class HttpParserTest {

    val httpParser = HttpParser()

    @Test
    fun parseHttpReqNotNull() {
        assertNotNull(httpParser)
    }

    @Test
    fun parseRequest() {
        val request = httpParser.parseHttpReq(validParseTestCase())
        log.logSuccess("Method returned: ${request.method}")
        assertEquals(request.method, HttpMethod.GET)
        assertEquals("/", request.requestTarget)
        assertEquals(request.compatibleHttpVersion, "HTTP_1_1")

    }

    @Test
    fun parseMethodHead() {
        val request = httpParser.parseHttpReq(validParseTestCaseHead())
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
                httpParser.parseHttpReq(invalidParseTestCase())
            }
        )
    }

    @Test
    fun parseInvalidMethod() {
        assertFailsWith<HttpParseException>(
            message = "Not Implemented",
            block = {
                httpParser.parseHttpReq(invalidMethodTestCase())
            }
        )
    }

    @Test
    fun parseInvalidRequestEmptyReqLine() {
        val req = httpParser.parseHttpReq(invalidParseTestCaseEmptyRequestLine())
        assertEquals(400, req.statusNumber)
    }

    @Test
    fun parseInvalidRequestEmptyLineFeed() {
        val req = httpParser.parseHttpReq(invalidParseTestCaseEmptyLineFeed())
        assertEquals(400, req.statusNumber)
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
        val req = httpParser.parseHttpReq(invalidHttpVersionRequest())
        assertEquals(505, req.statusNumber)

    }

    @Test
    fun invalidHttpReqBadVersion() {
        val req = httpParser.parseHttpReq(invalidHttpVersionRequest())
        assertEquals(505, req.statusNumber)


    }

    @Test
    fun invalidHttpReqBadMajorVersion() {
        var req = httpParser.parseHttpReq(unsupportedHttpVersionRequest())
        println(req.httpVersion)
        assertEquals(505, req.statusNumber)

    }

    @Test
    fun validSupportedHttpVersion() {
        val request = httpParser.parseHttpReq(supportedHttpVersionRequest())
        assertEquals(HttpVersion.HTTP_1_1.toString(), request.compatibleHttpVersion)

    }

    @Test
    fun parseValidHeaders() {
        val request = httpParser.parseHttpReq(supportedHttpVersionRequest())
        //assertNotNull(request.headers)
    }

    @Test
    fun status200OK() {
        val request = httpParser.parseHttpReq(supportedHttpVersionRequest())
        assertEquals(200, request.statusNumber)
    }

    @Test
    fun status400() {
        val request = httpParser.parseHttpReq(invalidParseTestCaseEmptyRequestLine())
        assertEquals(400, request.statusNumber)
        println(request.statusMsg)
    }

    @Test
    fun responseHeaders() {
        val htmlIn = FileInputStream("src/main/resources/web_files/index.html")
        val version = "HTTP/1.1"
        val statusNumber = "200 OK"
        val CRLF: String = "\n\r"
        val status = "${statusNumber}"
        val conn = "Connection: Keep-Alive${CRLF}"
        var contentType: String = "Content-Type: text/html$CRLF"
        val date = Date()
        val formattedDate = "Date: $date$CRLF"
        val body = htmlIn
        fun contentLength(file: FileInputStream): Int =
            file.readAllBytes().toString(Charsets.UTF_8).length
        println("$version $status$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength(htmlIn)}${CRLF}${CRLF}\"<html><head><title> Kotlin http server</title></head><body><h1 style=\\\"color:red;\\\">404 not found</h1></body></html>")
    }

    @Test
    fun routingToIndex() {
        val file = Path("src/main/resources/web_files/index.html") // size = 530 bytes
        val req = HttpParser().parseHttpReq(validParseTestCase())
        val target = Router().handleTarget(req)

        assert(target.available() >= 530)
        val output = getFileStream(file.toString())
        println(output)

        assertThat(output, containsString("Kotlin HTTP Server"))
    }

    @Test
    fun routingTo404(){
        val file = Path("src/main/resources/web_files/400/404.html") //size = 143
        val req = HttpParser().parseHttpReq(validParseTestCaseTo404())
        val target = Router().handleTarget(req)

        assert(target.available() >= 140)
        val output = getFileStream(file.toString())
        println(output)

        assertThat(output, containsString("404 not found"))

    }



    fun getFileStream(file:String):String {
        val input: FileInputStream = FileInputStream(file)
        var output = StringBuilder()
        try {
            val byte: ByteArray = ByteArray(input.available())
            input.read(byte)

            println("File size = ${byte.size}")
            for (i in 0..byte.size) {
                if (i >= byte.size) {
                    break
                } else {
                    //print(byte[i].toInt().toChar())
                    output.append(byte[i].toInt().toChar())

                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            input.close()
        }
        return output.toString()

    }

    fun supportedHttpVersionRequest(): InputStream {
        val validRequestString: String =
            "GET / HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )

    }

    //Fix version checking!!!
    fun unsupportedHttpVersionRequest(): InputStream {
        val validRequestString: String =
            "GET / HTTP/0.2\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )

    }

    fun invalidHttpVersionRequest(): InputStream {
        val validRequestString: String =
            "GET / HTTP/2.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )

    }

    fun validParseTestCase(): InputStream {
        val validRequestString: String =
            "GET / HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }
    fun validParseTestCaseTo404(): InputStream {
        val validRequestString: String =
            "GET /notfound HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }

    fun validParseTestCaseHead(): InputStream {
        val validRequestString: String =
            "HEAD / HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "Accept-Language: en-US,en;q=0.9\r\n" +
                    "Cache-Control: max-age=0\r\n" +
                    "Connection: keep-alive\r\n" +
                    "Host: localhost:8081\r\n" +
                    "Sec-Fetch-Dest: document\r\n" +
                    "Sec-Fetch-Mode: navigate\r\n" +
                    "Sec-Fetch-Site: cross-site\r\n" +
                    "Upgrade-Insecure-Requests: 1\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }

    fun invalidParseTestCase(): InputStream {
        val validRequestString: String =
            "get / HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }

    fun invalidParseTestCaseEmptyRequestLine(): InputStream {
        val validRequestString: String =
            "\r\n" + // Empty request
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }

    fun invalidParseTestCaseEmptyLineFeed(): InputStream {
        val validRequestString: String =
            "HEAD / HTTP/1.1\r" + // no linefeed
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }

    fun invalidMethodTestCase(): InputStream {
        val validRequestString: String =
            "GETTTTTTT / HTTP/1.1\r\n" +
                    "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\n" +
                    "Accept-Encoding: gzip, deflate, br\r\n" +
                    "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36\r\n" +
                    "sec-ch-ua: \"Chromium\";v=\"104\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"104\"\r\n" +
                    "sec-ch-ua-mobile: ?0\r\n" +
                    "\r\n"
        return ByteArrayInputStream(
            validRequestString.toByteArray(
                StandardCharsets.US_ASCII
            )
        )
    }
}


