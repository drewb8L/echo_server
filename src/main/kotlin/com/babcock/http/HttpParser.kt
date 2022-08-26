package com.babcock.http
import com.babcock.Log
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

class HttpParser {
    private val log = Log()



    fun parseHttpReq(inputStream: InputStream): HttpReq{
        val reader = InputStreamReader(inputStream, StandardCharsets.US_ASCII)
        val request = HttpReq()

        try {
            parseRequestLine(reader, request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        parseHeaders(reader, request)
        parseBody(reader, request)

        return request

    }

    private fun parseBody(reader: InputStreamReader, request: HttpReq) {
        //TODO("Not yet implemented")
    }

    private fun parseHeaders(reader: InputStreamReader, request: HttpReq) {
        //TODO("Not yet implemented")
    }

    @Throws(IOException::class)
    private fun parseRequestLine(reader: InputStreamReader, request: HttpReq) {
        var byte: Int
        val dataBufferProcess = StringBuilder()

        while((reader.read().also { byte = it }) >=0){
            if(byte == CR){
                byte = reader.read()
                if (byte == LF){

                    return
                }
            }
            if (byte == SP){
                dataBufferProcess.delete(0, dataBufferProcess.length)
            } else {
                dataBufferProcess.append(byte.toChar())
            }

        }
    }

    companion object {

        // In request line SP = Space, CR = Carriage Return, LF = Line Feed
        private const val SP: Int = 0x20
        private const val CR: Int = 0x0d
        private const val LF: Int = 0x0a

    }
}