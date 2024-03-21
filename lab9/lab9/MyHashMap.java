package lab9;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }


    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        return buckets[index].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null || value == null) {
            return;
        }
        int index = hash(key);
        buckets[index].put(key, value);
        size += 1;
        if (loadFactor() > MAX_LF) {
            resize(2 * buckets.length);
        }
    }

    /**
     * 1. iterate through each bucket
     * 2. for each bucket, iterate through all the entries
     * 3. get the value by key (iterator), and put key-value pair to new buckets
     * @param capacity new capacity of new buckets
     */
    private void resize(int capacity) {
        MyHashMap<K, V> newBuckets = new MyHashMap<>();
        newBuckets.buckets = new ArrayMap[capacity];
        newBuckets.clear();
        for (int i = 0; i < buckets.length; i += 1) {
            for (K key: buckets[i]) {
                V value = buckets[i].get(key);
                newBuckets.put(key, value);
            }
        }
        buckets = newBuckets.buckets;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (int i = 0; i < buckets.length; i += 1) {
            Set<K> keysetOfBucket = buckets[i].keySet();
            keyset.addAll(keysetOfBucket);
        }
        return keyset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            return null;
        }
        int index = hash(key);
        V value = buckets[index].remove(key);
        if (value != null) {
            size -= 1;
        }
        return value;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null || value == null) {
            return null;
        }
        int index = hash(key);
        V returnedValue = buckets[index].get(key);
        if (returnedValue == null || returnedValue != value) {
            return null;
            // this pair must exist
        } else {
            size -= 1;
            return remove(key);
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    @Test
    public void testPutAndGet() {
        MyHashMap<String, Integer> hashMap = new MyHashMap<>();
        hashMap.put("hello", 5);
        hashMap.put("cat", 3);
        hashMap.put("zebra", 10);
        hashMap.put("fish", 4);

        assertEquals(5, (int)hashMap.get("hello"));
        assertEquals(10, (int)hashMap.get("zebra"));
        assertEquals(null, hashMap.get("what"));
    }

    @Test
    public void testPutWithResize() {
        MyHashMap<Integer, Integer> myHashMap = new MyHashMap<>();
        int limit = 160;
        for (int i = 0; i < limit; i += 1) {
            myHashMap.put(i, i);
        }
        System.out.println();
    }

    @Test
    public void testKeyset() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("hello", 5);
        myHashMap.put("cat", 3);
        myHashMap.put("zebra", 10);
        myHashMap.put("fish", 4);
        Set<String> keyset = myHashMap.keySet();
        for (String s: keyset) {
            System.out.println(s);
        }
    }

    @Test
    public void testRemove() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("hello", 5);
        myHashMap.put("cat", 3);
        myHashMap.put("zebra", 10);
        myHashMap.put("fish", 4);
        myHashMap.remove("fish");
        Set<String> keyset = myHashMap.keySet();
        for (String s: keyset) {
            System.out.println(s);
        }
        assertEquals(null, myHashMap.remove("null"));
        myHashMap.remove("cat");
        myHashMap.remove("zebra");
        myHashMap.remove("hello");
        assertEquals(0, myHashMap.size());
    }

    @Test
    public void testRemoveKeyValue() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("hello", 5);
        myHashMap.put("cat", 3);
        myHashMap.put("zebra", 10);
        myHashMap.put("fish", 4);
        assertEquals(null, myHashMap.remove(null, null));
        assertEquals(null, myHashMap.remove("null", 1));
        assertEquals(null, myHashMap.remove("hello", 666));
        assertEquals(3, (int)myHashMap.remove("cat", 3));
    }

    @Test
    public void testIterator() {
        MyHashMap<String, Integer> myHashMap = new MyHashMap<>();
        myHashMap.put("hello", 5);
        myHashMap.put("cat", 3);
        myHashMap.put("zebra", 10);
        myHashMap.put("fish", 4);
        for (String s: myHashMap) {
            System.out.println(s);
        }
    }
}
