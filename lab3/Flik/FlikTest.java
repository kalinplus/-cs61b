import static org.junit.Assert.*;
import org.junit.Test;

public class FlikTest {
    @Test
    public void testIsSameNumber() {
        // maybe Interger can only store from -128 to 127
        // will be true
        assertTrue(Flik.isSameNumber(0, 0));
        assertTrue(Flik.isSameNumber(127, 127));
        assertTrue(Flik.isSameNumber(-128, -128));
        // will be false
        assertTrue(!Flik.isSameNumber(-129, -129));
        assertTrue(!Flik.isSameNumber(128, 128));
    }
}

