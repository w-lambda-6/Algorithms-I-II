import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] threshold;
    private final int T;

    public PercolationStats(int n, int trials){
        if (n < 1 || trials < 1){
            throw new IllegalArgumentException();
        }
        double area = n*n;
        T = trials;
        threshold = new double[T];
        for (int i = 0; i < T; i++){
            Percolation p = new Percolation(n);
            while (!p.percolates()){
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;
                p.open(row, col);
            }
            threshold[i] = p.numberOfOpenSites()/area;
        }
    }

    public double mean(){
        return StdStats.mean(threshold);
    }

    public double stddev(){
        return StdStats.stddev(threshold);
    }

    public double confidenceLo(){
        double pBar = mean();
        double s = stddev();
        return pBar - 1.96 * s/Math.sqrt(T);
    }

    public double confidenceHi(){
        double pBar = mean();
        double s = stddev();
        return pBar + 1.96 * s/Math.sqrt(T);
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java-algs4 PercolationStats n T");
            return;
        }
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats percStats = new PercolationStats(n, T);
        System.out.println("mean = " + percStats.mean());
        System.out.println("standard deviation = " + percStats.stddev());
        System.out.println("95% confidence interval = [" + percStats.confidenceLo()
                + ", " + percStats.confidenceHi() + "]");
    }
}
