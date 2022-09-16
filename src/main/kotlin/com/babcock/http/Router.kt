package com.babcock.http

import java.io.FileInputStream
import java.net.http.HttpRequest

class Router() {
//    val target: String = request.requestTarget

    fun handleTarget(request: HttpReq):FileInputStream {
        val requestTarget = when(request.requestTarget){
            "/" -> FileInputStream("src/main/resources/web_files/index.html")

            else -> {
                println("Target: ${request.requestTarget}")
                FileInputStream("src/main/resources/web_files/400/404.html")
            }
        }
        return requestTarget

    }

    fun calculateFileSize(){

    }

}