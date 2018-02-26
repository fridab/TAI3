package model;

import control.EstimatorInterface;

/**
 * Localizer by Justin Ma and Frida Börnfors
 * EDAF70, Assignment 3, 2018
 */
public class RobotLocalizer implements EstimatorInterface {
    private Robot r;
    private Sensor s;

    public RobotLocalizer(Robot r, Sensor s) {
        this.r = r;
        this.s = s;
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
        //Sensorn ska uppdateras
    }

    public int[] getCurrentTruePosition() {
        return r.getPosition();
    }

    public int[] getCurrentReading() {
        return s.sensorReading();
    }

    public double getCurrentProb(int x, int y) {
        return 0;
    }

    public double getOrXY(int rX, int rY, int x, int y, int h) {
        return 0;
    }

    public double getTProb(int x, int y, int h, int nX, int nY, int nH) {
        return 0;
    }
}
