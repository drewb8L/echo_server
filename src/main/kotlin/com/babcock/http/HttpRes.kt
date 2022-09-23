package com.babcock.http

import java.io.FileInputStream
import java.nio.file.Path

class HttpRes(request: HttpReq) {
    lateinit var statusCode:HttpStatusCode
    lateinit var statusMessage:String
    lateinit var statusNumber:String
    lateinit var version: String
    lateinit var target: Path
    lateinit var responseHeadersAndBody:String
    lateinit var path: Path
    lateinit var body: FileInputStream//? = null
    var request = request

    init {


        try {
            Router(this.request).handleResourceType() //.getFileOrResource(this.request)
            if(this.request.statusCode == HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND){
                this.target = request.fullFilePath!! //= FileInputStream("${request.fullFilePath}")
                this.version = request.httpVersion
                this.path = request.fullFilePath!!
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.body = FileInputStream(target.toString())
                this.statusNumber = statusCode.STATUS_CODE.toString()
                responseHeadersAndBody = ResponseProvider(this.request).notFound404()
            }else {
                responseHeadersAndBody = EndpointRouter().provideResource(request)
                this.target = request.fullFilePath!! //FileInputStream("${request.fullFilePath}")
                this.version = request.httpVersion
                this.path = request.fullFilePath!!
                this.statusCode = request.statusCode
                this.statusMessage = statusCode.MESSAGE
                this.body = FileInputStream(target.toString())
                this.statusNumber = statusCode.STATUS_CODE.toString()

            }
        }catch (e:Throwable){
            e.printStackTrace()
        }


    }


}
