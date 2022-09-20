package com.babcock.http

import java.io.FileInputStream
import java.util.*

class HttpRes(request: HttpReq) {
    lateinit var statusCode:HttpStatusCode
    lateinit var statusMessage:String
    lateinit var statusNumber:String
    var version: String
    var target:FileInputStream? = null
    var responseHeadersAndBody:String? = null


    init {
        this.version = request.httpVersion

        this.target = Router().handleTarget(request)
        try {
            this.statusCode = request.statusCode
            this.statusMessage = statusCode.MESSAGE
            this.statusNumber = statusCode.STATUS_CODE.toString()
            responseHeadersAndBody = this.responseHeaders(target!!)
        }catch (e:Throwable){
            e.printStackTrace()
        }

    }



 fun responseHeaders(file:FileInputStream):String{
     val version = this.version
     val statusNumber = this.statusNumber
     val CRLF: String = "\n\r"
     val conn = "Connection: Keep-Alive${CRLF}"
     var contentType: String = "Content-Type: text/html$CRLF"
     val date = Date()
     val formattedDate = "Date: $date$CRLF"

     val contentLength = file.available().toString()
     val body = file.readAllBytes().toString(Charsets.UTF_8)
     return "$version $statusNumber$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
    }

}
