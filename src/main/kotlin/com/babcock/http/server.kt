package com.babcock

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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
                //if (request == "GET / HTTP/1.1" || request == "GET /favicon.ico HTTP/1.1"){
                log.logMessage("Server is listening on port $port")
                try {
                    while (true) {
                        output.writeStringUtf8("${socket.socketContext}: ")
                        val request = input.readUTF8Line()

                        log.logSuccess("Request received:, $request / ${socket.socketContext}")
                        output.writeStringUtf8(
                            """
            HTTP/1.1 200 OK
            
            <html><head><title> Kotlin http server</title></head><body><h1 style="color:blue;">Hello, beautiful world!</h1></body></html>""".trimIndent()
                        )


                    }
                } catch (e: Throwable) {
                    withContext(Dispatchers.IO) {
                        socket.close()
                    }
                }
            }
        }
    }

}