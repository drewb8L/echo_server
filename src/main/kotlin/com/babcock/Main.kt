package com.babcock

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


fun mainTest(): Int {
    return 1
}


fun main() {
    server()
}


fun server() {
    println("Echo Server")
    runBlocking {
        val sm = SelectorManager(Dispatchers.IO)
        val aSocket = aSocket(sm).tcp().bind("127.0.0.1", 8080)
        println("Server is listening at ${aSocket.localAddress}")
        while (true) {
            val socket = aSocket.accept()
            println("Connection made")
            println("Your connection context is: ${socket.socketContext}")
            launch {
                val rc = socket.openReadChannel()
                val sc = socket.openWriteChannel(autoFlush = true)
                sc.writeStringUtf8("Enter some text to send to the server, close connection by entering '.':\n")
                try {
                    while (true) {
                        sc.writeStringUtf8("${socket.socketContext}: ")
                        val msg = rc.readUTF8Line()
                        if (msg == ".") {
                            sc.writeStringUtf8("Goodbye! \n")
                            withContext(Dispatchers.IO) {
                                socket.close()
                            }
                        } else {
                            sc.writeStringUtf8("Server echos with: $msg\n")
                        }
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

