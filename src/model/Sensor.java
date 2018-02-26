package model;

import java.util.Random;

public class Sensor {
    private Robot r;
    private Random rand = new Random();

    public Sensor(Robot r) {
        this.r = r;
    }

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

}
