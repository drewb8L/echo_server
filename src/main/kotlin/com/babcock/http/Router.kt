package com.babcock.http

import io.ktor.utils.io.core.*
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

// TODO: explicit types
class Router {


    fun handleFileTarget(request: HttpReq, path: String): FileInputStream {

        ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
        request.fullFilePath = Paths.get(path)
        return FileInputStream(path)

    }

    fun fileMatcher(path: String): Boolean {
        val pattern = Regex(".html")
        return pattern.containsMatchIn(path)
    }

    fun getFileOrResource(request: HttpReq, target: String = request.requestTarget) {
        val file = target.lowercase().trim()
        val webRoot = Paths.get("src/main/resources/web_files")
        var path: Path
        val matchFile = fileMatcher(file)

        if(target == "/"){
            path = Paths.get("$webRoot${target}index.html")
            request.fullFilePath = Paths.get(path.toString())
            ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
            ResponseProvider(request).getResponse(request)
        } else if (EndpointRouter().isValidEndpoint(target)) {
            path = Paths.get(webRoot.toString(), target)
            request.fullFilePath = Paths.get("$path")
            EndpointRouter().provideResource(request, request.fullFilePath.toString())
        } else if (matchFile) {
            path = Paths.get(webRoot.toString(), target)
            if (Files.exists(path)) {
                request.fullFilePath = path
                handleFileTarget(request, request.fullFilePath.toString())
            } else {
                ResponseStatus().setStatus(request, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND)
                request.fullFilePath = Paths.get("src/main/resources/web_files/400/404.html")
                ResponseProvider(request).notFound404()

            }
        }

    }


}