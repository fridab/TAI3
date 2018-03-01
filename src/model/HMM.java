package model;

import java.util.ArrayList;
import java.util.List;

public class HMM {
    private RobotState[] states;    //Possible states
    private List<double[]> obsProb; //List with probabilities for all possible observations
    private Coordinate[] obs;       //Possible observations
    private double[][] T;
    private Robot robot;
    private Sensor sensor;
    private double[] previousF;
    private Room r;

    public HMM(Robot robot, Sensor sensor, Room r) {
        this.robot = robot;
        this.r = r;
        states = new RobotState[robot.getRoomSize() * robot.getRoomSize() * Robot.headings];
        initObservations();
        initStates();
        setTransitionModel();
        obsProb = new ArrayList<double[]>(obs.length); //21 possible observations
        this.sensor = sensor;
        initObsProb();
        previousF = new double[states.length];
        for (int i = 0; i < previousF.length; i++) {
            previousF[i] = ((double) 1 / states.length); //Assuming all states have equal probabilities at first
        }
    }

    /**
     * Computes all state estimates when given evidence e
     *
     * @param e the evidence/observation
     */
    public void filter(Coordinate e) {
        //Find which evidence object we have
        int index = -1;
        for (int i = 0; i < obs.length; i++) {
            if (obs[i] == null && e == null) {
                index = i;
                break;
            }
            if (obs[i].equals(e)) {
                index = i;
                break;
            }
        }

        double[][] o = createDiagonalMatrix(obsProb.get(index));
        double[][] tT = transpose(T);

        double[][] futureF = new double[1][states.length];
        //futureF = alpha * o*tT* previousF; NEEDS TO BE IMPLEMENTED
        double[][] otT = multiplyMatrices(o, tT);
        double[][] fMatrix = new double[previousF.length][1];
        for (int i = 0; i < previousF.length; i++) {
            fMatrix[i][0] = previousF[i];
        }
        futureF = multiplyMatrices(otT, fMatrix);
        futureF = normalize(futureF);
        for (int i = 0; i < futureF.length; i++) {
            previousF[i] = futureF[i][0];
        }

    }

    /**
     * Creates a diagonal matrix out of the diagonal vector
     *
     * @param vector
     * @return
     */
    private double[][] createDiagonalMatrix(double[] vector) {
        double[][] matrix = new double[vector.length][vector.length];
        for (int i = 0; i < vector.length; i++) {
            matrix[i][i] = vector[i];
        }
        return matrix;
    }

    /**
     * Transposes a matrix
     *
     * @param t
     * @return
     */
    private double[][] transpose(double[][] t) {
        double[][] tT = new double[t[0].length][t.length];
        for (int i = 0; i < t.length; i++) {
            for (int j = 0; j < t[0].length; j++) {
                tT[j][i] = t[i][j];
            }
        }
        return tT;
    }

    /**
     * Multiplies matrices a and b. a needs to have the same number of cols as b has rows
     * Resulting matrix will have dimension [a.rows b.cols]
     *
     * @param a matrix a
     * @param b matrix b
     * @return matrix ab
     */
    private double[][] multiplyMatrices(double[][] a, double[][] b) {
        if (a[0].length == b.length) {
            double[][] product = new double[a.length][b[0].length];
            double[][] bT = transpose(b);
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < b[0].length; j++) {
                    product[i][j] = dotProduct(a[i], bT[j]);
                }
            }
            return product;
        }
        return null; //Matrix multiplication not defined
    }

    /**
     * Normalizes the matrix such that the sum of all matrix elements equals 1
     *
     * @param matrix
     * @return the normalized matrix
     */
    private double[][] normalize(double[][] matrix) {
        double sum = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                sum += matrix[i][j];
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = matrix[i][j] / sum;
            }
        }
        return matrix;
    }

    /**
     * Calculates dot product of two vectors
     *
     * @param a  first vector
     * @param bT the transpose of the second vector
     * @return dot product of a and b
     */
    private double dotProduct(double[] a, double[] bT) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) {
            sum += a[i] * bT[i];
        }
        return sum;
    }

    /**
     * Computes the summed probability to be in position
     * Prob = P(position, North) + P(position, South) + P(position, East) + P(position, West)
     *
     * @param position the position of the state
     * @return
     */
    public double getProb(Coordinate position) {
        double sum = 0;
        for (int h = 0; h < 4; h++) {
            RobotState state = new RobotState(position, h);
            //Find which state object we have
            int index = -1;
            for (int i = 0; i < states.length; i++) {
                if (states[i].equals(state)) {
                    index = i;
                    break;
                }
            }
            if (index == -1) { //Have no such state
                return -1;
            }
            double prob = previousF[index];
            sum += prob;
        }
        return sum;
    }

    /**
     * Lists possible robot states in attribute states
     */
    private void initStates() {
        states = new RobotState[robot.getRoomSize() * robot.getRoomSize() * Robot.headings]; //Number of possible states
        int index = 0;
        for (int i = 0; i < obs.length - 1; i++) { //Do not include the "nothing"-observation
            for (int j = 0; j < 4; j++) {
                states[index] = new RobotState(obs[i], j);
                index++;
            }

        }
    }

    /**
     * Lists possible observations in attribute obs
     */
    private void initObservations() {
        obs = new Coordinate[robot.getRoomSize() * robot.getRoomSize() + 1];
        int index = 0;
        for (int i = 0; i < robot.getRoomSize(); i++) {
            for (int j = 0; j < robot.getRoomSize(); j++) {
                obs[index] = new Coordinate(i, j);
                index++;
            }
            obs[index] = null; //NOTHING
        }
    }

    /**
     * Specifies the transition model
     * Tij gives the probability to currently be in state j, given
     * the previous one was state i
     */
    private void setTransitionModel() {
        T = new double[states.length][states.length];
        for (int i = 0; i < T.length; i++) {
            for (int j = 0; j < T[0].length; j++) {
                T[i][j] = getProbability(i, j, r);
            }
        }
    }

    /**
     * @param i previous state
     * @param j current state
     * @return probability [0,1] to be in j given i
     */
    private double getProbability(int i, int j, Room r) {
        RobotState current = states[j];
        RobotState previous = states[i];
        return previous.getTo(current, r);

    }

    public double getProbability(RobotState i, RobotState j) {
        return i.getTo(j, r);
    }

    /**
     * Initializes the diagonal elements for all observation matrices
     * Here stored as vector
     */
    private void initObsProb() {
        for (int i = 0; i < obs.length; i++) { //All possible observations have their own matrix
            double[] probs = new double[states.length]; //Store probabilities in 1D-array
            for (int j = 0; j < states.length; j++) { //For every possible robot state
                probs[j] = sensor.getProb(obs[i], states[j]); //Calculate the probability and add to vector
            }
            obsProb.add(probs);
        }

    }

}
