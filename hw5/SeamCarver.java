import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.MinPQ;

import java.awt.Color;
import java.lang.IndexOutOfBoundsException;
import java.lang.IllegalArgumentException;
import java.lang.Comparable;


/**
 *  1. first consider remove vertical seam
 *  2. we should calculate the energy of the whole picture
 *    a. consider calc energy of point x, y in p
 *    b. use the formula shown in material, only to remind the round, use (n + size) % size
 *  3. find the seam with iteration
 *    a. create a sentinel top src, and set the pathWeightTo of top row nodes and edgeTo
 *    b. run dijkstra like algorithm from src, until we reach the bottom row
 *    c. prepare a distTo[][], an edgeTo[][] and a pq
 *    d. after founded, backtracking to get seam
 *  4. remove the seam, just call the api
 *  5. always notice that x is column, y is row, from upper left to lower right
 *  6. for height, first transpose the pic, then run find seam in width
 */
public class SeamCarver {
    private Picture pic;

    public SeamCarver(Picture picture) {
        pic = picture;
    }
    public Picture picture() {
        return pic;
    }
    public int width() {
        return pic.width();
    }
    public int height() {
        return pic.height();
    }

    // tested with several different pictures
    public double energy(int x, int y) {
        validateXY(x, y);
        Color x1 = pic.get(effX(x + 1), y);
        Color x2 = pic.get(effX(x - 1), y);
        Color y1 = pic.get(x, effY(y + 1));
        Color y2 = pic.get(x, effY(y - 1));
        double energyX = oneDirEnergy(x1, x2);
        double energyY = oneDirEnergy(y1, y2);
        return energyX + energyY;
    }
    private double oneDirEnergy(Color a, Color b) {
        int dr = a.getRed() - b.getRed();
        int db = a.getBlue() - b.getBlue();
        int dg = a.getGreen() - b.getGreen();
        return dr * dr + db * db + dg * dg;
    }

    // use transpose to avoid repetition
    public int[] findHorizontalSeam() {
        int h = height();
        int w = width();
        Picture transPic = new Picture(h, w);
        for (int r = 0; r < h; r += 1) {
            for (int c = 0; c < w; c += 1) {
                transPic.setRGB(r, c, pic.getRGB(c, r));
            }
        }
        Picture tmp = this.pic;
        this.pic = transPic;
        int[] hseam = findVerticalSeam();
        this.pic = tmp;
        return hseam;
    }

    private class Pos implements Comparable<Pos>{
        private int c;
        private int r;
        private double pw;
        Pos(int c, int r, double pw) {
            this.c = c;
            this.r = r;
            this.pw = pw;
        }
        @Override
        public int compareTo(Pos o) {
            return Double.compare(this.pw, o.pw);
        }
    }

    // thoroughly tested, from small to large, and edge single line, large picture
    // picture with all same energy cases. All right so far
    public int[] findVerticalSeam() {
        int w = width(); int h = height();
        // h is row, w is column
        int[][] pathWeightTo = new int[h][w];
        Pos[][] edgeTo = new Pos[h][w];
        MinPQ<Pos> pq = new MinPQ<>();

        for (int c = 0; c < w; c += 1) {
            double e = energy(c, 0);
            pathWeightTo[0][c] = (int)e;
            Pos p = new Pos(c, 0, e);
            edgeTo[0][c] = p;
            pq.insert(p);
        }
        for (int r = 1; r < h; r += 1) {
            for (int c = 0; c < w; c += 1) {
                pathWeightTo[r][c] = Integer.MAX_VALUE;
            }
        }

        while (!pq.isEmpty()) {
            Pos p = pq.delMin();
            int r = p.r;
            if (r == h - 1)
                return getSeam(p, edgeTo);
            int c = p.c;
            relax(pq, c - 1, r + 1, p, pathWeightTo, edgeTo);
            relax(pq, c, r + 1, p, pathWeightTo, edgeTo);
            relax(pq, c + 1, r + 1, p, pathWeightTo, edgeTo);
        }
        return null;
    }
    private void relax(MinPQ<Pos> pq, int c, int r, Pos p, int[][] pathWeightTo, Pos[][] edgeTo) {
        if (inBound(c, r)) {
            int e = (int)energy(c, r);
            if (p.pw + e < pathWeightTo[r][c]) {
                pathWeightTo[r][c] = (int)p.pw + e;
                edgeTo[r][c] = p;
                pq.insert(new Pos(c, r, (int)p.pw + e));
            }
        }
    }

    private int[] getSeam(Pos p, Pos[][] edgeTo) {
        int[] seam = new int[height()];
        int r = p.r;
        int c = p.c;
        int idx = height() - 1;
        do {
            seam[idx] = c;
            Pos last = edgeTo[r][c];
            r = last.r; c = last.c;
            idx -= 1;
        } while (idx >= 0);
        return seam;
    }
    public void removeHorizontalSeam(int[] seam) {
        validateSeam(seam, width());
        SeamRemover.removeHorizontalSeam(pic, seam);
    }
    public void removeVerticalSeam(int[] seam) {
        validateSeam(seam, height());
        SeamRemover.removeVerticalSeam(pic, seam);
    }

    private int effX(int x) {
        return (x + width()) % width();
    }

    private int effY(int y) {
        return (y + height()) % height();
    }

    private void validateXY(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height()) {
            throw new IndexOutOfBoundsException(x + " or " + y + " out of boundary");
        }
    }

    private boolean inBound(int c, int r) {
        return c >= 0 && c < width() && r >= 0 && r < height();
    }

    // todo
    private void validateSeam(int[] seam, int WH) {
        int sl = seam.length;
        if (sl != WH) {
            throw new IllegalArgumentException("Invalid seam length " + sl + ", should be " + WH);
        }
        for (int i = 0; i < sl - 1; i += 1) {
            if (Math.abs(seam[i] - seam[i + 1]) > 1) {
                throw new IllegalArgumentException("Two consecutive entries " + seam[i] +
                        " and " + seam[i + 1] + " differ by more than 1");
            }
        }
    }
}
