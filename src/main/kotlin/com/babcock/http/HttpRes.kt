package com.babcock.http

import com.babcock.log
import kotlinx.coroutines.*
import java.io.FileInputStream
import java.util.*

class HttpRes(request: HttpReq) {
    var statusCode:HttpStatusCode //make router set code
    var statusMessage:String = ""
    var statusNumber:Int = 0
    var version: String
    var target:FileInputStream? = null
    var responseHeadersAndBody:String? = null


    init {
        this.statusCode = request.statusCode
        this.version = request.httpVersion
        this.statusNumber = request.statusNumber
        this.statusMessage = request.statusMsg
        this.target = Router().handleTarget(request)
        try {
            responseHeadersAndBody = this.responseHeaders(target!!)
        }catch (e:Throwable){
            e.printStackTrace()
        }

    }


//    fun status(request: HttpReq) {
//       if (request.statusCode == HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
//    }


 fun responseHeaders(file:FileInputStream):String{
     //val htmlIn = FileInputStream("src/main/resources/web_files/index.html")
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

    fun responseBody():FileInputStream?{
        return target
    }

//    fun response():String {
//        var res = ""
//        val CRLF: String = "\n\r"
//        val htmlIn = FileInputStream("src/main/resources/web_files/index.html")//.readAllBytes().toString(Charsets.UTF_8)
//        //var body = htmlIn.readAllBytes().toString(Charsets.UTF_8)
//        val html404 = " \"<html><head><title> Kotlin http server</title></head><body><h1 style=\\\"color:red;\\\">404 not found</h1></body></html>\""
//        log.logMessage("Length: ${responseHeaders(htmlIn)}")
//        res = if (status()) {
//            body = htmlIn.readAllBytes().toString(Charsets.UTF_8)
//
//            responseHeaders(htmlIn)
//
//            //"${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF}Content-Length: ${htmlIn.length}${CRLF}${CRLF}${htmlIn}${CRLF}${CRLF}"
//        } else {
//            log.logError("Error")
//            "${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF} Content-Length: ${html404.length}${CRLF}${CRLF}${html404}${CRLF}${CRLF}"
//
//        }
//        return res
//    }
}
