package hw2;

import java.lang.*;
public class PercolationStats {
    private double confidenceConst = 1.96;
    private double[] xi;
    private int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IndexOutOfBoundsException();
        }
        this.T = T;
        double[] xi = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            int openSites = 0;
            while(!p.percolates()) {
                int x, y;
                do {
                    x = edu.princeton.cs.introcs.StdRandom.uniform(N);
                    y = edu.princeton.cs.introcs.StdRandom.uniform(N);
                } while (!p.isOpen(x, y));

                p.open(x, y);
                openSites += 1;
            }
            xi[i] = (double)(openSites) / (N * N);
        }
    }
    public double mean() {
        return edu.princeton.cs.introcs.StdStats.mean(xi);
    }
    public double stddev() {
        return edu.princeton.cs.introcs.StdStats.stddev(xi);
    }
    public double confidenceLow() {
        return mean() - (confidenceConst * stddev()) / Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean() + (confidenceConst * stddev()) / Math.sqrt(T);
    }
}
