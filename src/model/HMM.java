package model;

import java.util.ArrayList;
import java.util.List;

public class HMM {
    private RobotState[] states;    //Possible states
    private List<Double[]> obsProb; //List with probabilities for all possible observations
    private Coordinate[] obs;       //Possible observations
    private double[][] T;

    public HMM(Robot robot) {
        states = new RobotStates[robot.getRoomSize()*robot.getRoomSize()*Robot.headings];
        initStates();
        setTransitionModel();
        initObservations();
        obsProb = new ArrayList<Double[20]>(obs.length); //21 possible observations
    }

    /**
     * Lists possible robot states in attribute states
     */
    private void initStates() {
        states = new RobotState[robot.getRoomsize()*robot.getRoomSize*Robot.headings]; //Number of possible states
        for(int i = 0; i<states.length; i++) {
            //states[i] = new RobotState(row, col, h)
        }
    }

    /**
     * Lists possible observations in attribute obs
     */
    private void initObservations() {
        obs=new Coordinate[21];
        for(int i =0; i<obs.length-1;i++) {
            //obs[i] = MÖJLIG OBSERVATIOn
        }
        obs[20] = null; //NOTHING
    }

    /**
     * Specifies the transition model
     */
    private void setTransitionModel() {
        T = new double[states.length][states.length];

    }
}
