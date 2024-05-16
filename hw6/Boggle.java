import java.util.*;

import edu.princeton.cs.algs4.MinPQ;
public class Boggle {
    
    // File path of dictionary file
//    static String dictPath = "trivial_words.txt";
    static String dictPath = "words.txt";
    /**
     * Solves a Boggle puzzle.
     *
     * 0. What's more, what important variables do we need in solve()?
     *     (1) char board[][]
     *     (2) MinPQ ks, store k longest found words dynamically
     *     (3) Prefix trie
     * 1. (done) use a tree map to build a trie, fast enough
     * 2. just like start from one tile, and find possible solu to all other tiles,
     *    but repeat MxN times
     * 3. We need to track the Position, the cur node in Prefix, and visited list
     *    for every searching node
     * 4. So we create helper classes Pos and Search
     * 5. init a queue, for every tile
     *   a. enqueue it as entry
     *   b. visit it, which means: dequeue it, add it to mark list, check if soFar is valid word
     *   c. if this search node is not null, we iterate through all adj (adj of cur node, not start pos)
     *      that is not visited push them into queue
     *
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        if (k <= 0) {
            throw new IllegalArgumentException("k is non-positive!");
        }
        In inBoard = new In(boardFilePath);
        if (!inBoard.exists()) {
            throw new IllegalArgumentException("The board file path does not exist");
        }
        // check if input board is rectangular
        String[] allLines = inBoard.readAllLines();
        int height = allLines.length; int width = allLines[0].length();
        for (int i = 1; i < height; i += 1) {
            if (allLines[i].length() != width) {
                throw new IllegalArgumentException("The input board in not rectangular!");
            }
        }
        // init board and visited
        Character[][] board = new Character[height][width];
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                board[i][j] = allLines[i].charAt(j);
            }
        }

        // init MinPQ to store longest k found words
        MinPQ<String> klwpq = new MinPQ<>(new wordComparator());
        Queue<Search> sq = new LinkedList<>();
        // init prefix trie for matching valid words
        Trie prefix = new Trie(dictPath);
        for (int i = 0; i < height; i += 1) {
            for (int j = 0; j < width; j += 1) {
                Trie.Node next = prefix.getNext(board[i][j]);
                sq.add(new Search(next, new Pos(i, j), new ArrayList<>()));
                relax(sq, k, klwpq, height, width, board);
            }
        }

        // get result: k longest strings in list
        return getKLongest(klwpq);
    }

    private static void relax(Queue<Search> sq, int k, MinPQ<String> klwpq, int height, int width, Character[][] board) {
        while (!sq.isEmpty()) {
            Search se = sq.poll();
            if (se.node != null) {
                se.visited.add(se.p);

                if (se.node.getKey()) {
                    insert(se.node.getSoFar(), k, klwpq);
                }

                int r = se.p.r;
                int c = se.p.c;
                for (int x = r - 1; x <= r + 1; x += 1) {
                    for (int y = c - 1; y <= c + 1; y += 1) {
                        if (x >= 0 && x < height && y >= 0 && y < width) {
                            Pos adj = new Pos(x, y);
                            if (!se.visited.contains(adj)) {
                                ArrayList<Pos> adjVisited = new ArrayList<>(se.visited);
                                sq.add(new Search(se.node.getNext(board[x][y]), adj, adjVisited));
                            }
                        }
                    }
                }
            }
        }
    }

    private static void insert(String word, int k, MinPQ<String> klwpq) {
        for (String w : klwpq) {
            if (word.equals(w)) {
                return;
            }
        }
        klwpq.insert(word);
        if (klwpq.size() > k) {
            klwpq.delMin();
        }
    }

    private static List<String> getKLongest(MinPQ<String> klwpq) {
        int size = klwpq.size();
        List<String> tmp = new ArrayList<>(size);
        for (int i = 0; i < size; i += 1) {
            tmp.add(klwpq.delMin());
        }
        List<String> klwls = new ArrayList<>(size);
        for (int i = size - 1; i >= 0; i -= 1) {
            klwls.add(tmp.get(i));
        }
//        printKLongest(klwls);
        return klwls;
    }

    private static void printKLongest(List<String> klw) {
        for (String s : klw) {
            System.out.println(s);
        }
    }

    private static class wordComparator implements Comparator<String>{
        public int compare(String w1, String w2) {
            if (w1.length() != w2.length()) {
                return w1.length() - w2.length();
            } else {
                return -w1.compareTo(w2);
            }
        }
    }

    private static class Pos {
        public int r;
        public int c;
        public Pos(int r, int c) {
            this.r = r;
            this.c = c;
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (this.getClass() != that.getClass()) {
                return false;
            }
            Pos posThat = (Pos) that;
            return this.r == posThat.r && this.c == posThat.c;
        }
    }

    private static class Search {
        public Trie.Node node;
        public Pos p;
        public List<Pos> visited;
        public Search(Trie.Node node, Pos p, List<Pos> visited) {
            this.node = node;
            this.p = p;
            this.visited = visited;
        }
    }

    public static void main(String args[]) {
//        solve(20, "exampleBoard2.txt");
//        solve(7, "exampleBoard.txt");
//        solve(50, "smallBoard.txt");
        solve(50, "smallBoard2.txt");
    }
}
