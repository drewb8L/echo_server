package com.babcock.http

import com.sun.jdi.IntegerType
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.min

enum class HttpVersion(val VERSION: String, val MAJOR: Int, val MINOR: Int) {
    HTTP_1_1("HTTP/1.1", 1, 1),;



    companion object{
        var httpVersionPattern: Pattern = Pattern.compile("^HTTP/(?<major>\\d+).(?<minor>\\d+)")
        fun getCompatibleVersion(inputVersion:String):HttpVersion?{
            val matcher: Matcher = httpVersionPattern.matcher(inputVersion)
            if (!matcher.find() || matcher.groupCount() != 2){

                throw HttpParseException(HttpStatusCode.SERVER_ERROR_505_INTERNAL_SERVER_ERROR)
            }
            val major = matcher.group("major").toInt()
            val minor = matcher.group("minor").toInt()
            var tempCompatibleVersion: HttpVersion? = null

            for(version:HttpVersion in HttpVersion.values()){
                if(version.VERSION == inputVersion){
                    return version
                }else {
                    if (version.MAJOR == major) {
                        if(version.MINOR < minor){
                            tempCompatibleVersion = version
                        }
                    }
                }
            }
            return  tempCompatibleVersion
        }
    }
}