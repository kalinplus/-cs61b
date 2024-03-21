package lab9;

import com.sun.source.tree.Tree;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node sentinel;
    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        sentinel = new Node(null, null);
        root = null;
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int compare = p.key.compareTo(key);
        V value = null;
        // p.key > key, key should be in the left of p
        if (compare > 0) {
            value = getHelper(key, p.left);
        } else if (compare < 0) {
            value = getHelper(key, p.right);
        } else {
            value = p.value;
        }
        return value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            p = new Node(key, value);
            return p;
        }
        int compare = p.key.compareTo(key);
        // p.key > key, so key should in the left of p
        if (compare > 0) {
            p.left = putHelper(key, value, p.left);
        } else if (compare < 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p = new Node(key, value);
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        size += 1;
        root = putHelper(key, value, root);
        sentinel.left = root;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private void keySetHelper(Node p, Set<K> treeKeySet) {
        if (p != null) {
            treeKeySet.add(p.key);
            keySetHelper(p.left, treeKeySet);
            keySetHelper(p.right, treeKeySet);
        }
    }

    @Override
    public Set<K> keySet() {
        TreeSet<K> treeKeySet = new TreeSet<>();
        keySetHelper(root, treeKeySet);
        return treeKeySet;
    }

    /**
     * 1. To find the left largest node, from the curr, we first go left (what trav initial be)
     * 2. we continuously go right, until we find a node without right child, it should be the left largest node
     *
     * @param curr current node to be swapped, must has 2 children, so trav must exist
     * @param trav left child of current node, traverse to find the left largest node
     *
     * @return key for removing the swapped node (no duplicate node)
     */
    private K swapWithLeftLargest(Node curr, Node trav) {
        // find the left largest, swap !
        if (trav.right == null) {
            Node tmp = new Node(curr.key, curr.value);
            curr.key = trav.key;
            curr.value = trav.value;
            trav.key = tmp.key;
            trav.value = tmp.value;
            return tmp.key;
        } else {
            return swapWithLeftLargest(curr, trav.right);
        }
    }

    /** 1. For removal, there are 4 possiblities for the node to be removed
     *  1.a. If don't existing, removal failts, return null
     *  1.b. If with no child node (it is leaf node), remove directly
     *  1.c. If with one child, concat its child with its parent
     *  1.d. If with two children, swap it with the largest of left child tree, and delete the latter
     */
    private V removeHelper(K key, Node curr, Node prev, Boolean isLeftLean) {
        if (curr == null) {
            return null;
        }
        // you find it !
        int compare = curr.key.compareTo(key);
        if (compare == 0) {
            V value = null;
            // no child
            if (curr.left == null && curr.right == null) {
                value = curr.value;
                if (isLeftLean) {
                    prev.left = null;
                } else {
                    prev.right = null;
                }
                return value;
            // one child
                // how to deal with that we remove the root with one child?
                // we need a left leaning sentinel node !!!
                // but who is root now ?
                // use a left leaning sentinel !
            } else if (curr.left == null && curr.right != null) {
                if (isLeftLean) {
                    prev.left = curr.right;
                } else {
                    prev.right = curr.right;
                }
                value = curr.value;
                curr = curr.right;
                return value;
            } else if (curr.right == null && curr.left != null) {
                if (isLeftLean) {
                    prev.left = curr.left;
                } else {
                    prev.right = curr.left;
                }
                value = curr.value;
                curr = curr.left;
                return value;
            // two children, need to swap
            } else {
                K toRemove = swapWithLeftLargest(curr, curr.left);
                // after swap, we still need to remove "original" curr
                // this swap break bst from the curr, so start with curr.left,
                // where the node to be removed must existing, and this bst is valid
                return removeHelper(toRemove, curr.left, curr, isLeftLean);
            }
        } else {
            // curr.key > key, key should in the left of curr (if existing)
            if (compare > 0) {
                return removeHelper(key, curr.left, curr, true);
            } else {
                return removeHelper(key, curr.right, curr, false);
            }
        }
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        size -= 1;
        return removeHelper(key, root, sentinel, true);
    }

    // almost the same as remove by key
    public V removeHelper(K key, V value, Node curr, Node prev, Boolean isLeftLean) {
        if (curr == null) {
            return null;
        }
        int compare = curr.key.compareTo(key);
        if (compare == 0) {
            if (value == curr.value) {
                V toReturn = null;
                // no child
                if (curr.left == null && curr.right == null) {
                    value = curr.value;
                    if (isLeftLean) {
                        prev.left = null;
                    } else {
                        prev.right = null;
                    }
                    return toReturn;
                    // one child
                    // how to deal with that we remove the root with one child?
                    // we need a left leaning sentinel node !!!
                    // but who is root now ?
                    // use a left leaning sentinel !
                } else if (curr.left == null && curr.right != null) {
                    if (isLeftLean) {
                        prev.left = curr.right;
                    } else {
                        prev.right = curr.right;
                    }
                    toReturn = curr.value;
                    curr = curr.right;
                    return toReturn;
                } else if (curr.right == null && curr.left != null) {
                    if (isLeftLean) {
                        prev.left = curr.left;
                    } else {
                        prev.right = curr.left;
                    }
                    toReturn = curr.value;
                    curr = curr.left;
                    return toReturn;
                    // two children, need to swap
                } else {
                    // we have ensured that the key and value are matched
                    // so it is ok to use the same method to swap
                    K toRemove = swapWithLeftLargest(curr, curr.left);
                    return removeHelper(toRemove, value, curr.left, curr, isLeftLean);
                }
            } else {
                return null;
            }
        } else {
            if (compare > 0) {
                return removeHelper(key, value, curr.left, curr, true);
            } else {
                return removeHelper(key, value, curr.left, curr, false);
            }
        }
    }
    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        size -= 1;
        return removeHelper(key, value, root, sentinel, true);
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTMapIter();
    }

    private class BSTMapIter implements Iterator<K> {
        private Stack<Node> stack;
        private BSTMapIter() {
            stack = new Stack<Node>();
            pushAllLeft(root);
        }

        private void pushAllLeft(Node p) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public K next() {
            Node min = stack.pop();
            pushAllLeft(min.right);
            return min.key;
        }
    }

    @Test
    public void testSwapWithLeftLargest() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("deck", 6);
        bstmap.put("goose", 19);
        // another test
        // bstmap.swapWithLeftLargest(bstmap.root, bstmap.root.left);
        bstmap.swapWithLeftLargest(bstmap.root.left, bstmap.root.left.right);
    }

    @Test
    public void testRemove() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("deck", 6);
        bstmap.put("goose", 19);
        // remove root node, also node with two children
        bstmap.remove("hello");
        // remove node with one child
        bstmap.remove("fish");
        // remove node with no child
        bstmap.remove("zebra");
    }

    @Test
    public void testRemoveKeyValue() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        bstmap.put("deck", 6);
        bstmap.put("goose", 19);
        assertEquals(null, bstmap.remove("deck", 500));
        assertEquals(10, (int)bstmap.remove("cat", 10));
    }
    @Test
    public void testGetAndSize() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        assertEquals(5, (int)bstmap.get("hello"));
        assertEquals(22, (int)bstmap.get("fish"));
        assertEquals(null, bstmap.get("what"));
        assertEquals(4, bstmap.size());
    }
    @Test
    public void testKeySet() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        Set<String> keySet = bstmap.keySet();
    }

    @Test
    public void testIterator() {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        Iterator<String> it = bstmap.iterator();
        for (String s: bstmap) {
            System.out.println(s);
        }
        for (; it.hasNext(); ) {
            String s = it.next();
            System.out.println(s);
        }
    }
    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
    }
}
