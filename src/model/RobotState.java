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

//    int dir;
//    int dirLeft;
//    int dirRight;
//    int dirUnder;
//
//        if (target.heading == 0) {
//        dir = Robot.NORTH;
//        dirLeft = Robot.WEST;
//        dirRight = Robot.EAST;
//        dirUnder = Robot.SOUTH;
//
//    } else if (target.heading == 1) {
//        dir = Robot.EAST;
//        dirLeft = Robot.NORTH;
//        dirRight = Robot.SOUTH;
//        dirUnder = Robot.EAST;
//
//    } else if (target.heading == 2) {
//        dir = Robot.SOUTH;
//        dirLeft = Robot.EAST;
//        dirRight = Robot.WEST;
//        dirUnder = Robot.NORTH;
//
//    } else if (target.heading == 3) {
//        dir = Robot.WEST;
//        dirLeft = Robot.SOUTH;
//        dirRight = Robot.NORTH;
//        dirUnder = Robot.EAST;
//
//    } else {
//        dir = -1;
//        dirLeft = -1;
//        dirRight = -1;
//        dirUnder = -1;
//
//    }

    /**
     * @param target
     * @return P of getting to target from this state in room r
     */
    public double getTo(RobotState target, Room r) {
        RobotState[] pos = getPossMoves(this, r);

        if (pos != null && contain(target, pos)) {
            if (target.heading == this.heading) {
                return 0.7;
            }
            boolean flag = false;
            for (int i = 0; i < pos.length; i++) {
                if (pos[i].heading == this.heading) {
                    flag = true;
                }
            }
            if (flag) {
                return 0.3 / (pos.length - 1);
            } else {
                return (double) 1 / pos.length;
            }

        }
        return 0;
    }


    private boolean contain(RobotState target, RobotState[] pos) {
        for (int i = 0; i < pos.length; i++) {
            if (pos[i].equals(target)) {
                return true;
            }
        }
        return false;

    }

    private boolean isPossibleMove(RobotState target) {
        if (this.equals(target) || Math.abs(pos.getRow() - target.pos.getRow()) > 1 || Math.abs(pos.getCol() - target.pos.getCol()) > 1) {
            return false;
        }
        return true;
    }

    private RobotState[] getPossibleMoves(int roomsize) {
        if (possibleMoves != null) {
            return possibleMoves;
        }
        int index = 0;
        RobotState[] temp = new RobotState[4];
        Coordinate copy = new Coordinate(pos.getRow(), pos.getCol());
        copy.up();
        if (validPos(copy, roomsize)) {
            RobotState valid = new RobotState(copy, 0);
            temp[index] = valid;
            index++;
        }
        copy.down();
        copy.down();
        if (validPos(copy, roomsize)) {
            RobotState valid = new RobotState(copy, 2);
            temp[index] = valid;
            index++;
        }
        copy.up();
        copy.left();
        if (validPos(copy, roomsize)) {
            RobotState valid = new RobotState(copy, 3);
            temp[index] = valid;
            index++;
        }
        copy.right();
        copy.right();
        if (validPos(copy, roomsize)) {
            RobotState valid = new RobotState(copy, 2);
            temp[index] = valid;
            index++;
        }
        possibleMoves = new RobotState[index];
        for (int i = 0; i < index; i++) {
            possibleMoves[i] = temp[i];
        }
        return possibleMoves;
    }

    private RobotState[] getPossMoves(RobotState target, Room r) {
        int forward = target.heading;
        int left = getHeadingLeft(target);
        int right = getHeadingRight(target);
        int backward = getHeadingDown(target);
        RobotState[] temp;

        if (hasWallFL(target, r)) {
            return getWallFL(target);
        } else if (hasWallFR(target, r)) {
            return getWallFR(target);
        } else if (hasWallBL(target, r)) {
            return getWallBL(target);
        } else if (hasWallBR(target, r)) {
            return getWallBR(target);
        } else if (hasWallF(target, r)) {
            return getWallF(target);
        } else if (hasWallB(target, r)) {
            return getWallB(target);
        } else if (hasWallLeft(target, r)) {
            return getWallLeft(target);
        } else if (hasWallRight(target, r)) {
            return getWallRight(target);
        } else {
            return getElse(target);
        }

    }

    private boolean hasWallF(RobotState target, Room r) {
        return r.wallEncountered(target.pos, target.heading);
    }

    private boolean hasWallFL(RobotState target, Room r) {
        int dirLeft;

        if (target.heading == 0) {
            dirLeft = Robot.WEST;
        } else if (target.heading == 1) {
            dirLeft = Robot.NORTH;
        } else if (target.heading == 2) {
            dirLeft = Robot.EAST;
        } else if (target.heading == 3) {
            dirLeft = Robot.SOUTH;
        } else {
            dirLeft = -1;
        }
        return r.wallEncountered(target.pos, target.heading) && r.wallEncountered(target.pos, dirLeft);
    }

    private boolean hasWallFR(RobotState target, Room r) {
        int dirRight;

        if (target.heading == 0) {
            dirRight = Robot.EAST;
        } else if (target.heading == 1) {
            dirRight = Robot.SOUTH;
        } else if (target.heading == 2) {
            dirRight = Robot.WEST;
        } else if (target.heading == 3) {
            dirRight = Robot.NORTH;
        } else {
            dirRight = -1;
        }
        return r.wallEncountered(target.pos, target.heading) && r.wallEncountered(target.pos, dirRight);
    }

    private boolean hasWallBL(RobotState target, Room r) {
        int dirLeft;
        int dirBehind;

        if (target.heading == 0) {
            dirLeft = Robot.WEST;
            dirBehind = Robot.SOUTH;
        } else if (target.heading == 1) {
            dirLeft = Robot.NORTH;
            dirBehind = Robot.WEST;
        } else if (target.heading == 2) {
            dirLeft = Robot.EAST;
            dirBehind = Robot.NORTH;
        } else if (target.heading == 3) {
            dirLeft = Robot.SOUTH;
            dirBehind = Robot.EAST;
        } else {
            dirLeft = -1;
            dirBehind = 1;
        }
        return r.wallEncountered(target.pos, dirBehind) && r.wallEncountered(target.pos, dirLeft);
    }

    private boolean hasWallBR(RobotState target, Room r) {
        int dirRight;
        int dirBehind;

        if (target.heading == 0) {
            dirRight = Robot.EAST;
            dirBehind = Robot.SOUTH;
        } else if (target.heading == 1) {
            dirRight = Robot.SOUTH;
            dirBehind = Robot.WEST;
        } else if (target.heading == 2) {
            dirRight = Robot.WEST;
            dirBehind = Robot.NORTH;
        } else if (target.heading == 3) {
            dirRight = Robot.NORTH;
            dirBehind = Robot.EAST;
        } else {
            dirRight = -1;
            dirBehind = -1;
        }
        return r.wallEncountered(target.pos, dirBehind) && r.wallEncountered(target.pos, dirRight);
    }

    private boolean hasWallLeft(RobotState target, Room r) {
        int dirLeft;

        if (target.heading == 0) {
            dirLeft = Robot.WEST;
        } else if (target.heading == 1) {
            dirLeft = Robot.NORTH;
        } else if (target.heading == 2) {
            dirLeft = Robot.EAST;
        } else if (target.heading == 3) {
            dirLeft = Robot.SOUTH;
        } else {
            dirLeft = -1;
        }
        return r.wallEncountered(target.pos, dirLeft);
    }

    private boolean hasWallRight(RobotState target, Room r) {
        int dirRight;
        if (target.heading == 0) {
            dirRight = Robot.EAST;
        } else if (target.heading == 1) {
            dirRight = Robot.SOUTH;
        } else if (target.heading == 2) {
            dirRight = Robot.WEST;
        } else if (target.heading == 3) {
            dirRight = Robot.NORTH;
        } else {
            dirRight = -1;
        }
        return r.wallEncountered(target.pos, dirRight);
    }

    private boolean hasWallB(RobotState target, Room r) {
        int dirBehind;
        if (target.heading == 0) {
            dirBehind = Robot.SOUTH;
        } else if (target.heading == 1) {
            dirBehind = Robot.WEST;
        } else if (target.heading == 2) {
            dirBehind = Robot.NORTH;
        } else if (target.heading == 3) {
            dirBehind = Robot.EAST;
        } else {
            dirBehind = -1;
        }
        return r.wallEncountered(target.pos, dirBehind);
    }


    private int getHeadingLeft(RobotState target) {
        if (target.heading == 0) {
            return Robot.WEST;
        } else if (target.heading == 1) {
            return Robot.NORTH;
        } else if (target.heading == 2) {
            return Robot.EAST;
        } else if (target.heading == 3) {
            return Robot.SOUTH;
        } else {
            return -1;
        }
    }

    private int getHeadingRight(RobotState target) {
        if (target.heading == 0) {
            return Robot.EAST;
        } else if (target.heading == 1) {
            return Robot.SOUTH;
        } else if (target.heading == 2) {
            return Robot.WEST;
        } else if (target.heading == 3) {
            return Robot.NORTH;
        } else {
            return -1;
        }
    }

    private int getHeadingDown(RobotState target) {
        if (target.heading == 0) {
            return Robot.SOUTH;
        } else if (target.heading == 1) {
            return Robot.WEST;
        } else if (target.heading == 2) {
            return Robot.NORTH;
        } else if (target.heading == 3) {
            return Robot.EAST;
        } else {
            return -1;
        }
    }

    private RobotState[] getWallFL(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[2];
        if (forward == 0) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        }
        return temp;
    }

    private RobotState[] getWallFR(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[2];
        if (forward == 0) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;
        }
        return temp;
    }

    private RobotState[] getWallF(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[3];
        if (forward == 0) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;
        } else if (forward == 1) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() - 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.NORTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);
            return temp;
        }
        return temp;
    }

    private RobotState[] getWallBL(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[2];
        if (forward == 0) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() + 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.EAST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);
            return temp;

        }
        return temp;
    }

    private RobotState[] getWallBR(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[2];
        if (forward == 0) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() - 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.NORTH);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);
            return temp;
        }
        return temp;
    }

    private RobotState[] getWallB(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[3];
        if (forward == 0) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() - 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.NORTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);
            return temp;
        }
        return temp;
    }

    private RobotState[] getWallLeft(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[3];
        if (forward == 0) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

            int x3 = pos.getRow() - 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.NORTH);

            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() + 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.SOUTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);

            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

            int x3 = pos.getRow();
            int y3 = pos.getCol() - 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.WEST);

            return temp;
        }
        return temp;
    }

    private RobotState[] getWallRight(RobotState target) {
        int forward = target.heading;
        RobotState[] temp = new RobotState[3];
        if (forward == 0) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() - 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.WEST);

            int x3 = pos.getRow() - 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.NORTH);
            return temp;

        } else if (forward == 1) {
            int x1 = pos.getRow();
            int y1 = pos.getCol() - 1;
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.WEST);

            int x2 = pos.getRow() - 1;
            int y2 = pos.getCol();
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.NORTH);

            int x3 = pos.getRow();
            int y3 = pos.getCol() + 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.EAST);
            return temp;

        } else if (forward == 2) {
            int x1 = pos.getRow() - 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

            int x3 = pos.getRow() + 1;
            int y3 = pos.getCol();
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);
            return temp;

        } else if (forward == 3) {
            int x1 = pos.getRow() + 1;
            int y1 = pos.getCol();
            temp[0] = new RobotState(new Coordinate(x1, y1), Robot.SOUTH);
            int x2 = pos.getRow();
            int y2 = pos.getCol() + 1;
            temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);
            int x3 = pos.getRow();
            int y3 = pos.getCol() - 1;
            temp[2] = new RobotState(new Coordinate(x3, y3), Robot.WEST);
            return temp;
        }
        return temp;
    }

    private RobotState[] getElse(RobotState target) {
        RobotState[] temp = new RobotState[4];
        int x1 = pos.getRow() - 1;
        int y1 = pos.getCol();
        temp[0] = new RobotState(new Coordinate(x1, y1), Robot.NORTH);

        int x2 = pos.getRow();
        int y2 = pos.getCol() + 1;
        temp[1] = new RobotState(new Coordinate(x2, y2), Robot.EAST);

        int x3 = pos.getRow() + 1;
        int y3 = pos.getCol();
        temp[2] = new RobotState(new Coordinate(x3, y3), Robot.SOUTH);

        int x4 = pos.getRow();
        int y4 = pos.getCol() - 1;
        temp[3] = new RobotState(new Coordinate(x4, y4), Robot.WEST);
        return temp;
    }

    private boolean validPos(Coordinate c, int roomsize) {
        if (c.getRow() < 0 || c.getCol() < 0 || c.getRow() >= roomsize || c.getCol() >= roomsize) {
            return false;
        }
        return true;
    }
}
