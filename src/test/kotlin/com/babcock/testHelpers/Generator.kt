package com.babcock.testHelpers

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

object Generator {
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

