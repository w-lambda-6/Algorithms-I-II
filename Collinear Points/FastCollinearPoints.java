import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<>();

    // gets the point that is the lowest in an array from index lo to hi
    private Point min(Point[] a, int lo, int hi){
        if (a == null || lo > hi){
            throw new IllegalArgumentException();
        }
        Point res = a[lo];
        for (int i = lo +1; i <= hi; i++){
            if (res.compareTo(a[i])>0){
                res = a[i];
            }
        }
        return res;
    }

    // similar to the above
    private Point max(Point[] a, int lo, int hi){
        if (a == null || lo > hi){
            throw new IllegalArgumentException();
        }
        Point res = a[lo];
        for (int i = lo +1; i <= hi; i++){
            if (res.compareTo(a[i])<0){
                res = a[i];
            }
        }
        return res;
    }

    ////////////////////////////////////////////////////////////////////////////

    public FastCollinearPoints(Point[] points){
        // illegal argument corner case checking
        // no empty arguments allowed
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (Point p:points){
            if (p == null){
                throw new IllegalArgumentException();
            }
        }
        // no repeated points allowed
        int len = points.length;
        for (int i = 0; i < len; i++){
            for (int j = i + 1; j < len; j++){
                if (points[i].compareTo(points[j])==0){
                    throw new IllegalArgumentException();
                }
            }
        }
        // not enough points
        if (len < 4) return;

        Arrays.sort(points);
        Point[] tmp = points.clone();
        for (Point p : points){
            // sort every point other than p according to their slope with p
            // tmp[0] will always be p itself
            Arrays.sort(tmp, p.slopeOrder());
            for (int i = 1; i < len;){
                int j = i+1;
                while (j < len && p.slopeTo(tmp[i])==p.slopeTo(tmp[j])){
                    j++;
                }

                // greater than 2 as tmp[0] and [i] are included as well
                if (j-i > 2 && tmp[0].compareTo(min(tmp, i, j-1))<0){
                    segments.add(new LineSegment(tmp[0], max(tmp, i, j-1)));
                }
                if (j == len) break;
                // no 4s can be found, update i to where j leftoff
                // get new slope
                i = j;
            }
        }
    }

    public int numberOfSegments(){
        return segments.size();
    }

    public LineSegment[] segments(){
        int segCnt = numberOfSegments();
        LineSegment[] res = new LineSegment[segCnt];
        for (int i = 0; i < segCnt; i++){
            res[i] = segments.get(i);
        }
        return res;
    }

    public static void main(String[] args) {}
}
