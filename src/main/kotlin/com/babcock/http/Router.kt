package com.babcock.http


import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


class Router(var request: HttpReq) {

    fun endpointMatcher(path:String): Boolean{
        val pattern = Regex("^/.*_?.*\$", setOf(RegexOption.IGNORE_CASE))
        return pattern.containsMatchIn(path)
    }

    fun handleResourceType() {
        val resource = request.requestTarget.lowercase().trim()
        val webRoot = Paths.get("src/main/resources/web_files")
        var path: Path = Paths.get(webRoot.toString(), resource)

        var isResourceFound = false

        while (!isResourceFound) {
            if (resource == "/redirect"){
                ResponseStatus().setStatus(request, HttpStatusCode.MULTIPLE_CHOICES_301)
                request.fullFilePath = Paths.get("localhost:5000/simple_get").toString()
                ResponseProvider(request).redirect()
                break
            }
            if (resource == "/") { //handles root/
                setFilePathToIndex(path) // TODO: (path, webroot, resource)
                ResponseProvider(request).handleResponseByMethod()
                break


            }else{
                ResponseStatus().setStatus(request, HttpStatusCode.CLIENT_ERROR_404_NOT_FOUND)
                request.fullFilePath = Paths.get("src/main/resources/web_files/400/404.html").toString()
                ResponseProvider(request).notFound404()
            }

            if (endpointMatcher(resource) && Files.isDirectory(path)){ //handles endpoints
                request.fullFilePath = Paths.get("$path").toString()
                EndpointRouter().provideResource(request, request.fullFilePath.toString()) // TODO: <- setting valid endpoints to 405
                }
                isResourceFound = true
        }
    }



    private fun setFilePathToIndex(path: Path) { // TODO: (path, webroot, resource)
        //var filePath: Path = Paths.get("$webRoot${resource}index.html")
        var filePath:Path = Paths.get("${path}index.html")
        request.fullFilePath = Paths.get(filePath.toString()).toString()
        ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)
        ResponseProvider(request).getResponse(request)

    }


    fun handlePostRequest() {
        ResponseStatus().setStatus(request, HttpStatusCode.SUCCESS_200_OK)


    }



}