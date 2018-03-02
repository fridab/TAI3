package model;

import java.util.ArrayList;
import java.util.Random;

public class Sensor {
    private Robot r;
    private Random rand = new Random();

    public Sensor(Robot r) {
        this.r = r;
    }

    /**
     * Simulates a sensor reading for the robot it belongs
     * to given the sensor model and current position
     * @return a reading (row, col)
     */
    public int[] sensorReading() {
        int[] pos = r.getPosition();
        int[][] ls = r.getLS(pos);
        int[][] ls2 = r.getLS2(pos);

        Double prob = rand.nextDouble();
        int probTot1 = ls.length;
        int probTot2 = ls2.length;
        if (prob <= 0.1) {
            return pos;
        } else if (prob <= (0.1 + probTot1 * 0.05)) {
            int ind = rand.nextInt(ls.length);
            return ls[ind];
        } else if (prob <= (0.1 + probTot1 * 0.05 + probTot2 * 0.025)) {
            int ind = rand.nextInt(ls2.length);
            return ls2[ind];
        }

        return null;
    }

    /**
     *
     * @param reading the sensor reading: (row, col)
     * @param state the robot state (x,y,h)
     * @return probability to obtain reading, given a robotstate and a robot
     */
    public double getProb(Coordinate reading, RobotState state) {
        Coordinate pos = state.getPosition();
        int[][] ls = r.getLS(pos.getPosition());
        int[][] ls2 = r.getLS2(pos.getPosition());
        ArrayList<Coordinate> lsCoord = new ArrayList<Coordinate>();
        ArrayList<Coordinate> ls2Coord = new ArrayList<Coordinate>();

        for(int i = 0 ; i<ls.length; i++) {
            lsCoord.add(new Coordinate(ls[i][0], ls[i][1]));
        }
        for(int i = 0 ; i<ls2.length; i++) {
            ls2Coord.add(new Coordinate(ls2[i][0], ls2[i][1]));
        }

        if(reading == null) { //"Nothing"
            return (1.0 - 0.1 - ls.length*0.05 - ls2.length*0.025);
        }

        if(reading.equals(pos)) { //Correct reading
            return 0.1;
        }

        if(lsCoord.contains(reading)) {
            return 0.05;
        }

        if(ls2Coord.contains(reading)) {
            return 0.025;
        }
        else {
            return 0;
        }

    }

}
