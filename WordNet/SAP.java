import edu.princeton.cs.algs4.*;

import java.util.Iterator;

public class SAP {
    private final Digraph DIG;

    private boolean hasValue(Iterable<Integer> a){
        Iterator<Integer> it = a.iterator();
        return it.hasNext();
    }

    ////////////////////////////////////////////////////////////////////////////

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if (G==null) throw new IllegalArgumentException();
        DIG = new Digraph(G);
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(DIG, v);
        BreadthFirstDirectedPaths bfdp2 = new BreadthFirstDirectedPaths(DIG, w);
        int vertices = DIG.V();
        int minPath = Integer.MAX_VALUE;
        for (int i =0; i < vertices; i++){
            if (bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)){
                minPath = Math.min(bfdp1.distTo(i)+bfdp2.distTo(i), minPath);
            }
        }
        return minPath == Integer.MAX_VALUE ? -1:minPath;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(DIG, v);
        BreadthFirstDirectedPaths bfdp2 = new BreadthFirstDirectedPaths(DIG, w);
        int vertices = DIG.V();
        int minPath = DIG.V();      // interesting
        int commonAncestor = -1;
        for(int i =0; i < vertices; i++){
            if (bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)){
                int currentPath = bfdp1.distTo(i)+bfdp2.distTo(i);
                if (currentPath < minPath){
                    minPath = currentPath;
                    commonAncestor = i;
                }
            }
        }
        return commonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if (v==null || w == null) throw new IllegalArgumentException();
        for (Integer i:v) if (i == null) throw new IllegalArgumentException();
        for (Integer i:w) if (i == null) throw new IllegalArgumentException();

        if (!hasValue(v)||!hasValue(w)) return -1;

        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(DIG, v);
        BreadthFirstDirectedPaths bfdp2 = new BreadthFirstDirectedPaths(DIG, w);

        int vertices = DIG.V();
        int minPath = Integer.MAX_VALUE;
        for (int i = 0; i < vertices; i++){
            if (bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)){
                minPath = Math.min(minPath, bfdp1.distTo(i) + bfdp2.distTo(i));
            }
        }
        return minPath == Integer.MAX_VALUE ? -1 : minPath;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if (v==null || w == null) throw new IllegalArgumentException();
        for (Integer i:v) if (i == null) throw new IllegalArgumentException();
        for (Integer i:w) if (i == null) throw new IllegalArgumentException();

        if (!hasValue(v)||!hasValue(w)) return -1;

        BreadthFirstDirectedPaths bfdp1 = new BreadthFirstDirectedPaths(DIG, v);
        BreadthFirstDirectedPaths bfdp2 = new BreadthFirstDirectedPaths(DIG, w);
        int vertices = DIG.V();
        int minPath = DIG.V();
        int commonAncestor = -1;
        for (int i = 0; i < vertices; i++){
            if (bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)){
                int currentPath = bfdp1.distTo(i) + bfdp2.distTo(i);
                if (currentPath < minPath) {
                    minPath = currentPath;
                    commonAncestor = i;
                }
            }
        }
        return commonAncestor;
    }

    // test client
    public static void main(String[] args){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
