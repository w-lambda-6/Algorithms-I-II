import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // creates n-by-n grid, with all sites initially blocked
    private WeightedQuickUnionUF uf;
    private boolean[][] open;
    private final int gridWidth;
    private int openSites;
    private final int[] dx = {-1, 0, 0, 1};
    private final int[] dy = {0, -1, 1, 0};

    // compute the corresponding index of a site in the UF
    // as the UF is implemented as an array
    // rows and cols are 1-indexed
    private int calcIndex(int row, int col) {
        int index;
        index = gridWidth*(row-1) + col;
        return index;
    }

    // checks if input is valid
    private boolean inGrid(int row, int col){
        return row <= gridWidth && row > 0 && col > 0 && col <= gridWidth;
    }

    // connect the given site with its neighbors if the neighbor has been open.
    private void connectNeighbors(int row, int col) {
        int newRow, newCol; //rows and cols of the neighbors
        int currIndex = calcIndex(row, col);

        //checks all neighbors one by one
        for (int i = 0; i < 4; i++){
            newRow = row + dx[i];
            newCol = col + dy[i];
            if (inGrid(newRow, newCol) && isOpen(newRow, newCol)){
                uf.union(calcIndex(newRow, newCol), currIndex);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////

    public Percolation(int n){
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        // the add 2 is for the top and bottom virtual sites
        uf = new WeightedQuickUnionUF(n*n + 2);

        gridWidth = n;
        openSites = 0;
        open = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                open[i][j] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(!inGrid(row, col)){
            throw new IllegalArgumentException();
        }

        int currIndex = calcIndex(row, col);
        if (!open[row-1][col-1]){
            open[row-1][col-1] = true;
            // rows here are actually 1-indexed
            if (row == 1) {
                // top row connected to top virtual node
                uf.union(0, currIndex);
            }
            if (row == gridWidth){
                // bottom row connected to bottom virtual node
                uf.union(gridWidth * gridWidth + 1, currIndex);
            }
            connectNeighbors(row,col);
            openSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(!inGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(!inGrid(row, col)) {
            throw new IllegalArgumentException();
        }
        return uf.find(calcIndex(row, col)) == uf.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openSites;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(0) == uf.find(gridWidth * gridWidth + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(3, 3);
        perc.open(3, 1);
    }
}
