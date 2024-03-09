import edu.princeton.cs.algs4.CC;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));

        assertTrue(palindrome.isPalindrome("cac"));
        assertTrue(palindrome.isPalindrome("caac"));
        assertFalse(palindrome.isPalindrome("cat"));
        assertFalse(palindrome.isPalindrome("cata"));

        assertTrue(palindrome.isPalindrome("abcdefg gfedcba"));
        assertFalse(palindrome.isPalindrome("hello, world !!!"));
    }

    @Test
    public void testOffByOneAndNPalindrome() {
        CharacterComparator cc = new OffByOne();

        assertTrue(palindrome.isPalindrome("", cc));
        assertTrue(palindrome.isPalindrome("6", cc));

        assertTrue(palindrome.isPalindrome("ad6cb", cc));
        assertTrue(palindrome.isPalindrome("adcb", cc));
        assertTrue(palindrome.isPalindrome("&d6c%", cc));
        assertTrue(palindrome.isPalindrome("&B6A%", cc));

        assertFalse(palindrome.isPalindrome("hello", cc));
        assertFalse(palindrome.isPalindrome("hell", cc));
        assertFalse(palindrome.isPalindrome("!@#$%^&$#14235", cc));
        assertFalse(palindrome.isPalindrome("ABC14BB", cc));

        CharacterComparator ccc = new OffByN(5);
        assertTrue(palindrome.isPalindrome("", ccc));
        assertTrue(palindrome.isPalindrome("1", ccc));
        assertTrue(palindrome.isPalindrome("af", ccc));
        assertTrue(palindrome.isPalindrome("AF", ccc));
        assertTrue(palindrome.isPalindrome("16", ccc));

        assertFalse(palindrome.isPalindrome("18", ccc));
        assertFalse(palindrome.isPalindrome("afjklagjasldglcbio", ccc));
    }
}
