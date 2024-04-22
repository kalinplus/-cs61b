import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Quick;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        for (Item i: unsorted) {
            // pivot is larger than i
            if (pivot.compareTo(i) > 0) {
                less.enqueue(i);
            } else if (pivot.compareTo(i) == 0) {
                equal.enqueue(i);
            } else {
                greater.enqueue(i);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest.
     *
     *  1. return if size of para < 1
     *  2. create less, equal, and greater, and partition() them
     *  3. recursively quickSort less and greater
     *  4. concat less, equal, and greater
     * */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        if (items.size() <= 1) {
            return items;
        }
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();

        partition(items, getRandomItem(items), less, equal, greater);
        less = quickSort(less);
        greater = quickSort(greater);

        Queue<Item> sorted = new Queue<>();
        sorted = QuickSort.catenate(less, equal);
        sorted = QuickSort.catenate(sorted, greater);
        return sorted;
    }
    public static void main(String[] args) {
        Queue<Integer> nums = new Queue<>();
        nums.enqueue(3);
        nums.enqueue(2);
        nums.enqueue(4);
        nums.enqueue(1);
        nums.enqueue(5);

        System.out.println(nums.toString());
        System.out.println(QuickSort.quickSort(nums).toString());
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
        System.out.println(QuickSort.quickSort(alice).toString());
    }
}
