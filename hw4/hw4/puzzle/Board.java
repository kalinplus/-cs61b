package hw4.puzzle;
import java.lang.IndexOutOfBoundsException;
import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState{

    private final int[][] board;
    private final int size;
    private final int BLANK = 0;

    public Board(int[][] tiles) {
        board = new int[tiles.length][tiles.length];
        size = tiles.length;
        for (int i = 0; i < size; i++) {
            System.arraycopy(tiles[i], 0, this.board[i], 0, size);
        }
    }

    public int tileAt(int i, int j) {
        if (!inBound(i, j)) {
            throw new IndexOutOfBoundsException("i, j out of index of board!");
        }
        return board[i][j];
    }

    public int size() {
        return size;
    }

    /**
     * 1. what does the neighbor state means? Well, it means that the
     *    board state that produced by 1 movement from the current state
     * 2. to generate neighbors, we need to find the blank board first
     *    (only one in 8-puzzle)
     * 3. find all exchangeable tiles for the blank tile
     * 4. put the board stated that has been exchanged to return iterable
     *
     * @return the neighbors of current board state
     */
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
//       Queue<WorldState> it = new Queue<>();
//       int blankX = 0;
//       int blankY = 0;
//       for (int i = 0; i < size(); i += 1) {
//           for (int j = 0; j < size(); j += 1) {
//               if (tileAt(i, j) == BLANK) {
//                   blankX = i;
//                   blankY = j;
//               }
//           }
//       }
//       int[][] tiles = new int[size()][size()];
//       for (int i = 0; i < size(); i += 1) {
//           for (int j = 0; j < size(); j += 1) {
//               tiles[i][j] = board[i][j];
//           }
//       }
//       if (inBound(blankX - 1, blankY)) {
//           swapTile(blankX, blankY, blankX - 1, blankY, tiles);
//           it.enqueue(new Board(tiles));
//           swapTile(blankX, blankY, blankX - 1, blankY, tiles);
//       }
//       if (inBound(blankX + 1, blankY)) {
//           swapTile(blankX, blankY, blankX + 1, blankY, tiles);
//           it.enqueue(new Board(tiles));
//           swapTile(blankX, blankY, blankX + 1, blankY, tiles);
//       }
//       if (inBound(blankX, blankY - 1)) {
//           swapTile(blankX, blankY, blankX, blankY - 1, tiles);
//           it.enqueue(new Board(tiles));
//           swapTile(blankX, blankY, blankX, blankY - 1, tiles);
//       }
//       if (inBound(blankX, blankY + 1)) {
//           swapTile(blankX, blankY, blankX, blankY + 1, tiles);
//           it.enqueue(new Board(tiles));
//           swapTile(blankX, blankY, blankX, blankY + 1, tiles);
//       }
//       return it;
    }

    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (board[i][j] != BLANK) {
                    if (board[i][j] != i * size + j + 1)
                        hamming += 1;
                }
            }
        }
        return hamming;
    }

    // we need rxyTo1d first
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
//                if (board[i][j] != BLANK) {
//                    Pos p = rxyTo1d(board[i][j]);
//                    manhattan += Math.abs(i - p.x);
//                    manhattan += Math.abs(j - p.y);
//                }
                if (board[i][j] == 0) {
                    continue;
                }
                if (board[i][j] != i * size + (j + 1)) {
                    int iGoal = (board[i][j] - 1) / size;
                    int jGoal = board[i][j] - iGoal * size - 1;
                    manhattan += (int) Math.abs(j - jGoal) + (int) Math.abs(i - iGoal);
                }
            }
        }
        return manhattan;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.size() != that.size()) {
            return false;
        }
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    private boolean inBound(int i, int j) {
        return (i >= 0 && i < size && j >= 0 && j < size);
    }

    private void swapTile(int x1, int y1, int x2, int y2, int[][] tiles) {
        if (!inBound(x1, y1) || !inBound(x2, y2)) {
            throw new IndexOutOfBoundsException();
        }
        int tmp = tiles[x1][y1];
        tiles[x1][y1] = tiles[x2][y2];
        tiles[x2][y2] = tmp;
    }

    private class Pos {
        public int x;
        public int y;
        Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private Pos rxyTo1d(int n) {
        int x = (n - 1) / size();
        int y = n - x * size() - 1;
        return new Pos(x, y);
    }

    public int hashCode() {
        return super.hashCode();
    }
}
