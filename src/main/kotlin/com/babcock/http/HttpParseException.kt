package com.babcock.http

import java.lang.Exception

class HttpParseException(errorCode:HttpStatusCode): Exception(errorCode.MESSAGE) {
}