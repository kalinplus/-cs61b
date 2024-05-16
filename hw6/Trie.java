import java.util.Map;
import java.util.TreeMap;
import edu.princeton.cs.algs4.In;

public class Trie {
    private Node root;
    public class Node {
        private boolean isKey;
        private Map<Character, Node> next;
        private String soFar;

        private Node(boolean k, String sf) {
            isKey = k;
            next = new TreeMap<>();
            soFar = sf;
        }
        public boolean getKey() {
            return isKey;
        }
        public String getSoFar() {return soFar;}
        public Node getNext(Character ch) {
            return next.get(ch);
        }
    }
    public Trie(String fileName) {
        In in = new In(fileName);
        if (!in.exists()) {
            throw new IllegalArgumentException("The dictionary file does not exist");
        }

        root = new Node(false, "");
        while (in.hasNextLine()) {
            Node trav = root;
            String line = in.readLine();
            int len = line.length();

            for (int i = 0; i < len; i += 1) {
                Character ch = line.charAt(i);
                if (!trav.next.containsKey(ch)) {
                    trav.next.put(ch, new Node(i == len - 1,
                              trav.soFar + ch));
                }
                trav = trav.next.get(ch);
            }
        }
    }

    public Node getNext(Character ch) {
        return root.next.get(ch);
    }

    public static void main(String[] args) {
        Trie trie1 = new Trie("trivial_words.txt");
        Trie trie2 = new Trie("words.txt");
        System.out.println("done");
    }
}
