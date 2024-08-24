import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> segArr = new ArrayList <>();
    // oops, couldn't use array, immutable: private LineSegment[] segArray;

    ////////////////////////////////////////////////////////////////////////////

    // finds all line segments that contains 4 points
    public BruteCollinearPoints(Point[] points){
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

        if (len < 4) return;
        Point[] tmp = points.clone();
        for (int i = 0; i < len; i++){
            for (int j = i+1; j < len; j++){
                for (int k = j+1; k < len; k++){
                    for (int l = k + 1; l < len; l++){
                        double s1 = tmp[i].slopeTo(tmp[j]);
                        double s2 = tmp[i].slopeTo(tmp[k]);
                        double s3 = tmp[i].slopeTo(tmp[l]);
                        if (s1 == s2 && s2 == s3){
                            segArr.add(new LineSegment(tmp[i], tmp[l]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments(){
        return segArr.size();
    }

    public LineSegment[] segments(){
        int segCnt = numberOfSegments();
        LineSegment[] res = new LineSegment[segCnt];
        for (int i = 0; i < segCnt; i++){
            res[i] = segArr.get(i);
        }
        return res;
    }

}
