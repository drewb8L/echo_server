package com.babcock.http

import java.io.FileInputStream
import java.util.*


class ResponseProvider(request: HttpReq, file: FileInputStream) {
    val file:FileInputStream // may need a nul check
    init {
        this.file = file
        when(request.method){
            HttpMethod.GET -> getResponse(request)
            HttpMethod.HEAD -> headResponse()
            HttpMethod.POST ->postResponse()
            HttpMethod.OPTIONS -> optionsResponse()
            else -> {
                notImplementedResponse()
            }
        }
    }

    fun allowedMethods():String{


        return "Allow: GET, HEAD"
    }

    fun getResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val CRLF: String = "\n\r"
        val conn:String = "Connection: Keep-Alive${CRLF}"
        var contentType: String = "Content-Type: text/html$CRLF"//TODO: Set programmatically based on file type
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"

        val contentLength:String = this.file.available().toString()
        val body:String = this.file.readAllBytes().toString(Charsets.UTF_8)
        return "$version $statusNumber$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
    }

    fun headResponse(){

    }

    fun postResponse(){

    }


    fun optionsResponse(){

    }

    fun notImplementedResponse(){

    }

    fun notAllowedResponse(){

    }
}