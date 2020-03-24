import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final int sideLen;
    private final int trialNum;
    private final double[] thresholds;
    private final double mean;
    private final double standardDev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Negative numbers in arguments");
        }
        sideLen = n;
        trialNum = trials;

        thresholds = new double[trials];
        for (int i = 0; i < trials; ++i) {
            Percolation iGrid = new Percolation(n);
            thresholds[i] = getThreshold(iGrid);
        }

        mean = StdStats.mean(thresholds);
        standardDev = devCalc(thresholds);
    }

    private double devCalc(double[] arr) {
        if (trialNum == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }


    private double getThreshold(Percolation grid) {
        int randRow;
        int randCol;

        while (!grid.percolates()) {
            randRow = StdRandom.uniform(sideLen);
            randCol = StdRandom.uniform(sideLen);
            ++randRow;
            ++randCol;
            if (!grid.isOpen(randRow, randCol))
                grid.open(randRow, randCol);
        }

        return (double) grid.numberOfOpenSites() /
                // -----------------------------
                        (sideLen * sideLen);
    }


    // sample mean of percolation threshold
    public double mean() {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return standardDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - (1.96 * standardDev)) /
                // ---------------------------
                     Math.sqrt(trialNum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + (1.96 * standardDev)) /
                // ---------------------------
                    Math.sqrt(trialNum);
    }


    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats test = new PercolationStats(n, trials);
        System.out.print("mean                        = ");
        System.out.print(test.mean());
        System.out.println();

        System.out.print("stddev                      = ");
        System.out.print(test.stddev());
        System.out.println();

        System.out.print("95% confidence interval     = [");
        System.out.print(test.confidenceLo());
        System.out.print(", ");
        System.out.print(test.confidenceHi());
        System.out.println("]");
    }
}