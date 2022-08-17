import com.babcock.Main
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.io.IOException
import kotlin.jvm.Throws
import kotlin.test.assertEquals


class Test{
    @Test
    fun testMain(){
        val main = Main()
        assertEquals(main.mainTest(), 1)
    }

    @Test
    @Throws(IOException::class)
    fun serverSocket() {
        val selectorManager = SelectorManager(Dispatchers.Default)
        val serverSocket = aSocket(selectorManager)
        assertNotNull(serverSocket)

    }
}