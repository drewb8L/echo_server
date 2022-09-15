package com.babcock.http

data class HttpRes(val status: HttpStatusCode, val body: String? = null)
