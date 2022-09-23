package com.babcock.http

import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
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
        val contentType: String = "Content-Type: text/html$CRLF"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        //val contentLength:String = this.file?.available().toString()
        //var body:String? = this.file?.readAllBytes()?.toString(Charsets.UTF_8)
        val (body2: String, contentLength: String) = bodyAndLength("Hello world")
        return "$version $statusNumber $statusMsg$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body2"
    }

    private fun bodyAndLength(msg:String): Pair<String, String> {
        var resBody: InputStream = ByteArrayInputStream(msg.toByteArray())
        val body2: String = resBody.readAllBytes().toString(Charsets.UTF_8)
        val contentLength: String = body2.length.toString()
        return Pair(body2, contentLength)
    } //replaces

    fun headResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val CRLF: String = "\r\n"

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
        val CRLF: String = "\r\n"
        val allowed:String = "Allow: ${EndpointMethodProvider.endpointList[request.requestTarget].toString().removePrefix("[").removeSuffix("]")}\n\r"
        return "$version $statusNumber $statusMessage${CRLF}$allowed${CRLF}${CRLF}"
    }

    fun notFound404(): String {
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\r\n"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        val contentLength:String = this.file?.available().toString()
        val body:String? = this.file?.readAllBytes()?.toString(Charsets.UTF_8)
        return "$version $statusNumber $statusMessage${CRLF}$formattedDate${CRLF}"
    }
}