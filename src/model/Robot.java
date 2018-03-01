package model;

import java.util.Random;

import static model.Direction.*;

public class Robot {
    private int heading;
    private Coordinate pos;
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
        pos = new Coordinate(2, 2);
        heading = newRandomHeading();
    }


    public int newRandomHeading() { //JUST NU KAN MAN FÅ SAMMA IGEN - SE TILL ATT DET BLIR EN NY
        int newHeading;
        do {
            int choice = rand.nextInt(4);
            switch (choice) {
                case 0:
                    newHeading = NORTH;
                    break;
                case 1:
                    newHeading = EAST;
                    break;
                case 2:
                    newHeading = SOUTH;
                    break;
                case 3:
                    newHeading = WEST;
                    break;
                default:
                    newHeading = -1;
                    break;
            }
        }
        while (newHeading == heading);

        if (room.wallEncountered(pos, newHeading)) {
            return newRandomHeading();
        }
        return newHeading;
    }


    public void step() {
        //Pick new heading
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
        System.out.println("Current position: " + pos + " heading: " + heading);
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

    public int[][] getLS(int[] pos) {
        return room.getLS(pos);
    }

    public int[][] getLS2(int[] pos) {
        return room.getLS2(pos);
    }

    public Room getRoom() {
        return room;
    }
}
