package com.babcock.http

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
            HttpMethod.HEAD -> headResponse()
            HttpMethod.POST ->postResponse()
            HttpMethod.OPTIONS -> optionsResponse()
            HttpMethod.PUT -> putResponse()
        }
    }



    fun getResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val CRLF: String = "\n\r"
        val conn:String = "Connection: Keep-Alive${CRLF}"
        var contentType: String = "Content-Type: text/html$CRLF"//TODO: Set programmatically based on file type
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        val contentLength:String = this.file?.available().toString()
        val body:String? = this.file?.readAllBytes()?.toString(Charsets.UTF_8)
        return "$version $statusNumber$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
    }

    fun headResponse():String{
        TODO("Not yet implemented")
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
        val contentLength:String = this.file?.available().toString()
        val body:String = ""
        return "$version $statusNumber $statusMessage${CRLF}$allowed${CRLF}${CRLF}$body"
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