package com.babcock.http


import io.ktor.utils.io.*
import javax.xml.stream.events.Characters
import kotlin.Throws


enum class HttpMethod {
    GET, HEAD, POST, OPTIONS;


    companion object {

        fun convertToMethod(method: String): HttpMethod {
            method.trim()
            val code = when (method) {
                "GET" -> GET
                "HEAD" -> HEAD
                "POST" -> POST
                "OPTIONS" -> OPTIONS
                else -> {
                    throw HttpParseException(HttpStatusCode.CLIENT_ERROR_405_METHOD_NOT_ALLOWED)
                }
            }
            return code
        }

    }


}