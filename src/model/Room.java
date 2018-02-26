package model;

public class Room {
    private int size;
    //private int[][] room;

    public Room(int size) {
     this.size = size;
    }

    public int getSize(){
        return size;
    }

    public boolean wallEncountered(Coordinate pos, Direction heading) {
        if (pos.getRow() == 0 && heading.equals(Direction.NORTH)) {
            return true;
        }
        if (pos.getRow() == size-1 && heading.equals(Direction.SOUTH)) {
            return true;
        }
        if (pos.getCol() == 0 && heading.equals(Direction.WEST)) {
            return true;
        }
        if (pos.getCol() == size -1 && heading.equals(Direction.EAST)) {
            return true;
        }
        return false;

    }

}
