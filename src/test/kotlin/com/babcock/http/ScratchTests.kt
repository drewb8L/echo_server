//package com.babcock.http
//
//import com.babcock.testHelpers.Generator
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.Test
//import kotlin.test.assertFailsWith
//import org.hamcrest.MatcherAssert.assertThat
//import org.hamcrest.CoreMatchers.containsString
//import kotlin.io.path.Path
//import com.babcock.testHelpers.Utils
//import io.ktor.util.*
//import java.nio.file.Files
//import java.nio.file.Path
//import java.nio.file.Paths
//import kotlin.io.path.name
//
//internal class ScratchTests {
//    val httpParser = HttpParser()
//
//    @Test
//    fun handleEndpoint(){
//        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGet())
//        val response = HttpRes(request)
//        assertEquals("200", response.statusNumber)
//    }
//
//  @Test
//  fun isEndpoint(){
//      val webRoot = "src/main/resources/web_files"
//      val rootTarget = "/"
//      val getTarget = "/simple_get"
//      val invalidEndpoint = "/not_an_endpoint" // does not exist
//      val targetToRootFile = "/index.html"
//      val targetToNestedFile = "/some_endpoint/index.html"
//
//      val pattern = Regex(pattern = "^/.*$")
//      val target:String = getTarget
//
//      if (pattern.matches(target)){
//          val path = Paths.get("$webRoot$target")
//          when (path.let { Files.isDirectory(it) }) {
//              true -> println("$path Is a valid endpoint")
//              else -> {
//                  println("Not an endpoint")
//              }
//          }
//      } else {
//          println("Not an endpoint")
//      }
//
//  }
//
//    @Test
//    fun getBody(){
//        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
//        val response = HttpRes(request)
//        println(response.body)
//    }
//    @Test
//    fun endpointsAndMethods(){
//        val request = httpParser.parseHttpReq(Generator.getRequestToHead())
//        val response = HttpRes(request)
//
//
//        println("RESPONSE:\n${response.responseHeadersAndBody}")
//    }
//
//    @Test
//    fun resourceType(){
//        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
//        val response = HttpRes(request)
//    }
//
//
//
//
//    @Test
//    fun validEndpoint(){ //do not keep test
//        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
//        val regex = Regex("^/.*_.*\$", setOf(RegexOption.IGNORE_CASE))
//        request.fullFilePath = Paths.get("src/main/resources/web_files",request.requestTarget).toString()
//        val dirName:String = Paths.get("src/main/resources/web_files",request.requestTarget).fileName.toString()
//        val result = regex.matches(request.requestTarget)
//        println("ENDPOINT IS A DIR: ${request.fullFilePath?.let { Files.isDirectory(Path.of(it)) }}")
//        println(dirName)
//        println(request.fullFilePath.toString())
//        Router(request).handleResourceType()
//        //val res = HttpRes(request)
//        //println(res.toString())
//
//    }
//
//    @Test
//    fun invalidEndpoint(){
//        val request = httpParser.parseHttpReq(Generator.validParseTestCaseTo404())
//        val regex = Regex("^/.*_.*\$", setOf(RegexOption.IGNORE_CASE))
//        val result = regex.matches(request.requestTarget)
//        println("ENDPOINT: ${request.requestTarget} \n MATCH: $result")
//
//    }
//
//    @Test
//    fun reType(){
//        val request = httpParser.parseHttpReq(Generator.getRequestToSimpleGetBody())
//        Router(request).handleResourceType()
//    }
//
//    @Test
//    fun requestBody(){
//        val request = httpParser.parseHttpReq(Generator.postRequestWithBody())
//        val response = HttpRes(request)
//        println(request.requestToString())
//        println()
//        println(response.body)
//    }
//
//    @Test
//    fun redirect(){
//        val request = httpParser.parseHttpReq(Generator.requestToRedirect())
//        val response = HttpRes(request)
//        println(response.toString())
//
//    }
//
//
//}