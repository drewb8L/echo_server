class Log {
    // ANSI escape code
    private val ANSI_RED = "\u001B[31m"
    private val ANSI_GREEN = "\u001B[32m"
    private val ANSI_YELLOW = "\u001B[33m"
    private val ANSI_WHITE = "\u001B[37m"



    fun logError(error: String) {
        println("$ANSI_RED $error")
    }

    fun logWarning(warning: String) {
        println("$ANSI_YELLOW $warning")
    }

    fun logSuccess(success: String) {
        println("$ANSI_GREEN $success")
    }

    fun logMessage(msg: String){
        println("$ANSI_WHITE $msg")
    }

}