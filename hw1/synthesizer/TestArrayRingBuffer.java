package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;
import java.lang.Integer;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer(10);
        assertTrue(arb.isEmpty());
        arb.enqueue(5);
        arb.enqueue(5);
        arb.enqueue(5);
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
        arb.dequeue();
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
