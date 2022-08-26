package com.babcock.http

enum class HttpMethod {
    GET, HEAD;

    companion object{
        fun convertToMethod(method:String): HttpMethod{
            method.trim()
            return(
                    when (method) {
                        "GET" -> GET
                        "HEAD" -> HEAD
                        else -> {HEAD}
                    }

                    )

        }
    }


}