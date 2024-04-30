import static org.junit.Assert.*;
import org.junit.Test;
public class RadixSortTester {
    private static String[] someStrings = {"alice", "wait", "bob", "weak", "padding", "merge"};

    @Test
    public void testLSDRadixSort() {
        String[] tmp = RadixSort.sort(someStrings);
        for (String s: tmp) {
            System.out.println(s);
        }
    }
}
