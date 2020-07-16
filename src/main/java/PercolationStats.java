import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final int trials;
    private final int size;
    private final double[] percentegeOfOpening;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0){
            throw new IllegalArgumentException();
        }
        size = n;
        this.trials = trials;
        percentegeOfOpening = new double[this.trials];
        while (trials > 0) {
            trials--;
            double p = (double) simulation()/(n*n);
            percentegeOfOpening[trials] =p ;
        }
    }


    private int simulation() {
        Percolation percolation = new Percolation(size);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(size)+1;
            int col = StdRandom.uniform(size)+1;
            if (!percolation.isOpen(row,col)) {
                percolation.open(row,col);
            }
        }
        return percolation.numberOfOpenSites();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percentegeOfOpening);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percentegeOfOpening);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean()-(CONFIDENCE_95*stddev()/Math.sqrt(trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean()+(CONFIDENCE_95*stddev()/Math.sqrt(trials));
    }


    // test client (see below)
    public static void main(String[] args) {
    }

}
