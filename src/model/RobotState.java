package model;

public class RobotState {
    private int heading;
    private Coordinate pos;

    public RobotState(Coordinate pos, int heading) {
        this.pos = pos;
        this.heading = heading;
    }

    /**
     * @return true if position and heading is equal
     */
    public boolean equals(Object o) {
        if(!(o instanceof RobotState)) {
            return false;
        }
        RobotState obj = (RobotState) o;
        return pos.equals(obj.pos) && heading==((RobotState) o).heading;
    }

    /**
     * @param target
     * @return Probability of getting to target from this state
     */
    public double getTo(RobotState target) {
        if(this.equals(target)) { //It will always move
            return 0;
        }
        if(Math.abs(pos.getRow()-target.pos.getRow()) > 1) { //Too far away
            return 0;
        }
        if(Math.abs(pos.getCol()-target.pos.getCol()) > 1) { //Too far away
            return 0;
        }
        if(pos.getRow() == target.pos.getRow()) { //Moving in this row
            return 0;
        }
            return 1;
    }
}
