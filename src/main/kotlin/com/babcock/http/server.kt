package com.babcock

import com.babcock.http.HttpParseException
import com.babcock.http.HttpParser
import com.babcock.http.HttpStatusCode
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.w3c.dom.html.HTMLTableCaptionElement
import java.io.InputStream
import java.lang.Thread.sleep

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

                val html: String =
                    "<html><head><title> Kotlin http server</title></head><body><h1 style=\"color:blue;\">Hello, beautiful world!</h1></body></html>"
                val CRLF: String = "\n\r"

                val response: String = "HTTP/1.1 200 OK${CRLF}" + "Content-Length: ${html.toByteArray().size}${CRLF}${CRLF}${html}${CRLF}${CRLF}"

                log.logMessage("Server is listening on port $port")
                try {
                    val request = input.readUTF8Line()
                    log.logSuccess("Request received:, $request / ${socket.socketContext}")


                    val res = request?.let { parser.parseHttpReq(it.byteInputStream()) }
                    if (res != null) {
                        output.writeStringUtf8(res.method.toString())
                    } else {
                        log.logError("Null")
                    }

                    //log.logSuccess("${res.method}")
                    //val response: String = "${res.compatibleHttpVersion} 200 OK$CRLF" +
                            //"Content-Length: ${html.toByteArray().size}$CRLF$CRLF$CRLF$html$CRLF$CRLF"



                    //log.logWarning(response)
                    output.writeStringUtf8(response)


                } catch (e: Throwable) {
                    withContext(Dispatchers.IO) {
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