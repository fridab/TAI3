package model;

public class Room {
    private int size;
    //private int[][] room;

    public Room(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean wallEncountered(Coordinate pos, int heading) {
        if (pos.getRow() == 0 && heading == Robot.NORTH) {
            return true;
        }

        if (pos.getRow() == size - 1 && heading == Robot.SOUTH) {

            return true;
        }
        if (pos.getCol() == 0 && heading == Robot.WEST) {
            return true;
        }

        if (pos.getCol() == size - 1 && heading == Robot.EAST) {
            return true;
        }
        return false;

    }

    public int[][] getLS(int[] pos) {
        int[][] lstemp = new int[8][2];
        int rowCount = 0;
        int size = getSize();
        for (int row = -1; row <= 1; row++) {
            for (int col = -1; col <= 1; col++) {
                //if current pos or outside room, don't store row and col
                if ((row == 0 && col == 0) || pos[0] + row < 0 || pos[1] + col < 0 || pos[0] + row >= size || pos[1] + col >= size) {
                    continue;
                }
                lstemp[rowCount][0] = pos[0] + row;
                lstemp[rowCount][1] = pos[1] + col;
                rowCount++;
            }
        }

        int[][] ls = new int[rowCount][2];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < 2; col++) {
                ls[row][col] = lstemp[row][col];
            }
        }

        return ls;
    }


    public int[][] getLS2(int[] pos) {
        int[][] ls2temp = new int[16][2];
        int rowCount = 0;
        int size = getSize();
        for (int row = -2; row <= 2; row++) {
            for (int col = -2; col <= 2; col++) {

                //If not in outer ring
                if ((row > -2 && row < 2) && (col > -2 && col < 2)) {
                    continue;
                }
                if ((row == -1 && col == -1) || (row == -1 && col == 0) || (row == -1 && col == 1) ||
                        (row == 0 && col == -1) || (row == 0 && col == 0) || (row == 0 && col == 1) ||
                        (row == 1 && col == -1) || (row == 1 && col == 0) || (row == 1 && col == 1)) {
                    continue;
                }
                //If outside room
                if (pos[0] + row < 0 || pos[1] + col < 0 || pos[0] + row >= size || pos[1] + col >= size) {
                    continue;
                }

                ls2temp[rowCount][0] = pos[0] + row;
                ls2temp[rowCount][1] = pos[1] + col;
                rowCount++;
            }
        }

        int[][] ls2 = new int[rowCount][2];
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < 2; col++) {
                ls2[row][col] = ls2temp[row][col];
            }
        }

        return ls2;
    }

}
