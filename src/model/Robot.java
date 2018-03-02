package model;

import java.util.Random;

public class Robot {
    private int heading;
    private Coordinate pos; //Current position of robot
    private Random rand = new Random();
    private int roomSize;
    private Room room;
    public static final int headings = 4;
    public static final int NORTH = 0;
    public static final int EAST = 1;
    public static final int SOUTH = 2;
    public static final int WEST = 3;


    public Robot(int roomSize) {
        this.roomSize = roomSize;
        this.room = new Room(roomSize);

        //Pick random starting state
        pos = new Coordinate(rand.nextInt(roomSize), rand.nextInt(roomSize));
        heading = newRandomHeading();
    }


    public int newRandomHeading() {
        int newHeading;
        do {
            newHeading = rand.nextInt(4);
        } while (newHeading == heading);

        if (room.wallEncountered(pos, newHeading)) { //Badly chosen new heading, try a new one
            return newRandomHeading();
        }
        return newHeading;
    }

    /**
     * Simulates a robot step
     * Moves one tile, and possibly chooses new heading
     */
    public void step() {
        //Pick new heading or stick with the same
        if (room.wallEncountered(pos, heading)) {
            heading = newRandomHeading();
        } else {
            double p = rand.nextDouble();
            if (p < 0.3) { //Not encountering a wall, probability of choosing new direction is 0.3
                heading = newRandomHeading();
            }
        }
        //Step
        move();
    }

    public void move() {
        switch (heading) {
            case NORTH:
                pos = pos.up();
                break;
            case EAST:
                pos = pos.right();
                break;
            case SOUTH:
                pos = pos.down();
                break;
            case WEST:
                pos = pos.left();
                break;
        }
    }

    public int[] getPosition() {
        return pos.getPosition();
    }

    public int getRoomSize() {
        return room.getSize();
    }

    /**
     * Determines the ring surrounding pos
     * @param pos
     * @return the coordinates in the surrounding ring
     */
    public int[][] getLS(int[] pos) {
        return room.getLS(pos);
    }

    /**
     * Determines "second ring" surrounding pos
     * @param pos
     * @return the coordinates in the second surrounding ring
     */
    public int[][] getLS2(int[] pos) {
        return room.getLS2(pos);
    }

    public Room getRoom() {
        return room;
    }
}
