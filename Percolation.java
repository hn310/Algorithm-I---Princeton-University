import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/*---------------------------------------------------------
 *  Author:        Nam.NVH
 *  Written:       29/01/2016
 *  Briefly describe your program
 *---------------------------------------------------------*/
public class Percolation {

    private int boundary;
    private int[] state;
    private WeightedQuickUnionUF cellStorage;

    /* create N-by-N grid, with all sites blocked */
    public Percolation(final int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        boundary = N;
        cellStorage = new WeightedQuickUnionUF(N * N + 2);
        state = new int[N * N + 2];
        for (int index = 0; index < N * N; index++) {
            state[index] = 0;
        }
        state[N * N] = 1;
        state[N * N + 1] = 1;
    }

    /* open site (row i, column j) if it is not open already */
    public void open(final int i, final int j) {
        // validate the indices of the site that it receives
        checkIndices(i, j);
        // mark the site as open
        if (isOpen(i, j)) {
            return; // if cell(i, j) is opened then exits
        }
        int cell = getCellIndex(i, j);
        state[cell] = 1;
        // sequence of WeightedQuickUnionUF operations that links the site in
        // question to its open neighbors
        // if not top row
        if (i != 1 && isOpen(i - 1, j)) {
            union(getCellIndex(i - 1, j), cell);
        } else if (i == 1) {
            // connect to virtual top cell
            union(cell, boundary * boundary);
        }
        // if not bottom row
        if (i != boundary && isOpen(i + 1, j)) {
            union(getCellIndex(i + 1, j), cell);
        } else if (i == boundary) {
            // connect to virtual bottom cell
            union(cell, boundary * boundary + 1);
        }
        // if not left border
        if (j != 1 && isOpen(i, j - 1)) {
            union(getCellIndex(i, j - 1), cell);
        }
        // if not right border
        if (j != boundary && isOpen(i, j + 1)) {
            union(getCellIndex(i, j + 1), cell);
        }
    }

    /* is site (row i, column j) open? */
    public boolean isOpen(final int i, final int j) {
        checkIndices(i, j);
        return state[getCellIndex(i, j)] == 1;
    }

    /* is site (row i, column j) full? */
    public boolean isFull(final int i, final int j) {
        checkIndices(i, j);
        return cellStorage.connected(boundary * boundary, getCellIndex(i, j));
    }

    /* does the system percolate? */
    public boolean percolates() {
        return cellStorage.connected(boundary * boundary, boundary * boundary
                + 1);
    }

    private void checkIndices(final int i, final int j) {
        if (i <= 0 || j <= 0 || i > boundary || j > boundary) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int getCellIndex(final int row, final int column) {
        return (boundary * (row - 1)) + column - 1;
    }

    private void union(final int a, final int b) {
        if (!cellStorage.connected(a, b)) {
            cellStorage.union(a, b);
        }
    }

    public static void main(final String[] args) {

    }
}