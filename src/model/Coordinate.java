package model;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public int[] getPosition() {
        return new int[]{x, y};
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coordinate up() {
        y--;
        return this;
    }
    public Coordinate right() {
        x++;
        return this;
    }
    public Coordinate down() {
        y++;
        return this;
    }
    public Coordinate left() {
        x--;
        return this;
    }

    public String toString() {
        return ("x: " + x + "\t y: " + y);
    }

}
