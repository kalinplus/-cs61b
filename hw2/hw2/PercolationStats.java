package hw2;

import java.lang.*;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double confidenceConst = 1.96;
    private double[] xi;
    private int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IndexOutOfBoundsException("N and T should be greater than 0");
        }

        this.T = T;
        this.xi = new double[T];

        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            while(!p.percolates()) {
                int x, y;
                do {
                    x = StdRandom.uniform(N);
                    y = StdRandom.uniform(N);
                } while (p.isOpen(x, y));
                p.open(x, y);
            }
            xi[i] = (double)(p.numberOfOpenSites()) / (N * N);
        }
    }
    public double mean() {
        return StdStats.mean(xi);
    }
    public double stddev() {
        return StdStats.stddev(xi);
    }
    public double confidenceLow() {
        return mean() - (confidenceConst * stddev()) / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + (confidenceConst * stddev()) / Math.sqrt(T);
    }
}
