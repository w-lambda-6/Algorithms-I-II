import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.List;

public class PointSET {
    private SET<Point2D> points;

    public PointSET(){
        points = new SET<>();
    }

    public boolean isEmpty(){
        return points.isEmpty();
    }

    public int size(){
        return points.size();
    }

    public void insert(Point2D p){
        if (p == null){
            throw new IllegalArgumentException();
        }
        if (!points.contains(p)){
            points.add(p);
        }
    }

    public boolean contains(Point2D p){
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    public void draw(){
        for (Point2D point: points){
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        if (rect == null){
            throw new IllegalArgumentException("rect is null");
        }
        List<Point2D>  containedPoints = new ArrayList<>();
        for (Point2D point: points){
            if (rect.contains(point)){
                containedPoints.add(point);
            }
        }
        return containedPoints;
    }

    public Point2D nearest(Point2D p){
        if (p == null){
            throw new IllegalArgumentException();
        }
        Point2D nearestPoint = null;
        double dist = Double.MAX_VALUE;
        for (Point2D point : points){
            if (p.distanceTo(point) < dist){
                nearestPoint = point;
                dist = p.distanceTo(point);
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {}
}
