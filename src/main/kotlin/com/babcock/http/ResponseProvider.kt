package com.babcock.http

import java.io.File
import java.io.FileInputStream
import java.util.*


class ResponseProvider(request: HttpReq) {
    var file:FileInputStream? = null
    val request:HttpReq

    init {
        this.request = request
        this.file = FileInputStream(request.fullFilePath.toString())
    }

    fun handleResponseByMethod(request: HttpReq = this.request):String {
        return when(request.method){
            HttpMethod.GET -> getResponse(request)
            HttpMethod.HEAD -> headResponse(request)
            HttpMethod.POST ->postResponse()
            HttpMethod.OPTIONS -> optionsResponse()
            HttpMethod.PUT -> putResponse()
        }
    }



    fun getResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val CRLF: String = "\r\n"
        val conn:String = "Connection: Keep-Alive${CRLF}"
        val contentType: String = "Content-Type: text/html$CRLF"//TODO: Set programmatically based on file type
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        val contentLength:String = this.file?.available().toString()
        val body:String? = this.file?.readAllBytes()?.toString(Charsets.UTF_8)
        return "$version $statusNumber $statusMsg$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
    }

    fun headResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val CRLF: String = "\n\r"

        return "$version $statusNumber $statusMsg$CRLF${CRLF}${CRLF}"
    }

    fun postResponse():String{
        TODO("Not yet implemented")
    }

    private fun putResponse():String {
        TODO("Not yet implemented")
    }

    fun optionsResponse():String{
        TODO("Not yet implemented")
    }

    fun notImplementedResponse():String{
        TODO("Not yet implemented")
    }



    fun notAllowedResponse():String {
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\n\r"
        val allowed:String = "Allow: ${EndpointMethodProvider.endpointList[request.requestTarget].toString().removePrefix("[").removeSuffix("]")}\n\r"
        return "$version $statusNumber $statusMessage${CRLF}$allowed${CRLF}${CRLF}"
    }

    fun notFound404(): String {
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\n\r"
        val contentLength:String = this.file?.available().toString()
        val body:String? = this.file?.readAllBytes()?.toString(Charsets.UTF_8)
        return "$version $statusNumber $statusMessage${CRLF}${CRLF}"
    }
}