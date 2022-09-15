package com.babcock.http

import com.babcock.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

class HttpParser {
    private val log = Log()


    @Throws(HttpParseException::class)
    fun parseHttpReq(inputStream: InputStream): HttpReq {
        val reader = InputStreamReader(inputStream, StandardCharsets.US_ASCII)
        val request = HttpReq()
        //var headers = HttpReq.HttpHeaders()
        //parseRequestLine(reader, request)
        parseHeaders(reader, request)
        //headers = parseHeaders(reader, request)
        //println("${headers.toString()}")
        //parseBody(reader, request)
        return request

    }


    private fun parseBody(reader: InputStreamReader, request: HttpReq) {
        //TODO("Not yet implemented")
    }


    private fun parseHeaders(reader: InputStreamReader, request: HttpReq){ // return HttpHeaders obj
        var byte: Int
        val dataBufferProcess = StringBuilder()
        var counter = 0

        while ((reader.read().also { byte = it }) >= 0) {
            if (byte == 0x0a) {
                counter++
            }
            if (counter > 0) {

                when (byte) {
                    0x03a -> {
                        dataBufferProcess.offsetByCodePoints(1, 0)
                        val temp = dataBufferProcess.toString().trim()
                        println("Key:$temp{}")
                        dataBufferProcess.delete(0, dataBufferProcess.length)
                    }

                    0x0d -> {
                        dataBufferProcess.offsetByCodePoints(dataBufferProcess.length - 1, 0)
                        val temp = dataBufferProcess.toString().trim()
                        println("VALUE:$temp{}")
                        dataBufferProcess.delete(0, dataBufferProcess.length)
                    }

                    (0x0a) -> {
                        byte = reader.read()
                        if (byte == 0x0d) {
                            println("End")
                            break
                        }
                    }

                    else -> dataBufferProcess.append(byte.toChar())
                }
            }
        }

        println(dataBufferProcess.toString())


    }


@Throws(HttpParseException::class)
private fun parseRequestLine(reader: InputStreamReader, request: HttpReq): HttpReq {
    var byte: Int
    val dataBuffer = StringBuilder()


    var parsedMethod: Boolean = false
    var parsedRequestTarget: Boolean = false

    while ((reader.read().also { byte = it }) >= 0) {
        if (byte == CR) {
            byte = reader.read()
            if (byte == LF) {
                log.logWarning("Request line VERSION to process: $dataBuffer")
                if (!parsedMethod || !parsedRequestTarget) {
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                }
                try {
                    log.logSuccess("Version test: ${dataBuffer.toString()}")
                    request.compatibleHttpVersion = dataBuffer.toString()
                } catch (e: HttpParseException) {
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                }
                return request

            } else {
                throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            }
        }
        if (byte == SP) {
            if (!parsedMethod) {
                log.logWarning("Request line METHOD to process: $dataBuffer")
                request.method = HttpMethod.convertToMethod(dataBuffer.toString())

                parsedMethod = true
            } else if (!parsedRequestTarget) {
                log.logWarning("Request line TARGET to process: $dataBuffer")
                request.requestTarget = dataBuffer.toString()
                parsedRequestTarget = true
            } else {
                throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
            }
            log.logSuccess("delete buffer")
            dataBuffer.delete(0, dataBuffer.length)
        } else {
            dataBuffer.append(byte.toChar())

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