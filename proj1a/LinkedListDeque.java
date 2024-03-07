public class LinkedListDeque<T> {
   public class Node {
       T item;
       Node prev;
       Node next;

       public Node(T i, Node p, Node n) {
           item = i;
           prev = p;
           next = n;
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

    public boolean isEmpty() {
        return sentinel.prev == sentinel && sentinel.next == sentinel;
    }

    public int size() {
        return size;
    }

    public void addFirst(T item) {
        Node firstNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = firstNode;
        sentinel.next = firstNode;
        size += 1;
    }

    public void addLast(T item) {
        Node lastNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = lastNode;
        sentinel.prev = lastNode;
        size += 1;
    }

    public void printDeque() {
        Node tra = sentinel.next;
        while (tra.item != null) {
            System.out.print(tra.item);
            System.out.print(" ");
            tra = tra.next;
        }
        System.out.println();
    }

    public T removeFirst() {
        T first = sentinel.next.item;
        Node tmp = sentinel.next;
        sentinel.next = tmp.next;
        tmp.next.prev = sentinel;
        tmp = null;
        size -= 1;
        return first;
    }

    public T removeLast() {
        T last = sentinel.prev.item;
        Node tmp = sentinel.prev;
        sentinel.prev = tmp.prev;
        tmp.prev.next = sentinel;
        tmp = null;
        size -= 1;
        return last;
    }

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

}
