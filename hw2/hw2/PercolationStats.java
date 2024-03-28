package hw2;

import java.lang.*;
public class PercolationStats {
    private double confidenceConst = 1.96;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IndexOutOfBoundsException();
        }
        double[] xi = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation p = pf.make(N);
            int openSites = 0;
            while(!p.percolates()) {
                int randomx = edu.princeton.cs.introcs.StdRandom.uniform(N);
                int randomy = edu.princeton.cs.introcs.StdRandom.uniform(N);
                p.open(randomx, randomy);
                openSites += 1;
            }
            xi[i] = (double)(openSites) / (N * N);
        }

        mean(xi);
        stddev(xi);
        confidenceLow(mean(xi), stddev(xi), T);
        confidenceHigh(mean(xi), stddev(xi), T);
    }
    public double mean(double[] xi) {
        return edu.princeton.cs.introcs.StdStats.mean(xi);
    }
    public double stddev(double[] xi) {
        return edu.princeton.cs.introcs.StdStats.stddev(xi);
    }
    public double confidenceLow(double miu, double sigma, int T) {
        return miu - (confidenceConst * sigma) / Math.sqrt(T);
    }
    public double confidenceHigh(double miu, double sigma, int T) {
        return miu + (confidenceConst * sigma) / Math.sqrt(T);
    }
}
