package model;

public class Room {
    private int size;
    private int[][] room;

    public Room(int size) {
     this.size = size;
    }

    public int getSize(){
        return size;
    }

    public boolean wallEncountered(Coordinate pos, Direction heading) {
        if (pos.getX() == 0 && heading.equals(Direction.WEST)) {
            return false;
        }
        if (pos.getX() == size-1 && heading.equals(Direction.EAST)) {
            return false;
        }
        if (pos.getY() == 0 && heading.equals(Direction.NORTH)) {
            return false;
        }
        if (pos.getY() == size -1 && heading.equals(Direction.SOUTH)) {
            return false;
        }
        return true;

    }

}
