package com.babcock.http

import com.babcock.log
import kotlinx.coroutines.*
import java.io.FileInputStream
import java.util.*

class HttpRes(request: HttpReq) {
    var statusCode:HttpStatusCode
    var statusMessage:String = ""
    var statusNumber:Int = 0
    var version: String
    var target:FileInputStream? = null


    init {
        this.statusCode = request.statusCode
        this.version = request.httpVersion
        this.statusNumber = request.statusNumber
        this.statusMessage = request.statusMsg
        this.target = Router().handleTarget(request)
    }


    fun status():Boolean {
        val status:Boolean = if (this.statusCode == HttpStatusCode.SUCCESS_200_OK){
            true
        }else {
            log.logError(this.statusCode.toString())
            false
        }
        return status
    }
    lateinit var body:String
    @OptIn(DelicateCoroutinesApi::class)
 fun responseHeaders(file:FileInputStream):String{
        val CRLF: String = "\n\r"
        val status = "${this.statusNumber} ${this.statusMessage}${CRLF}"
        val conn = "Connection: Keep-Alive${CRLF}"
        val contentType: String = "Content-Type: text/html$CRLF"
        val date = Date()
        val formattedDate = "Date: $date$CRLF"

        val length = fun(file:FileInputStream): String = file.available().toString()



        return "$version $status${conn}${contentType}Content-Length: 364${CRLF}$formattedDate${CRLF}${CRLF}"
    }


    fun response():String {
        var res = ""
        val CRLF: String = "\n\r"
        val htmlIn = FileInputStream("src/main/resources/web_files/index.html")//.readAllBytes().toString(Charsets.UTF_8)
        //var body = htmlIn.readAllBytes().toString(Charsets.UTF_8)
        val html404 = " \"<html><head><title> Kotlin http server</title></head><body><h1 style=\\\"color:red;\\\">404 not found</h1></body></html>\""
        log.logMessage("Length: ${responseHeaders(htmlIn)}")
        res = if (status()) {
            body = htmlIn.readAllBytes().toString(Charsets.UTF_8)

            responseHeaders(htmlIn)

            //"${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF}Content-Length: ${htmlIn.length}${CRLF}${CRLF}${htmlIn}${CRLF}${CRLF}"
        } else {
            log.logError("Error")
            "${this.version} ${this.statusNumber} ${this.statusMessage}${CRLF} Content-Length: ${html404.length}${CRLF}${CRLF}${html404}${CRLF}${CRLF}"

        }
        return res
    }
}
