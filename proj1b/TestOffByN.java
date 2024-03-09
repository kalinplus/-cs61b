import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    @Test
    public void testEqualChars() {
        OffByN offBy3 = new OffByN(3);
        assertTrue(offBy3.equalChars('1', '4'));
        assertTrue(offBy3.equalChars('a', 'd'));
        assertTrue(offBy3.equalChars('d', 'a'));
        assertTrue(offBy3.equalChars('A', 'D'));

        assertFalse(offBy3.equalChars('1', '9'));
        assertFalse(offBy3.equalChars('a', 'n'));
        assertFalse(offBy3.equalChars('%', 'N'));
    }
}
