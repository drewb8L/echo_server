package com.babcock.http

import java.io.FileInputStream
import java.nio.file.Path
import java.util.*

class HttpRes(request: HttpReq) {
    lateinit var statusCode:HttpStatusCode
    lateinit var statusMessage:String
    lateinit var statusNumber:String
    lateinit var version: String
    var target:FileInputStream? = null
    var responseHeadersAndBody:String? = null
    var path: Path? = null
    var body: FileInputStream? = null

    init {

        //this.target = Router().getFileOrResource(request)
        try {
            Router().getFileOrResource(request)
            this.target = FileInputStream("${request.fullFilePath}")
            this.version = request.httpVersion
            this.path = request.fullFilePath
            this.statusCode = request.statusCode
            this.statusMessage = statusCode.MESSAGE
            this.statusNumber = statusCode.STATUS_CODE.toString()
            responseHeadersAndBody = EndpointRouter().provideResource(request) //this.responseHeaders(target!!)
        }catch (e:Throwable){
            e.printStackTrace()
        }


    }


}
