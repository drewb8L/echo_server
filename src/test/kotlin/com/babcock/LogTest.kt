package com.babcock

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class LogTest {
    var log = Log()
    @Test
    fun logError() {
        log.logError("Errors are red")
    }

    @Test
    fun logWarning() {
        log.logWarning("Warnings are yellow")
    }

    @Test
    fun logSuccess() {
        log.logSuccess("Success is green")
    }

    @Test
    fun logMessage() {
        log.logMessage("Messages are white")
    }
}