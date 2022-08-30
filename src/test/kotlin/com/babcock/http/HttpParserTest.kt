package com.babcock.http

import com.babcock.log
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets
import kotlin.test.assertFailsWith

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
}


