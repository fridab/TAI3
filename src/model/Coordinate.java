package model;

public class Coordinate {
    private int row;
    private int col;

    public Coordinate(int row, int col) {
        this.row=row;
        this.col=col;
    }

    public int[] getPosition() {
        return new int[]{row, col};
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Coordinate up() {
        row--;
        return this;
    }
    public Coordinate right() {
        col++;
        return this;
    }
    public Coordinate down() {
        row++;
        return this;
    }
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
