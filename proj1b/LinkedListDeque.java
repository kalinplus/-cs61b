public class LinkedListDeque<T> implements Deque<T> {
    public class Node {
        T item;
        Node prev;
        Node next;

        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
    private final Node sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.prev == sentinel && sentinel.next == sentinel;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void addFirst(T item) {
        Node firstNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = firstNode;
        sentinel.next = firstNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node lastNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = lastNode;
        sentinel.prev = lastNode;
        size += 1;
    }

    @Override
    public void printDeque() {
        Node tra = sentinel.next;
        while (tra.item != null) {
            System.out.print(tra.item);
            System.out.print(" ");
            tra = tra.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T first = sentinel.next.item;
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.prev = sentinel;
        tmp = null;
        size -= 1;
        return first;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T last = sentinel.prev.item;
        Node tmp = sentinel.prev;
        sentinel.prev = tmp.prev;
        tmp.prev.next = sentinel;
        tmp = null;
        size -= 1;
        return last;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node tra = sentinel.next;
        for (int i = 0; i < index; i++) {
            tra = tra.next;
        }
        return tra.item;
    }

    private T getRecursive(Node head, int index) {
        if (index == 0) {
            return head.item;
        }
        return getRecursive(head.next, index - 1);
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }
}