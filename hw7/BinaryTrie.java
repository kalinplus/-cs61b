import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.lang.Comparable;
import edu.princeton.cs.algs4.MinPQ;

public class BinaryTrie implements Serializable {
    Node root;

    /** Given a frequency table, build a huffman decoding trie, and
     *  low frequency char in 0s, higher in 1s
     *  1. first create each node and push them into pq
     *  2. pop two of them, merge them together, and push to pq again
     *  until there is only 1 node
     *  3. we ignore the only 1 node (root) case
     */
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> hfFactory = new MinPQ<>();
        for (Map.Entry<Character, Integer> entry: frequencyTable.entrySet()) {
            Node node = new Node(entry.getKey(), entry.getValue(), null, null);
            hfFactory.insert(node);
        }
        while (hfFactory.size() > 1) {
            Node zero = hfFactory.delMin();
            Node one = hfFactory.delMin();
            Node merged = new Node('\0', zero.fre + one.fre, zero, one);
            hfFactory.insert(merged);
        }
        root = hfFactory.delMin();
    }

    /**
     *
     * @param querySequence
     * @return Match obj(BitSequence sequence, char symbol)
     *
     * find the longest prefix match for given querySequence
     * For example, for the example Trie given in the introduction,
     * if we call trie.longestPrefixMatch(new BitSequence("0011010001")),
     * then we will get back a Match object containing b as the symbol
     * and 001 as the BitSequence.
     */
    public Match longestPrefixMatch(BitSequence querySequence) {
        String lpfm = new String();
        Node trav = root;
        int i = 0;
        while (true) {
            int bit = querySequence.bitAt(i);
            if (bit == 0) {
                lpfm += "0";
                trav = trav.zero;
            } else {
                lpfm += "1";
                trav = trav.one;
            }
            if (isLeaf(trav)) {
                return new Match(new BitSequence(lpfm), trav.symbol);
            }
        }
    }

    /**
     *
     * @return Map(Character, BitSequence) lookupTable
     * Given a coding trie, what key-value pairs should in it
     *
     * 1. traverse through the binary trie, if we meet the leaf, add it to lookupTable and return
     */
    public Map<Character, BitSequence> buildLookupTable() {
        HashMap<Character, BitSequence> lookupTable = new HashMap<>();
        buildLookupTable(root, "", lookupTable);
        return lookupTable;
    }

    private void buildLookupTable(Node trav, String bits, Map<Character, BitSequence> lookupTable) {
        if (isLeaf(trav)) {
            lookupTable.put(trav.symbol, new BitSequence(bits));
            return;
        }
        buildLookupTable(trav.zero, bits + "0", lookupTable);
        buildLookupTable(trav.one, bits + "1", lookupTable);
    }

    private class Node implements Comparable<Node>, Serializable {
        private char symbol;
        private int fre;
        private Node zero;
        private Node one;
        Node(char symbol, int fre, Node zero, Node one) {
            this.symbol = symbol;
            this.fre = fre;
            this.zero = zero;
            this.one = one;
        }
        public int compareTo(Node that) {
            return this.fre - that.fre;
        }
    }

    private boolean isLeaf(Node n) {
        return n.zero == null && n.one == null;
    }
}
