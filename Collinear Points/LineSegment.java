public class LineSegment {
    // a line consists of 2 points
    private final Point p;
    private final Point q;

    ////////////////////////////////////////////////////////////////////////////
    public LineSegment(Point p, Point q){
        if (p == null || q == null){
            throw new NullPointerException();
        }
        this.p = p;
        this.q = q;
    }

    public void draw(){
        p.drawTo(q);
    }

    public String toString(){
        return p + "->" + q;
    }

    @Override
    public int hashCode(){
        throw new UnsupportedOperationException();
    }

    public static void main(String[] args) {}
}
