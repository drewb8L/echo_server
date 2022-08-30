package com.babcock.http


import kotlin.Throws


enum class HttpMethod {
    GET, HEAD;

    companion object{

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