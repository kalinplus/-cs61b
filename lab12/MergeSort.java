import edu.princeton.cs.algs4.Queue;

public class MergeSort {
    /**
     * Removes and returns the smallest item that is in q1 or q2.
     *
     * The method assumes that both q1 and q2 are in sorted order, with the smallest item first. At
     * most one of q1 or q2 can be empty (but both cannot be empty).
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      The smallest item that is in q1 or q2.
     */
    private static <Item extends Comparable> Item getMin(
            Queue<Item> q1, Queue<Item> q2) {
        if (q1.isEmpty()) {
            return q2.dequeue();
        } else if (q2.isEmpty()) {
            return q1.dequeue();
        } else {
            // Peek at the minimum item in each queue (which will be at the front, since the
            // queues are sorted) to determine which is smaller.
            Comparable q1Min = q1.peek();
            Comparable q2Min = q2.peek();
            if (q1Min.compareTo(q2Min) <= 0) {
                // Make sure to call dequeue, so that the minimum item gets removed.
                return q1.dequeue();
            } else {
                return q2.dequeue();
            }
        }
    }

    /** Returns a queue of queues that each contain one item from items. */
    private static <Item extends Comparable> Queue<Queue<Item>>
            makeSingleItemQueues(Queue<Item> items) {
        Queue<Queue<Item>> qqs = new Queue<>();
        for (Item i: items) {
            Queue<Item> siq = new Queue<>();
            siq.enqueue(i);
            qqs.enqueue(siq);
        }
        return qqs;
    }

    /**
     * Returns a new queue that contains the items in q1 and q2 in sorted order.
     *
     * This method should take time linear in the total number of items in q1 and q2.  After
     * running this method, q1 and q2 will be empty, and all of their items will be in the
     * returned queue.
     *
     * @param   q1  A Queue in sorted order from least to greatest.
     * @param   q2  A Queue in sorted order from least to greatest.
     * @return      A Queue containing all of the q1 and q2 in sorted order, from least to
     *              greatest.
     *
     */
    private static <Item extends Comparable> Queue<Item> mergeSortedQueues(
            Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> sorted = new Queue<>();
        while (!q1.isEmpty() || !q2.isEmpty()) {
            sorted.enqueue(getMin(q1, q2));
        }
        return sorted;
    }

    /** Returns a Queue that contains the given items sorted from least to greatest.
     *  The given Queue remains no change
     *
     *  1. construct a new Queue sorted, partition Queue into
     *     queue of queues by makeSingleItemQueues()
     *  2. while size of sorted is larger than 1, continuously dequeue two queues,
     *     merge them, and enqueue
     *  3. justification: in 2, because of FIFO, you will always merge small queues
     *     before, and large later. After there is only one, that is what you want
     *
     * @param items A Queue to be sorted.
     * @return merge sorted new Queue
     *  */
    public static <Item extends Comparable> Queue<Item> mergeSort(
            Queue<Item> items) {
        Queue<Queue<Item>> qqs = makeSingleItemQueues(items);
        while (qqs.size() > 1) {
            Queue<Item> q1 = qqs.dequeue();
            Queue<Item> q2 = qqs.dequeue();
            Queue<Item> sorted = mergeSortedQueues(q1, q2);
            qqs.enqueue(sorted);
        }
        return qqs.peek();
    }

    public static void main(String[] args) {
        Queue<Integer> nums = new Queue<>();
        nums.enqueue(3);
        nums.enqueue(2);
        nums.enqueue(4);
        nums.enqueue(1);
        nums.enqueue(5);

        System.out.println(nums.toString());
        System.out.println(MergeSort.mergeSort(nums).toString());
        System.out.println();

        Queue<String> alice = new Queue<>();
        alice.enqueue("Alice");
        alice.enqueue("Travel");
        alice.enqueue("Wonder");
        alice.enqueue("Find");
        alice.enqueue("Kadas");
        alice.enqueue("Turtle");
        alice.enqueue("Soul");
        alice.enqueue("Dark");

        System.out.println(alice.toString());
        System.out.println(MergeSort.mergeSort(alice).toString());
    }
}
