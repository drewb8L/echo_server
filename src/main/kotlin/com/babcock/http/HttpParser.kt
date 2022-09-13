package com.babcock.http

import com.babcock.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

class HttpParser {
    private val log = Log()
    private val headerRegex = "^(.+): (.+)$".toRegex()

    @Throws(HttpParseException::class)
    fun parseHttpReq(inputStream: InputStream): HttpReq {
        val reader = InputStreamReader(inputStream, StandardCharsets.US_ASCII)
        var request = HttpReq()
        var headers = HttpHeaders()
        request = parseRequestLine(reader, request)
        //headers = parseHeaders(reader)
        println("${headers.toString()}")
        //parseBody(reader, request)
        return request

    }


    private fun parseBody(reader: InputStreamReader, request: HttpReq) {
        //TODO("Not yet implemented")
    }

    private fun parseHeaders(input: BufferedReader): HttpHeaders =
        input.lineSequence()
            .takeWhile { it.isNotBlank() }
            .map {
                val (header, values) = headerRegex.find(it)?.destructured
                    ?: throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                header to values.split(", ")
            }
            .groupBy { (name, _) -> name } // 5
            .mapValues { (_, valuesWithNames) -> valuesWithNames.map { (_, values) -> values }.flatten() }
            .let(::HttpHeaders)
    private fun parseHeadersOrg(reader: InputStreamReader, request: HttpReq) {
        //TODO("Not yet implemented")
    }

    @Throws(HttpParseException::class)
    private fun parseRequestLine(reader: InputStreamReader, request: HttpReq):HttpReq {
        var byte: Int
        val dataBufferProcess = StringBuilder()

        var parsedMethod: Boolean = false
        var parsedRequestTarget: Boolean = false

        while ((reader.read().also { byte = it }) >= 0) {
            if (byte == CR) {
                byte = reader.read()
                if (byte == LF) {
                    log.logWarning("Request line VERSION to process: $dataBufferProcess")
                    if (!parsedMethod || !parsedRequestTarget){
                        throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                    }
                    try {
                        log.logSuccess("Version test: ${dataBufferProcess.toString()}")
                        request.compatibleHttpVersion = dataBufferProcess.toString()
                    } catch (e: HttpParseException){
                        throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                    }
                    return request

                } else{
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                }
            }
            if (byte == SP) {
                if (!parsedMethod) {
                    log.logWarning("Request line METHOD to process: $dataBufferProcess")
                    request.method = HttpMethod.convertToMethod(dataBufferProcess.toString())

                    parsedMethod = true
                } else if (!parsedRequestTarget) {
                    log.logWarning("Request line TARGET to process: $dataBufferProcess")
                    request.requestTarget = dataBufferProcess.toString()
                    parsedRequestTarget = true
                } else {
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                }
                log.logSuccess("delete buffer")
                dataBufferProcess.delete(0, dataBufferProcess.length)
            } else{
                dataBufferProcess.append(byte.toChar())

            }

        }
        return request

    }

    companion object {

        // In request line SP = Space, CR = Carriage Return, LF = Line Feed
        private const val SP: Int = 0x20
        private const val CR: Int = 0x0d
        private const val LF: Int = 0x0a

    }
}