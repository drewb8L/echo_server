package com.babcock

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking



class Main {
    fun mainTest(): Int {
        return 1
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("Echo Server")
            val sm = SelectorManager(Dispatchers.IO)
            val aSocket = aSocket(sm).tcp().bind("127.0.0.1", 8080)
            println("Server is listening at ${aSocket.localAddress}, Context: ${aSocket.socketContext}")
            aSocket.close()

        }
    }

}