package com.babcock.http

import com.babcock.log
import java.io.FileInputStream

class HttpRes{
    var ressponseData: String = ""
    var statusCode:HttpStatusCode
    var statusMessage:String = ""
    var statusNumber:Int = 0
    var version: String
    var contentType: String = "text/html"
constructor(request: HttpReq){
    this.statusCode = request.statusCode
   this.version = request.httpVersion
   this.statusNumber = request.statusNumber
    this.statusMessage = request.statusMsg
}
    fun status():Boolean {
        val status:Boolean = if (this.statusCode == HttpStatusCode.SUCCESS_200_OK){
            log.logSuccess("Status 200 OK!")
            true
        }else {
            log.logError(this.statusCode.toString())
            false
        }
        return status
    }

    fun response():String {
        var res = ""
        val CRLF: String = "\n\r"
        val htmlIn = FileInputStream("src/main/resources/web_files/index.html").readAllBytes().toString(Charsets.UTF_8)
        val html404 = " \"<html><head><title> Kotlin http server</title></head><body><h1 style=\\\"color:red;\\\">404 not found</h1></body></html>\""
        if (status()) {
            res = "${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF} Content-Length: ${htmlIn.length}${CRLF}${CRLF}${htmlIn}${CRLF}${CRLF}"
        } else {
            log.logError("Error")
            res = "${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF} Content-Length: ${html404.length}${CRLF}${CRLF}${html404}${CRLF}${CRLF}"

        }
        return res
    }
}
