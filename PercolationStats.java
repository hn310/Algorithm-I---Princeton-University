import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/*---------------------------------------------------------
 *  Author:        Nam.NVH
 *  Written:       29/01/2016
 *  Briefly describe your program
 *---------------------------------------------------------*/
public class PercolationStats {
    private int T;
    private double[] storeResults;

    /* perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T) {
        // throw a java.lang.IllegalArgumentException if either N ≤ 0 or T ≤ 0
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.T = T;
        storeResults = new double[T];
        int k = 0;

        while (T > 0) {
            Percolation perc = new Percolation(N);
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, N + 1);
                int j = StdRandom.uniform(1, N + 1);
                perc.open(i, j);
            }
            int opened = 0;
            for (int row = 1; row <= N; row++) {
                for (int col = 1; col <= N; col++) {
                    if (perc.isFull(row, col)) {
                        opened++;
                    } else if (perc.isOpen(row, col)) {
                        opened++;
                    }
                }
            }
            storeResults[k] = (double) opened / (N * N);
            k++;
            T--;
        }
    }

    /* sample mean of percolation threshold */
    public double mean() {
        return (StdStats.mean(storeResults));
    }

    /* sample standard deviation of percolation threshold */
    public double stddev() {
        return (StdStats.stddev(storeResults));
    }

    /* low endpoint of 95% confidence interval */
    public double confidenceLo() {
        double x = StdStats.mean(storeResults) - 1.96
                * StdStats.stddev(storeResults) / Math.sqrt(this.T);
        return x;
    }

    /* high endpoint of 95% confidence interval */
    public double confidenceHi() {
        double x = StdStats.mean(storeResults) + 1.96
                * StdStats.stddev(storeResults) / Math.sqrt(this.T);
        return x;
    }

    public static void main(String[] args) {
        int N = 2; // NxN grid
        int T = 100000; // number of trials

        PercolationStats percStats = new PercolationStats(N, T);
        // for (int i = 0; i < percStats.storeResults.length; i++) {
        // System.out.println(percStats.storeResults[i]);
        // }

        System.out.println(percStats.mean());
        System.out.println(percStats.stddev());
        System.out.println("95% confidence interval: " + percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}