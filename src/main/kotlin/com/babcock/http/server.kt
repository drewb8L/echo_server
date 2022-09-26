package com.babcock.http

import com.babcock.Log
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.jvm.javaio.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

val log = Log()
//TODO: server port usage checking, increment port number by 1 if port is in use and ask for confirmation
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

                log.logMessage("Server is listening on port $port")
                try {
                    val request = input.toInputStream()

                    log.logSuccess("Request received:${socket.socketContext}")

                    val req = parser.parseHttpReq(request)

                    val res = HttpRes(req)
                    res.responseHeadersAndBody.let { output.writeStringUtf8(it) }


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

