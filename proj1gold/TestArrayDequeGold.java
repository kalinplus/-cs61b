import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testTask1() {
         StudentArrayDeque<Integer> bad1 = new StudentArrayDeque<Integer>();
         ArrayDequeSolution<Integer> good1 = new ArrayDequeSolution<Integer>();
         String errorMsg = new String();
         errorMsg += "\n";

         for (int i = 0; i < 100; i += 1) {
             double numBetZeroAndOne = StdRandom.uniform();

             if (numBetZeroAndOne < 0.25) {
                 bad1.addFirst(i);
                 good1.addFirst(i);
                 bad1.printDeque();
                 good1.printDeque();
                 errorMsg += "addFirst(" + i + ")\n";
             } else if (numBetZeroAndOne < 0.5) {
                 bad1.addLast(i);
                 good1.addLast(i);
                 errorMsg += "addLast(" + i + ")\n";
             } else if (numBetZeroAndOne < 0.75) {
                 if (!bad1.isEmpty()) {
                     Integer x = bad1.removeFirst();
                     Integer y = good1.removeFirst();
                     errorMsg += "removeFirst()\n";
                     assertEquals(errorMsg, y, x);
                 }
             } else {
                 if (!bad1.isEmpty()) {
                     Integer x = bad1.removeLast();
                     Integer y = good1.removeLast();
                     errorMsg += "removeLast()\n";
                     assertEquals(errorMsg, y, x);
                 }
             }
         }
    }
}
