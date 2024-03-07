public class ArrayDeque<T> {
    private T[] array;
    private int front;
    private int curSize;
    private int size;

    public ArrayDeque() {
        array = (T []) new Object[8];
        front = 0;
        curSize = 0;
        size = 8;
    }

    public boolean isEmpty() {
        return curSize == 0;
    }

    public int size() {
        return curSize;
    }

    public T get(int index) {
        if (index >= curSize) {
            return null;
        }
        return array[(front + index) % size];
    }

    public void printDeque() {
        for (int i = 0; i < curSize; i++) {
            System.out.print(array[(front + i) % size] + " ");
        }
        System.out.println();
    }

    private void resize(double rate) {
        int newSize = (int)(size * rate);
        T[] newArray = (T []) new Object[newSize];
        for (int i = 0; i < curSize; i++) {
            newArray[i] = array[(front + i) % size];
        }
        front = 0;
        size = newSize;
        array = newArray;
    }
    private void expand() {
        resize(2.0);
    }
    private void shrink() {
        while (size >= 16 && (double)(curSize / size) <= 0.25) {
            resize(0.5);
        }
    }

    private int getLastPos() {
        return (front + curSize - 1) % size;
    }

    public void addFirst(T item) {
        if (curSize == size) {
            expand();
        }
        front = (front - 1 < 0) ? (front - 1 + size) : (front - 1);
        array[front] = item;
        curSize += 1;
    }

    public void addLast(T item) {
        if (curSize == size) {
            expand();
        }
        int last = (getLastPos() + 1) % size;
        array[last] = item;
        curSize += 1;
    }

    public T removeFirst() {
        shrink();
        if (isEmpty()) {
            return null;
        }
        T first = array[front];
        array[front] = null;
        front = (front + 1) % size;
        curSize -= 1;
        return first;
    }

    public T removeLast() {
        shrink();
        if (isEmpty()) {
            return null;
        }
        T last = array[getLastPos()];
        array[getLastPos()] = null;
        curSize -= 1;
        return last;
    }
}
