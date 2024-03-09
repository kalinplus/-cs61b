import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('a', 'b'));
        assertTrue(offByOne.equalChars('b', 'a'));
        assertTrue(offByOne.equalChars('&', '%'));
        assertTrue(offByOne.equalChars('A', 'B'));

        assertFalse(offByOne.equalChars('a', 'a'));
        assertFalse(offByOne.equalChars('a', 'u'));
        assertFalse(offByOne.equalChars('1', 'u'));
        assertFalse(offByOne.equalChars('!', 'u'));
        assertFalse(offByOne.equalChars('A', 'I'));
    }
}
