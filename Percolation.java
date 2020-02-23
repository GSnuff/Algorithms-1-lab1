import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int side;
    private WeightedQuickUnionUF percolationConnectGrid;
    private WeightedQuickUnionUF connectGrid;
    private boolean[][] stateGrid;

    private void indexCheck(int row, int col) throws IllegalArgumentException {
        if (row <= 0 || row > side) {
            throw new IllegalArgumentException("The row index is out of range");
        }
        if (col <= 0 || col > side) {
            throw new IllegalArgumentException("The col index is out of range");
        }
    }

    // get index to connect 2D [n][n]array with 1D [n*n]array
    private int getIndex(int row, int col) {
        return side*(row-1) + col;
    }

    // connect upper virtual node (associated with connectGrid[0])
    // with first row
//    private void connectUpperVNode() {
//        for(int i = getIndex(1,1); i<=getIndex(1,side);++i) {
//            connectGrid.union(0,i);
//        }
//    }

    // connect bottom virtual node (associated with connectGrid[side*side+1])
    // with last row
//    private void connectBottomVNode() {
//        for(int i = getIndex(side,1); i<=getIndex(side,side); ++i) {
//            connectGrid.union(i,side*side+1);
//        }
//    }


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) throws IllegalArgumentException {
        if(n<=0) {
            throw new IllegalArgumentException("The argument is <= 0");
        }
        side = n;
        stateGrid = new boolean[n][n];

        for(int i = 0; i < side; ++i){
            for(int j = 0; j < side; ++j) {
                stateGrid[i][j]=false;
            }
        }
        connectGrid = new WeightedQuickUnionUF(side*side+1);
        percolationConnectGrid = new WeightedQuickUnionUF(side * side + 2);
//        connectUpperVNode();
//        connectBottomVNode();
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException{
        indexCheck(row,col);
        stateGrid[row-1][col-1] = true;

        if(side>1) {
            if (row-1 > 0) {
                if(row-1 < side - 1) {
                    if(col -1 > 0) {
                        if(col-1 < side -1 ) {
                            // inside
                            if (isOpen(row+1,col)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                                connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                            }
                            if (isOpen(row-1,col)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                                connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            }
                            if (isOpen(row,col-1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                                connectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                            }
                            if(isOpen(row, col+1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                                connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                            }
                        } // [1..n-2][n-1]
                        else {
                            if (isOpen(row+1,col)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                                connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                            }
                            if (isOpen(row-1,col)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                                connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            }
                            if (isOpen(row,col-1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                                connectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                            }
                        }
                    }//[1..n-2][0]
                    else {
                        if(isOpen(row+1,col)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                            connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                        }
                        if(isOpen(row-1,col)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                        }
                        if(isOpen(row,col+1)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                            connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                        }
                    }
                } // row - 1 == side - 1
                else {
                    percolationConnectGrid.union(getIndex(row,col),side*side+1);
                    if (col-1 > 0) {
                        if (col-1 < side - 1) {
                            if (isOpen(row,col-1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                                connectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                            }
                            if (isOpen(row,col +1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                                connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                            }
                            if (isOpen(row-1,col)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                                connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            }
                        } // [n-1][n-1]
                        else {
                            if(isOpen(row,col-1)) {
                                percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                                connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            }
                            if(isOpen(row-1,col)) {
                                percolationConnectGrid.union(getIndex(row,col),getIndex(row-1,col));
                                connectGrid.union(getIndex(row, col),getIndex(row-1,col));
                            }
                        }
                    } // [n-1][0]
                    else {
                        if(isOpen(row-1,col)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                            connectGrid.union(getIndex(row, col), getIndex(row - 1, col));
                        }
                        if(isOpen(row,col+1)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                            connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                        }
                    }
                }
            } // [0][i]
            else{
                percolationConnectGrid.union(0,getIndex(row,col));
                connectGrid.union(0,getIndex(row,col));
                // [0][0..n-1]
                if(col-1 > 0) {
                    // [0][1..n-2]
                    if(col-1 < side-1) {
                        if(isOpen(row,col-1)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                            connectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                        }
                        if(isOpen(row,col+1)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                            connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                        }
                        if(isOpen(row+1,col)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                            connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                        }
                    } // element [0][n-1]
                    else {
                        if(isOpen(row,col-1)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                            connectGrid.union(getIndex(row, col), getIndex(row, col - 1));
                        }
                        if(isOpen(row+1,col)) {
                            percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                            connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                        }
                    }
                } // element [0][0]
                else {
                    if(isOpen(row,col+1)) {
                        percolationConnectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                        connectGrid.union(getIndex(row, col), getIndex(row, col + 1));
                    }
                    if(isOpen(row+1,col)) {
                        percolationConnectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                        connectGrid.union(getIndex(row, col), getIndex(row + 1, col));
                    }
                }
            }
        } else {
            percolationConnectGrid.union(0,getIndex(1,1));
            percolationConnectGrid.union(getIndex(side,side), percolationConnectGrid.count()-1);
            connectGrid.union(0,getIndex(1,1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IllegalArgumentException{
        indexCheck(row,col);
        return stateGrid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IllegalArgumentException{
        indexCheck(row,col);
        return stateGrid[row-1][col-1] && connectGrid.connected(0, getIndex(row,col));

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int num = 0;

        for(int i = 0; i < side; ++i){
            for(int j = 0; j < side; ++j) {
                if(stateGrid[i][j]) {
                    ++num;
                }
            }
        }
        return num;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolationConnectGrid.connected(0, side*side+1);
    }


}
