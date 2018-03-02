package model;

import control.EstimatorInterface;

/**
 * Localizer by Justin Ma and Frida Börnfors
 * EDAF70, Assignment 3, 2018
 */
public class RobotLocalizer implements EstimatorInterface {
    private Robot r;
    private Sensor s;
    private HMM model;
    private Room room;
    private int[] latestReading;

    public RobotLocalizer(Robot r, Sensor s, Room room) {
        this.r = r;
        this.s = s;
        this.room = room;
        this.model = new HMM(r,s, room);
    }

    public int getNumRows() {
        return r.getRoomSize();
    }

    public int getNumCols() {
        return r.getRoomSize();
    }

    public int getNumHead() {
        return Robot.headings;
    }

    public void update() {
        r.step();
        latestReading = s.sensorReading();
        if(latestReading != null) {
            model.filter(new Coordinate(latestReading[0], latestReading[1]));
        } else {
            model.filter(null);
        }
    }

    public int[] getCurrentTruePosition() {
        return r.getPosition();
    }

    public int[] getCurrentReading() {
        return latestReading;
    }

    public double getCurrentProb(int x, int y) {
        return model.getProb(new Coordinate(x,y));
    }

    public double getOrXY(int rX, int rY, int x, int y, int h) {
        return s.getProb(new Coordinate(rX,rY), new RobotState(new Coordinate(x,y) ,h));
    }

    public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
        return model.getProbability(new RobotState(new Coordinate(x,y),h), new RobotState(new Coordinate(nX,nY),nH));
    }
}
