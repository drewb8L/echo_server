package com.babcock.http

class ResponseStatus {
    fun setStatus(request: HttpReq, status: HttpStatusCode): HttpReq {
        request.statusCode = status
        request.statusMsg = status.MESSAGE
        request.statusNumber = status.STATUS_CODE.toString()
        return request
    }
}