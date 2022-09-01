package com.babcock.http


import io.ktor.utils.io.*
import kotlin.Throws


enum class HttpMethod {
    GET, HEAD;


    companion object{
        fun maxLength(request:String):Boolean{
            println("Size in bytes: ${request.length.toByte()}")
            val maxSize:Byte = 2083.toByte()
            return(
                    request.length.toByte() > maxSize
                    )
        }

        fun convertToMethod(method:String):HttpMethod {
            method.trim()
          val code = when(method){
               "GET" -> GET
               "HEAD" -> HEAD
              else -> {throw HttpParseException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED)}
          }
            return code
        }
    }


}