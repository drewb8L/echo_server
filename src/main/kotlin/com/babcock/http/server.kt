package com.babcock

import com.babcock.http.HttpParser
import com.babcock.http.HttpRes
import com.babcock.http.Router
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.FileInputStream

val log = Log()

fun server(port: Int) {
    println("Echo Server")
    runBlocking {
        val sm = SelectorManager(Dispatchers.IO)
        val aSocket = aSocket(sm).tcp().bind("127.0.0.1", port)
        println("Server is listening at ${aSocket.localAddress}")
        while (true) {
            val socket = aSocket.accept()
            println("Connection made")
            println("Your connection context is: ${socket.socketContext}")
            launch {
                val input = socket.openReadChannel()
                val output = socket.openWriteChannel(autoFlush = true)
                val parser = HttpParser()

                val CRLF: String = "\n\r"

                val htmlIn = withContext(Dispatchers.IO) {
                    FileInputStream("src/main/resources/web_files/index.html").readAllBytes()
                }.toString(Charsets.UTF_8)





                log.logMessage("Server is listening on port $port")
                try {
                    val request = input.toInputStream()

                    log.logSuccess("Request received:${socket.socketContext}")

                    val req = parser.parseHttpReq(request)

                    val res = HttpRes(req)
                    res.responseHeadersAndBody?.let { output.writeStringUtf8(it) }


                    // HTTP/1.1 200 Ok
                    //val response: String = "${res.httpVersion} ${response2.statusCode}${CRLF} Content-Length: ${htmlIn.length}${CRLF}${CRLF}${htmlIn}${CRLF}${CRLF}"

//                   println(res.responseHeaders())
//                    println(res.responseBody())

                    //output.writeStringUtf8(res.response())
                    //println(res.body.length)
                    //output.writeStringUtf8(res.body)
                    // output body




                } catch (e: Throwable) {
                    withContext(Dispatchers.IO) {
                        log.logError("${e.message}")
                        socket.close()
                    }

                } finally {
                    withContext(Dispatchers.IO) {
                        socket.close()
                    }
                }
            }
        }


    }

}

