package model;

import java.util.Random;

import static model.Direction.NORTH;

public class Robot {
    private Direction heading;
    private Coordinate pos;
    private Random rand = new Random();
    private int roomSize;
    private Room room;
    public static final int headings = 4;

    public Robot(int roomSize) {
        heading = newRandomHeading();
        this.room = new Room(roomSize);
        pos = new Coordinate(2,2);
    }


    public Direction newRandomHeading() {
        int choice = rand.nextInt(4);
        switch(choice) {
            case 0:
                return NORTH;
            case 1:
                return Direction.EAST;
            case 2:
                return Direction.SOUTH;
            case 3:
                return Direction.WEST;
            default:
                return null;
        }
    }


    public void step() {
        //Pick new heading
        if(room.wallEncountered(pos, heading)) {
            heading = newRandomHeading(); //Can give the same heading with this implementation. FIX THIS.
        } else {
            double p = rand.nextDouble();
            if(p<0.3) { //Not encountering a wall, probability of choosing new direction is 0.3
                heading = newRandomHeading();
            }
        }
        //Step
        move();
        System.out.println("Current position: " + pos + " heading: " + heading);
    }

    public void move() {
        switch(heading) {
            case NORTH:
                pos = pos.up();
            case EAST:
                pos = pos.left();
            case SOUTH:
                pos = pos.down();
            case WEST:
                pos = pos.right();
        }
    }
    public int[] getPosition() {
        return pos.getPosition();
    }

    public int getRoomSize() {
        return room.getSize();
    }
}
