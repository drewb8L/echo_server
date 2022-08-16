import com.babcock.Main
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test{
    @Test
    fun testMain(){
        val main = Main()
        assertEquals(main.mainTest(), 1)
    }
}