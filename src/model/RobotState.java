package model;

import static model.Direction.*;

public class RobotState {
    private int heading;
    private Coordinate pos;
    private Direction dirH;
    private RobotState[] possibleMoves;

    public RobotState(Coordinate pos, int heading) {
        this.pos = pos;
        this.heading = heading;
        switch (heading) {
            case 0:
                dirH = Direction.NORTH;
                break;
            case 1:
                dirH = Direction.EAST;
                break;
            case 2:
                dirH = Direction.SOUTH;
                break;
            case 3:
                dirH = Direction.WEST;
                break;
        }
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
//    public double getTo(RobotState target) {
//        if (this.equals(target)) { //It will always move
//            return 0;
//        }
//        if (Math.abs(pos.getRow() - target.pos.getRow()) > 1) { //Too far away
//            return 0;
//        }
//        if (Math.abs(pos.getCol() - target.pos.getCol()) > 1) { //Too far away
//            return 0;
//        }
//        if (pos.getRow() == target.pos.getRow()) { //Moving in this row
//            if (pos.getCol() < target.pos.getCol()) { //Moving east
//                if (target.heading == 1) {
//                    return heading == 1 ? 0.7 : 0.3;
//                }
//                return 0;
//            }
//            if (pos.getCol() > target.pos.getCol()) { //Moving west
//                if (target.heading == 3) {
//                    return heading == 3 ? 0.7 : 0.3;
//                }
//                return 0;
//
//            }
//        }
//        if (pos.getCol() == target.pos.getCol()) { //Moving in this col
//            if (pos.getRow() < target.pos.getRow()) { //Moving south
//                if (target.heading == 2) {
//                    return heading == 2 ? 0.7 : 0.3;
//                }
//                return 0;
//            }
//            if (pos.getRow() > target.pos.getRow()) { //Moving north
//                if (target.heading == 0) {
//                    return heading == 0 ? 0.7 : 0.3;
//                }
//                    return 0;
//            }
//        }
//
//        return 0;
//    }

    /**
     * @param target
     * @return P of getting to target from this state in room r
     */
    public double getTo(RobotState target, Room r) {
        int dir;
        int dirLeft;
        int dirRight;
        int dirUnder;

        if (target.heading == 0) {
            dir = Robot.NORTH;
            dirLeft = Robot.WEST;
            dirRight = Robot.EAST;

        } else if (target.heading == 1) {
            dir = Robot.EAST;
            dirLeft = Robot.NORTH;
            dirRight = Robot.SOUTH;

        } else if (target.heading == 2) {
            dir = Robot.SOUTH;
            dirLeft = Robot.EAST;
            dirRight = Robot.WEST;

        } else if (target.heading == 3) {
            dir = Robot.WEST;
            dirLeft = Robot.SOUTH;
            dirRight = Robot.NORTH;

        } else {
            dir = -1;
            dirLeft = -1;
            dirRight = -1;

        }

        if (!isPossibleMove(target, dirLeft, dirRight)) { //It will always move
            return 0;
        }

        if (r.wallEncountered(target.pos, dir)) {

            if (r.wallEncountered(target.pos, dirLeft)) {
                if (target.heading == dirRight) {
                    return 0.5;
                }
            } else if (r.wallEncountered(target.pos, dirRight)) {

            } else {

            }

        } else {

            return heading == dir ? 0.7 : 0.1;
        }
        return 0;
    }


    private boolean isPossibleMove(RobotState target, int dirLeft, int dirRight) {
        if (this.equals(target) || Math.abs(pos.getRow() - target.pos.getRow()) > 1 || Math.abs(pos.getCol() - target.pos.getCol()) > 1) {
            return false;
        } else if (true) {

        }
        return true;
    }

    private RobotState[] getPossibleMoves() {
        if(possibleMoves != null) {
            return possibleMoves;
        }
            return null;

        //Koordinat ovanför
        //Koordinat nedanför
        //Koordinat till vänster
        //Koordinate till höger
    }
}
