package com.babcock

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


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
            launch {
                val rc = socket.openReadChannel()
                val sc = socket.openWriteChannel(autoFlush = true)
                sc.writeStringUtf8("Test msg")
                try {
                    while (true) {
                        val msg = rc.readUTF8Line()
                        sc.writeStringUtf8("$msg")
                        sc.close()
                    }
                } catch (e: Throwable) {
                    aSocket.close()
                }
            }
        }
    }

}

