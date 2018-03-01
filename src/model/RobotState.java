package model;

import static model.Direction.*;

public class RobotState {
    private int heading;
    private Coordinate pos;
    private Direction dirH;

    public RobotState(Coordinate pos, int heading) {
        this.pos = pos;
        this.heading = heading;
        switch(heading) {
            case 0:
                dirH=Direction.NORTH;
                break;
            case 1:
                dirH=Direction.EAST;
                break;
            case 2:
                dirH=Direction.SOUTH;
                break;
            case 3:
                dirH=Direction.WEST;
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
        if (this.equals(target)) { //It will always move
            return 0;
        }
        if (Math.abs(pos.getRow() - target.pos.getRow()) > 1) { //Too far away
            return 0;
        }
        if (Math.abs(pos.getCol() - target.pos.getCol()) > 1) { //Too far away
            return 0;
        }

        Direction dir;
        Direction dirLeft;
        Direction dirRight;

        if (target.heading == 0) {
            dir = NORTH;
            dirLeft = WEST;
            dirRight = EAST;

        } else if (target.heading == 1) {
            dir = EAST;
            dirLeft = NORTH;
            dirRight = SOUTH;

        } else if (target.heading == 2) {
            dir = SOUTH;
            dirLeft = EAST;
            dirRight = WEST;

        } else if (target.heading == 3) {
            dir = WEST;
            dirLeft = SOUTH;
            dirRight = NORTH;

        } else {
            dir = null;
            dirLeft = null;
            dirRight = null;

        }

        if (r.wallEncountered(target.pos, dir)) {
            if (r.wallEncountered(target.pos, dirLeft) || r.wallEncountered(target.pos, dirRight)) {

            } else {

            }

        } else {
               // return heading == dir ? 0.7 : 0.1;
            }
            return 0;
        }


    public double getTo(RobotState target, Robot r, Room room) {
        if (this.equals(target)) { //It will always move
            return 0;
        }
        if (Math.abs(pos.getRow() - target.pos.getRow()) > 1) { //Too far away
        return 0;
    }
        if (Math.abs(pos.getCol() - target.pos.getCol()) > 1) { //Too far away
        return 0;
    }
        int[][] ls = r.getLS(pos.getPosition());
        int possibleMoves=0;
        switch(ls[0].length) {
            case 3:
                possibleMoves = 2;
                break;
            case 5:
                possibleMoves = 3;
                break;
            case 8:
                possibleMoves = 4;
                break;
        }
        if(room.wallEncountered(pos, dirH)) {
            double prob = (double)1/possibleMoves;
            if (pos.getRow() == target.pos.getRow()) { //Moving in this row
                if (pos.getCol() < target.pos.getCol()) { //Moving east
                    if (target.heading == 1) {
                        return prob;
                    }
                    return 0;
                }
                if (pos.getCol() > target.pos.getCol()) { //Moving west
                    if (target.heading == 3) {
                        return prob;
                    }
                    return 0;

                }
            }
            if (pos.getCol() == target.pos.getCol()) { //Moving in this col
                if (pos.getRow() < target.pos.getRow()) { //Moving south
                    if (target.heading == 2) {
                        return prob;
                    }
                    return 0;
                }
                if (pos.getRow() > target.pos.getRow()) { //Moving north
                    if (target.heading == 0) {
                        return prob;
                    }
                    return 0;
                }
            }
        }
        //Wall not encountered
        double prob = 1/(double)(possibleMoves-1);
        if (pos.getRow() == target.pos.getRow()) { //Moving in this row
            if (pos.getCol() < target.pos.getCol()) { //Moving east
                if (target.heading == 1) {
                    return heading == 1 ? 0.7 : prob;
                }
                return 0;
            }
            if (pos.getCol() > target.pos.getCol()) { //Moving west
                if (target.heading == 3) {
                    return heading == 3 ? 0.7 : prob;
                }
                return 0;

            }
        }
        if (pos.getCol() == target.pos.getCol()) { //Moving in this col
            if (pos.getRow() < target.pos.getRow()) { //Moving south
                if (target.heading == 2) {
                    return heading == 2 ? 0.7 : prob;
                }
                return 0;
            }
            if (pos.getRow() > target.pos.getRow()) { //Moving north
                if (target.heading == 0) {
                    return heading == 0 ? 0.7 : prob;
                }
                return 0;
            }
        }
        return 3;
    }
}

