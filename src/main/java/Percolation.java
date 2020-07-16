public class Percolation {

    private boolean [][] topSide;
    private boolean [][] bottomSide;

    private boolean percolation;
    private int[][] array;
    private int[][] treeSize;
    private int openCounter;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        int realSize = n+1;
        array = new int[realSize][realSize];
        treeSize = new int[realSize][realSize];
        for (int i = 0; i < realSize; i++) {
            for (int j = 0; j < realSize; j++) {
                treeSize[i][j] = 1;
            }
        }
        topSide = new boolean[realSize][realSize];
        bottomSide = new boolean[realSize][realSize];
        openCounter = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!checkRange(row,col)){
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        array[row][col] = getID(row, col);
        topSide[row][col] = checkTop(getID(row,col));
        bottomSide[row][col] = checkBottom(getID(row,col));
        openCounter++;
        if (col - 1 > 0) {
            if (isOpen(row, col - 1)) {
                union(row, col - 1, row, col);
            }
        }
        if (col + 1 <= array.length - 1) {
            if (isOpen(row, col + 1)) {
                union(row, col + 1, row, col);
            }
        }

        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                union(row - 1, col, row, col);
            }
        }
        if (row + 1 <= array.length - 1) {
            if (isOpen(row + 1, col)) {
                union(row + 1, col, row, col);
            }
        }


    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!checkRange(row, col)) {
            throw new IllegalArgumentException();
        }
        return array[row][col] != 0;
    }

    private int getRoot(int row, int col) {
        int unicID = getID(row, col);
        while (unicID != array[row][col]) {
            unicID = array[getY(unicID)][getX(unicID)];
            row = getY(unicID);
            col = getX(unicID);
        }
        return unicID;
    }

    private void union(int startY, int startX, int stopY, int stopX) {
        int idStart = getRoot(startY, startX);
        int idStop = getRoot(stopY, stopX);
        if (idStart == idStop) {
            return;
        }
        if (treeSize[getY(idStart)][getX(idStart)] >= treeSize[getY(idStop)][getX(idStop)]) {
            if (checkTop(idStop)) {
                topSide[getY(idStart)][getX(idStart)] = true;
            }
            if (checkBottom(idStop)) {
                bottomSide[getY(idStart)][getX(idStart)] = true;
            }
            array[getY(idStop)][getX(idStop)] = getID(getY(idStart), getX(idStart));
            treeSize[getY(idStart)][getX(idStart)] += treeSize[getY(idStop)][getX(idStop)];
            percolationListener(idStart);
        } else {
            if (checkTop(idStart)) {
                topSide[getY(idStop)][getX(idStop)] = true;
            }
            if (checkBottom(idStart)) {
                bottomSide[getY(idStop)][getX(idStop)] = true;
            }
            array[getY(idStart)][getX(idStart)] = getID(getY(idStop), getX(idStop));
            treeSize[getY(idStop)][getX(idStop)] += treeSize[getY(idStart)][getX(idStart)];
            percolationListener(idStop);
        }
    }


    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!checkRange(row, col)){
            throw new IllegalArgumentException();
        }
        int root = getRoot(row,col);
    return topSide[getY(root)][getX(root)];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCounter;
    }

//    // does the system percolate?
    public boolean percolates() {
        if (array.length == 2 && isOpen(1,1)) {
            return true;
        }
        return percolation;
    }

    private int getY(int id) {
        return id / array.length;
    }

    private int getX(int id) {
        int y = getY(id);
        return id - y * array.length;
    }


    private int getID(int row, int col) {
        return array.length * row + col;
    }

    private boolean checkRange(int row, int col) {
        return row > 0 && col > 0 && row < array.length && col < array.length;
    }

    private boolean checkTop(int id) {
        if (getY(id) == 1){
            return true;
        } else {
            return topSide[getY(id)][getX(id)];
        }
    }

    private void percolationListener(int id) {
        if (topSide[getY(id)][getX(id)] && bottomSide[getY(id)][getX(id)]){
            percolation = true;
        }
    }

    private boolean checkBottom(int id) {
        if (getY(id) == array.length-1){
            return true;
        } else {
            return bottomSide[getY(id)][getX(id)];
        }
    }

    // test client (optional)
    public static void main(String[] args) {
    }
}
