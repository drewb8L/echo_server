package com.babcock.http

data class HttpRes(val status: HttpStatusCode, val headers: HttpHeaders = HttpHeaders(), val body: String? = null)
