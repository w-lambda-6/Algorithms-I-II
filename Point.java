import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements  Comparable<Point>{
    // make them immutable
    private final int x;
    private final int y;

    private class SlopeComparator implements Comparator<Point>{
        public int compare(Point p1, Point p2){
            Double slope1 = slopeTo(p1);
            Double slope2 = slopeTo(p2);
            return slope1.compareTo(slope2);
        }
    }

    ////////////////////////////////////////////////////////////////////////////

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)){
            return -1;
        } else if (this.x == that.x && this.y == that.y){
            return 0;
        } else {
            return 1;
        }
    }

    public double slopeTo(Point that) {
        if (this.x == that.x){
            if (this.y == that.y){
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }
        if (this.y == that.y){
            return +0.0;
        }
        return (double) (that.y - this.y)/(that.x-this.x);
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    public static void main(String[] args) {}
}
