package com.babcock.http

import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.properties.Delegates


class Router() {


    fun handleTarget(request: HttpReq):FileInputStream { // find files instead of hard code, target params
        val target = getFile(request.requestTarget)
        val requestTarget = when(target){

            "not found" -> {
                FileInputStream("src/main/resources/web_files/400/404.html")
            }
            else -> {
                FileInputStream(target)
            }
        }

        return requestTarget
    }

    fun getFile(target: String): String {
        val webRoot = "src/main/resources/web_files"
        val path = Path("$webRoot$target.html")
        return if (target == "/"){
            "src/main/resources/web_files/index.html"
        } else {
            when (Files.exists(path)) {
                true -> path.toString()
                else -> {
                    println(path)
                    "not found"
                }
            }
        }
    }

    fun handleErrorStatusCode(request: HttpReq){
         when(request.statusCode){
             HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST -> FileInputStream("src/main/resources/web_files/400/400.html")

             else -> {

             }
         }
    }

}