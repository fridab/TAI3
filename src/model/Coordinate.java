package model;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row=row;
        this.col=col;
    }

    /**
     * @return position as an int-vector [row, col]
     */
    public int[] getPosition() {
        return new int[]{row, col};
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    /**
     * Moves coordinate one row up
     * @return this coordinate
     */
    public Coordinate up() {
        row--;
        return this;
    }

    /**
     * Moves coordinate one column to the right
     * @return this coordinate
     */
    public Coordinate right() {
        col++;
        return this;
    }

    /**
     * Moves coordinate one row down
     * @return this coordinate
     */
    public Coordinate down() {
        row++;
        return this;
    }

    /**
     * Moves coordinate one column to the left
     * @return this coordinate
     */
    public Coordinate left() {
        col--;
        return this;
    }

    public String toString() {
        return ("row: " + row + "\t col: " + col);
    }

    public boolean equals(Object o) {
        if(! (o instanceof Coordinate)) {
            return false;
        }
        return ((Coordinate)o).row == row && ((Coordinate)o).col == col;
    }

}
