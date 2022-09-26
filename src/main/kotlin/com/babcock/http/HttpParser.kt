package com.babcock.http

import Log
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



        parseRequestLine(reader, request)
        parseHeaders(reader, request)
        log.logSuccess(request.headers.toString())
        parseBody(reader, request)
        return request

    }


    private fun parseBody(reader: InputStreamReader, request: HttpReq) {
        var byte: Int
        val dataBufferProcess = StringBuilder()
        while ((reader.read().also { byte = it }) >= 0) {
            dataBufferProcess.append(byte.toChar())
        }
        request.body = dataBufferProcess.toString().trim()
    }


    private fun parseHeaders(reader: InputStreamReader, request: HttpReq) { // return HttpHeaders obj
        var byte: Int
        val dataBufferProcess = StringBuilder()
        var counter = 0
        var name: String = ""
        var content: String = ""

        while ((reader.read().also { byte = it }) >= 0) {
            if (byte == 0x0a) {
                counter++
            }
            if (counter > 0) {

                when (byte) {
                    0x03a -> {
                        val temp = dataBufferProcess.toString().trim()
                        name = temp

                        dataBufferProcess.delete(0, dataBufferProcess.length)
                    }

                    0x0d -> {
                        val temp = dataBufferProcess.toString().trim()
                        content = temp
                        dataBufferProcess.delete(0, dataBufferProcess.length)
                    }

                    (0x0a) -> {
                        byte = reader.read()
                        if (byte == 0x0d) {
                            break
                        } else {
                            dataBufferProcess.append(byte.toChar())
                        }
                    }

                    else -> {
                        dataBufferProcess.append(byte.toChar())
                        request.headers[name] = content
                    }

                }
            }
        }


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
                        request.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                        request.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                        request.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE
                        return request

                    }
                    try {
                        log.logSuccess("Version test: ${dataBuffer.toString()}")
                        request.compatibleHttpVersion = dataBuffer.toString()
                    } catch (e: HttpParseException) {
                        request.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                        request.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                        request.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE

                        return request
                    }
                    return request

                } else {
                    request.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                    request.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                    request.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE

                }
            }
            if (byte == SP) {
                if (!parsedMethod) {
                    log.logWarning("Request line METHOD to process: $dataBuffer")
                    try {
                        request.method = HttpMethod.convertToMethod(dataBuffer.toString())

                    } catch (e: HttpParseException) {
                        request.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                        request.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                        request.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE
                    }

                    parsedMethod = true
                } else if (!parsedRequestTarget) {
                    request.requestTarget = "$dataBuffer"//dataBuffer.toString()
                    log.logWarning("Request line TARGET to process: ${request.requestTarget}")
                    parsedRequestTarget = true
                } else {
                    request.statusCode = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST
                    request.statusNumber = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.STATUS_CODE.toString()
                    request.statusMsg = HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST.MESSAGE
                    return request

                }

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