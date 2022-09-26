package com.babcock.http



class HttpRes(var request: HttpReq) {
    lateinit var statusCode: HttpStatusCode
    lateinit var statusMessage: String
    lateinit var statusNumber: String
    lateinit var version: String
    lateinit var target: String
    lateinit var responseHeadersAndBody: String
    lateinit var path: String
    var body: String = ""

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
            Router(this.request).handleResourceType()
            if (this.request.statusCode == HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND) {
                this.target = request.fullFilePath
                this.version = request.httpVersion
                this.path = request.fullFilePath
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.statusNumber = statusCode.STATUS_CODE.toString()
                responseHeadersAndBody = ResponseProvider(this.request).notFound404()
            } else {
                if (request.method != HttpMethod.POST && request.requestTarget != "/redirect") {
                    responseHeadersAndBody = EndpointRouter().provideResource(request)
                    this.target = request.fullFilePath
                    this.version = request.httpVersion
                    this.path = request.fullFilePath
                    this.statusCode = request.statusCode
                    this.statusMessage = statusCode.MESSAGE
                    this.body = request.body.trim()
                    this.statusNumber = statusCode.STATUS_CODE.toString()
                }
            }

            if (request.method == HttpMethod.GET && request.requestTarget == "/redirect"){
                this.target = request.fullFilePath
                this.version = request.httpVersion
                this.path = request.fullFilePath
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.body = request.body.trim()
                this.statusNumber = statusCode.STATUS_CODE.toString()
                responseHeadersAndBody = ResponseProvider(this.request).redirect()
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
