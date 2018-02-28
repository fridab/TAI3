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

    public HMM(Robot robot, Sensor sensor) {
        this.robot = robot;
        states = new RobotState[robot.getRoomSize()*robot.getRoomSize()*Robot.headings];
        initObservations();
        initStates();
        setTransitionModel();
        obsProb = new ArrayList<double[]>(obs.length); //21 possible observations
        initObsProb();
        this.sensor = sensor;
        previousF = new double[states.length];
        for(int i=0; i<previousF.length; i++) {
            previousF[i]=(1/states.length); //Assuming all states have equal probabilities at first
        }
    }

    /**
     * MATRIX CALCULATIONS NEED TO BE IMPLEMENTED, SEE COMMENT
     * Computes all state estimates when given evidence e
     * @param e the evidence/observation
     */
    public void filter(Coordinate e) {
        //Find which evidence object we have
        int index = -1;
        for(int i = 0; i<obs.length; i++) {
            if(obs[i].equals(e)) {
                index=i;
                break;
            }
        }

        double[][] o = createDiagonalMatrix(obsProb.get(index));
        double[][] tT = transpose(T);

        double[] futureF = new double[states.length];
        //futureF = alpha * o*tT* previousF; NEEDS TO BE IMPLEMENTED
        previousF=futureF;
    }

    /**
     * NEEDS TO BE IMPLEMENTED
     * Creates a diagonal matrix out of the diagonal vector
     * @param vector
     * @return
     */
    private double[][] createDiagonalMatrix(double[] vector) {
        double[][] matrix = new double[vector.length][vector.length];
        return matrix;
    }

    /**
     * NEEDS TO BE IMPLEMENTED
     * Transposes a matrix
     * @param t
     * @return
     */
    private double[][] transpose(double[][] t) {
        double[][] tT = new double[t.length][t[0].length];
        return tT;
    }

    /**
     * Computes the summed probability to be in position
     * Prob = P(position, North) + P(position, South) + P(position, East) + P(position, West)
     * @param position the position of the state
     * @return
     */
    public double getProb(Coordinate position) {
        double sum=0;
        for(int h = 0; h<4; h++) {
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
            sum+=prob;
        }
        return sum;
    }

    /**
     * Lists possible robot states in attribute states
     */
    private void initStates() {
        states = new RobotState[robot.getRoomSize()*robot.getRoomSize()*Robot.headings]; //Number of possible states
        int index = 0;
        for(int i = 0; i<obs.length-1; i++) { //Do not include the "nothing"-observation
            for(int j=0; j<4; j++) {
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
        for(int i =0; i<T.length; i++) {
            for(int j =0; j<T[0].length; j++) {
                T[i][j] = getProbability(i,j);
            }
        }
    }

    /**
     * @param i previous state
     * @param j current state
     * @return probability [0,1] to be in j given i
     */
    private double getProbability(int i, int j) {
        RobotState current = states[j];
        RobotState previous = states[i];
        return previous.getTo(current);
    }

    public double getProbability(RobotState i, RobotState j) {
        return i.getTo(j);
    }

    /**
     * Initializes the diagonal elements for all observation matrices
     * Here stored as vector
     */
    private void initObsProb() {
        for(int i =0; i<obs.length; i++) { //All possible observations have their own matrix
            double[] probs = new double[states.length]; //Store probabilities in 1D-array
            for(int j =0; j<states.length; j++) { //For every possible robot state
                probs[j] = sensor.getProb(obs[i], states[j]); //Calculate the probability and add to vector
            }
            obsProb.add(probs);
        }

    }

}
