package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 *  1. I use boolean[][] to track open, and WQU to check full
 *  2. But use only 1 WQU resulting in backwash, which is due to indirect link.
 *     So we use 2 WQUs
 *    a. 1 with just top site, to check if one site is full
 *    b. 1 with top and bottom sites, to check if is prelocated
 *  3. Some other attributes include topSite, bottomSite and nubmerOfOpenSites
 */
public class Percolation {
    private boolean[][] isSitesOpen;
    private WeightedQuickUnionUF fullUf;
    private WeightedQuickUnionUF percoUf;
    private int topSite;
    private int botSite;
    private int numOfOpenSite;
    private int size;

    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();

        topSite = N * N;
        botSite = N * N + 1;
        numOfOpenSite = 0;
        size = N;

        isSitesOpen = new boolean[N][N];
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                isSitesOpen[i][j] = false;
            }
        }

        fullUf = new WeightedQuickUnionUF(N * N + 1);
        percoUf = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i += 1) {
            fullUf.union(topSite, i);
            percoUf.union(topSite, i);
            percoUf.union(botSite, N * N - i - 1);
        }
    }
    public void open(int row, int col) {
        boundCheck(row, col);
        // handle open
        if (isSitesOpen[row][col])
            return;

        isSitesOpen[row][col] = true;
        numOfOpenSite += 1;
        // deal with union (full)
        if (row > 0)
            unionForOpenSite(row, col, row - 1, col);
        if (row < size - 1)
            unionForOpenSite(row, col, row + 1, col);
        if (col > 0)
            unionForOpenSite(row, col, row, col - 1);
        if (col < size -1)
            unionForOpenSite(row, col, row, col + 1);
    }

    private void unionForOpenSite(int row, int col, int urow, int ucol) {
        if (isSitesOpen[urow][ucol]) {
            fullUf.union(rcToIdx(row, col), rcToIdx(urow, ucol));
            percoUf.union(rcToIdx(row, col), rcToIdx(urow, ucol));
        }
    }

    public boolean isOpen(int row, int col) {
        boundCheck(row, col);
        return isSitesOpen[row][col];
    }
    public boolean isFull(int row, int col) {
        boundCheck(row, col);
        return isSitesOpen[row][col] && fullUf.connected(rcToIdx(row, col), topSite);
    }
    public int numberOfOpenSites() {
        return numOfOpenSite;
    }

    public boolean percolates() {
        // can't be percolated before open
        if (size == 1) {
            return isSitesOpen[0][0] && percoUf.connected(topSite, botSite);
        }
        return percoUf.connected(topSite, botSite);
    }

    public static void main(String[] args) {
    }

    private void boundCheck(int row, int col) {
        if (row < 0 || row >= size || col < 0 || col >= size) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int rcToIdx(int row, int col) {
        return row * size + col;
    }
}
