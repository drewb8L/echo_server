package com.babcock.http

import kotlinx.coroutines.yield
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

    fun getFileOrResource(request: HttpReq, target: String = request.requestTarget): FileInputStream? {
        val file = target.lowercase().trim()
        val webRoot = Paths.get("src/main/resources/web_files")
        var path: Path
        val match = fileMatcher(file)
        var resource:FileInputStream? = null

        if (EndpointRouter().isValidEndpoint(target)) {
            path = Paths.get(webRoot.toString(), target)
            //resource = EndpointRouter().provideResource(request, path.toString())
        } else if (match) {
            path = Paths.get(webRoot.toString(), target)
            if (Files.exists(path)) {
                resource = handleFileTarget(request, path.toString())
            } else {
                ResponseStatus().setStatus(request, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND)
                request.fullFilePath = Paths.get("src/main/resources/web_files/400/404.html")
                resource = FileInputStream("src/main/resources/web_files/400/404.html")
            }
        }
        return resource
    }


}