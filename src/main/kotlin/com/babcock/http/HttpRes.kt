package com.babcock.http

import java.io.FileInputStream
import java.nio.file.Path

class HttpRes(request: HttpReq) {
    lateinit var statusCode: HttpStatusCode
    lateinit var statusMessage: String
    lateinit var statusNumber: String
    lateinit var version: String
    lateinit var target: String
    lateinit var responseHeadersAndBody: String
    lateinit var path: String
    var body: String = ""
    var request = request

    init {


        try {
            if (request.method == HttpMethod.POST) {
                Router(request).handlePostRequest()
                this.body = request.body.trim()
                this.target = request.fullFilePath
                this.version = request.httpVersion
                this.path = ""
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.statusNumber = statusCode.STATUS_CODE.toString()
                responseHeadersAndBody = ResponseProvider(this.request).postResponse()
            }
            Router(this.request).handleResourceType() //.getFileOrResource(this.request)
            if (this.request.statusCode == HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND) {
                this.target = request.fullFilePath //= FileInputStream("${request.fullFilePath}")
                this.version = request.httpVersion
                this.path = request.fullFilePath
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.statusNumber = statusCode.STATUS_CODE.toString()
                responseHeadersAndBody = ResponseProvider(this.request).notFound404()
            } else {
                if (request.method != HttpMethod.POST) {
                    responseHeadersAndBody = EndpointRouter().provideResource(request)
                    this.target = request.fullFilePath //FileInputStream("${request.fullFilePath}")
                    this.version = request.httpVersion
                    this.path = request.fullFilePath
                    this.statusCode = request.statusCode
                    this.statusMessage = statusCode.MESSAGE
                    this.body = request.body.trim()
                    this.statusNumber = statusCode.STATUS_CODE.toString()
                }

            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }


    }

    override fun toString(): String {


        return """
            |***Response***
            |Status code:${statusCode.STATUS_CODE}
            |Status msg:${statusCode.MESSAGE}
            |Target: ${target.toString()}
            |Path: ${path.toString()}
            |Body: $body
            |*** Headers and Body
            |${responseHeadersAndBody}
            |***End Response***
            |""".trimMargin()
    }
}
