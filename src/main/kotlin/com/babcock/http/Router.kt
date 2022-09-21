package com.babcock.http

import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// TODO: explicit types
class Router {


    fun handleTarget(request: HttpReq): FileInputStream { // find files instead of hard code, target params
        val target = getFile(request.requestTarget)
        val requestTarget = when (target) {

            "not found" -> {
                ResponseStatus().setStatus(request, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND)
                request.fullFilePath = Paths.get(target)
                FileInputStream("src/main/resources/web_files/400/404.html")
            }

            else -> {
                ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
                request.fullFilePath = Paths.get(target)
                FileInputStream(target)
            }
        }

        return requestTarget
    }

    fun fileMatcher(path: String): Boolean {
        val pattern = Regex(".html")
        return pattern.containsMatchIn(path)
    }

    fun getFile(target: String): String {
        val file = target.lowercase().trim()
        val webRoot = Paths.get("src/main/resources/web_files")
        val path: Path?
        val match = fileMatcher(file)

        if (file == "/") {
            path = Paths.get("src/main/resources/web_files/index.html")

        } else if (match) {
            path = Paths.get(webRoot.toString(), target)
        } else {
            path = Paths.get(webRoot.toString(), "$target.html")
        }
        return when (path?.let { Files.exists(it) }) {
            true -> path.toString()
            else -> {
                "not found"
            }
        }
    }


}