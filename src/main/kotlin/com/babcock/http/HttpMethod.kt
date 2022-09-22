package com.babcock.http


import io.ktor.utils.io.*
import javax.xml.stream.events.Characters
import kotlin.Throws


enum class HttpMethod {
    GET, HEAD, POST, OPTIONS, PUT;


    companion object {

        fun convertToMethod(method: String): HttpMethod {
            method.trim()
            val code = when (method) {
                "GET" -> GET
                "HEAD" -> HEAD
                "POST" -> POST
                "OPTIONS" -> OPTIONS
                "PUT" ->  PUT
                else -> {
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)//TODO: change to set status code on response
                }
            }
            return code
        }

    }


}