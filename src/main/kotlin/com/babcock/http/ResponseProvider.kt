package com.babcock.http

import java.io.ByteArrayInputStream

import java.io.FileInputStream
import java.io.InputStream
import java.util.*


class ResponseProvider(request: HttpReq) {
    var file:FileInputStream? = null
    val request:HttpReq

    init {
        this.request = request
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

    private fun putResponse(): String {
        return ""
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
        return if(request.requestTarget == "/simple_get_with_body" || request.requestTarget == "/"){
            val (body: String, contentLength: String) = bodyAndLength("Hello world")
            this.request.body = body
            "$version $statusNumber $statusMsg$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
        }else{
            val (body: String, contentLength: String) = bodyAndLength("")
            this.request.body = body
        "$version $statusNumber $statusMsg$CRLF$conn$contentType${formattedDate}Content-Length: ${contentLength}${CRLF}${CRLF}$body"
        }
    }

    private fun bodyAndLength(msg:String): Pair<String, String> {
        var resBody: InputStream = ByteArrayInputStream(msg.toByteArray())
        val body2: String = resBody.readAllBytes().toString(Charsets.UTF_8)
        val contentLength: String = body2.length.toString()
        return Pair(body2, contentLength)
    }

    fun headResponse(request: HttpReq):String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val CRLF: String = "\r\n"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        return "$version $statusNumber $statusMsg$CRLF${formattedDate}${CRLF}${CRLF}"
    }

    fun postResponse():String{
        val CRLF: String = "\r\n"
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val contentType: String = "Content-Type: text/html$CRLF"
        val contentLength:String = "Content-Length: ${request.body.length}"
        val body = request.body.trim()
        return "$version $statusNumber $statusMsg$CRLF$contentType$contentType$contentLength${CRLF}$body"
    }


    fun optionsResponse():String{
        request.body = ""
        val version:String = request.httpVersion
        val statusNumber:String = request.statusCode.STATUS_CODE.toString()
        val statusMsg:String = request.statusCode.MESSAGE
        val allowed:String = "Allow: ${EndpointMethodProvider.endpointList[request.requestTarget].toString().removePrefix("[").removeSuffix("]")}\n\r"
        val CRLF: String = "\r\n"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        return "$version $statusNumber $statusMsg$CRLF${formattedDate}$allowed${CRLF}${CRLF}"
    }

    fun notImplementedResponse():String{
        TODO("Not yet implemented")
    }



    fun notAllowedResponse():String {
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\r\n"
        val body = request.body.trim()
        val allowed:String = "Allow: ${EndpointMethodProvider.endpointList[request.requestTarget].toString().removePrefix("[").removeSuffix("]")}\n\r"
        return "$version $statusNumber $statusMessage${CRLF}$allowed${CRLF}${CRLF}$body"
    }

    fun notFound404(): String {
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\r\n"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"

        return "$version $statusNumber $statusMessage${CRLF}$formattedDate${CRLF}"
    }

    fun redirect():String{
        val version:String = request.httpVersion
        val statusNumber:String = request.statusNumber
        val statusMessage:String = request.statusMsg
        val CRLF: String = "\r\n"
        val date:Date = Date()
        val formattedDate:String = "Date: $date$CRLF"
        val location:String = "Location: ${request.fullFilePath}$CRLF"

        return "$version $statusNumber $statusMessage${CRLF}$formattedDate$location${CRLF}"
    }

}