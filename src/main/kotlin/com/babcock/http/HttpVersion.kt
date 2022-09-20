package com.babcock.http


import java.util.regex.Matcher
import java.util.regex.Pattern


enum class HttpVersion(val VERSION: String, val MAJOR: Int, val MINOR: Int) {
    HTTP_1_1("HTTP/1.1", 1, 1),
    HTTP_0_0("HTTP/0.0", 0, 0); // for error handling


    companion object {
        var httpVersionPattern: Pattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)")
        fun getCompatibleVersion(inputVersion: String): HttpVersion? {
            println("Getting version from input: $inputVersion")
            val matcher: Matcher = httpVersionPattern.matcher(inputVersion)
            if (!matcher.find() || matcher.groupCount() != 2) {

                //throw HttpParseException(HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR)
                return HTTP_0_0
            }
            val major = matcher.group("major").toInt()
            val minor = matcher.group("minor").toInt()
            println("${major}/${minor}")
            var tempCompatibleVersion: HttpVersion? = null

            for (version: HttpVersion in HttpVersion.values()) {
                if (version.VERSION == inputVersion) {
                    println("Returning version")
                    return version
                } else if (version.MAJOR > major) {
                    return HTTP_0_0
                    //throw HttpParseException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST)
                } else if (version.MAJOR < major) {
                    return HTTP_0_0
                } else {
                    if (version.MAJOR == major) {
                        if (version.MINOR < minor) {
                            println("Version: $version")
                            tempCompatibleVersion = HTTP_1_1
                            println("Version temp: $tempCompatibleVersion")
                            break
                        }
                    }
                }
            }
            println("Last value of temp compatible version $tempCompatibleVersion")
            return tempCompatibleVersion
        }
    }
}