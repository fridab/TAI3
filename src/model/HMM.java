package model;

import java.util.ArrayList;
import java.util.List;

public class HMM {
    private RobotState[] states;    //Possible states
    private List<Double[]> obsProb; //List with probabilities for all possible observations
    private Coordinate[] obs;       //Possible observations
    private double[][] T;
    private Robot robot;

    public HMM(Robot robot) {
        this.robot = robot;
        states = new RobotState[robot.getRoomSize()*robot.getRoomSize()*Robot.headings];
        initStates();
        setTransitionModel();
        initObservations();
        obsProb = new ArrayList<Double[20]>(obs.length); //21 possible observations
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
     * @return probability [0,1] to be in i given j
     */
    private double getProbability(int i, int j) {
        RobotState current = states[j];
        RobotState previous = states[i];
        return previous.getTo(current);
    }
}
