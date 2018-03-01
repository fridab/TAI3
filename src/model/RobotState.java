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
        if (!(o instanceof RobotState)) {
            return false;
        }
        RobotState obj = (RobotState) o;
        return pos.equals(obj.pos) && heading == ((RobotState) o).heading;
    }

    public Coordinate getPosition() {
        return pos;
    }

    /**
     * @param target
     * @return P of getting to target from this state in room r
     */
    public double getTo(RobotState target, Room r) {
        if (this.equals(target)) { //It will always move
            return 0;
        }
        if (Math.abs(pos.getRow() - target.pos.getRow()) > 1) { //Too far away
            return 0;
        }
        if (Math.abs(pos.getCol() - target.pos.getCol()) > 1) { //Too far away
            return 0;
        }
        if (pos.getRow() == target.pos.getRow()) { //Moving in this row
            if (pos.getCol() < target.pos.getCol()) { //Moving east
                if (target.heading == 1) {
                    return heading == 1 ? 0.7 : 0.3;
                }
                return 0;
            }
            if (pos.getCol() > target.pos.getCol()) { //Moving west
                if (target.heading == 3) {
                    return heading == 3 ? 0.7 : 0.3;
                }
                return 0;

            }
        }
        if (pos.getCol() == target.pos.getCol()) { //Moving in this col
            if (pos.getRow() < target.pos.getRow()) { //Moving south
                if (target.heading == 2) {
                    return heading == 2 ? 0.7 : 0.3;
                }
                return 0;
            }
            if (pos.getRow() > target.pos.getRow()) { //Moving north
                if (target.heading == 0) {
                    return heading == 0 ? 0.7 : 0.3;
                }
                    return 0;
            }
        }

        return 0;
    }
}
