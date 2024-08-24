import edu.princeton.cs.algs4.StdOut;

import java.util.Stack;

public final class Board {
    private final int n;
    private final int[][] matrix;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        n = tiles.length;
        matrix = new int[n][n];
        for (int i = 0; i < n; i++){
            System.arraycopy(tiles[i], 0, matrix[i], 0, n);
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(n+"\n");
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                s.append(String.format("%2d", matrix[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int num = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n-1; j++){
                if (matrix[n-1][n-1]!=0) continue;
                int correct  = i*n+j+1;
                if (matrix[i][j]!=correct) num++;
            }
        }
        return num;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int sum = 0;
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                int num = matrix[i][j];
                if (num==0) continue;
                int x = num/n, y = num%n-1;
                int diff = Math.abs(x-i)+Math.abs(y-j);
                sum+=diff;
            }
        }
        return sum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming()==0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (this==y) return true;

        if (y==null || y.getClass() != this.getClass()) return false;

        Board that = (Board) y;
        if (n!=that.n) return false;

        for(int i =0; i < n;i++){
            for (int j= 0; j < n; j++){
                if (matrix[i][j]!=that.matrix[i][j]) return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        int[][] temp = new int[n][n];
        for (int i = 0; i < n; i++) temp[i] = matrix[i].clone();

        // can be any iterable, we use stack here
        Stack<Board> re = new Stack<Board>();

        // Find blank and move
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){

                if (matrix[i][j]==0){
                    for (int dy = -1; dy <= 1; dy++){
                        for (int dx = -1; dx <= 1; dx++){

                            if ((dy!=0 || dx != 0)&&
                                    (dy == 0 || dx == 0) &&
                                    i+dy >= 0 &&
                                    i+dy < n &&
                                    j+dx >= 0 &&
                                    j+dx < n){
                                // change to new state
                                temp[i][j] = temp[i+dy][j+dx];
                                temp[i+dy][j+dx] = 0;

                                re.push(new Board(temp));

                                // restore state
                                temp[i+dy][j+dx] = temp[i][j];
                                temp[i][j] = 0;
                            }
                        }
                    }
                    return re;
                }
            }
        }
        return re;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        // we just switch between the 4 left upper tiles, for simplicity
        // also to not check corner cases
        int [][] newMatrix = new int[n][n];
        for (int i = 0; i < n; i++){
            newMatrix[i]  = matrix[i].clone();
        }

        int temp, rowNum = 0;
        if (newMatrix[0][0]==0||newMatrix[0][1]==0){
            rowNum = 1;
        }
        temp = newMatrix[rowNum][0];
        newMatrix[rowNum][0] = newMatrix[rowNum][1];
        newMatrix[rowNum][1] = temp;

        return new Board(newMatrix);
    }

    // calls each method directly and verify that they work as prescribed
    public static void main(String[] args) {
        int[][] arr = {{8,3,2}, {4,5,6}, {7,1,0}};
        Board b = new Board(arr);
        StdOut.println("===SOURCE===");
        StdOut.println(b.toString());

        Iterable<Board> neighbours = b.neighbors();
        for (Board n: neighbours){
            StdOut.println(n.toString());
        }
    }
}
