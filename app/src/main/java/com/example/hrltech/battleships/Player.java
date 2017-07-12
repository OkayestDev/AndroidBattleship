package com.example.hrltech.battleships;

public class Player {
    private char[][] firingBoard;
    private char[][] shipBoard;
    private Ship[] ships;
    private int health;
    private Pair currentLocation;
    private int currentBoatCount;

    public Player() {
        firingBoard = new char[9][9];
        shipBoard = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                firingBoard[i][j] = '~';
                shipBoard[i][j] = '~';
            }
        }
        currentLocation = new Pair(0, 0);
        health = 17;
        currentBoatCount = 4;
        buildShipsArray();
    }

    public Pair getCurrentLocation() {
        return currentLocation;
    }

    public int getCurrentBoatCount() {
        return currentBoatCount;
    }

    public void buildShipsArray() {
        ships = new Ship[5];
        ships[4] = new Ship("Carrier", 5);
        ships[3] = new Ship("Battleship", 4);
        ships[2] = new Ship("Cruiser", 3);
        ships[1] = new Ship("Submarine", 3);
        ships[0] = new Ship("Destroyer", 2);
    }

    public void changeCurrentLocation(char change) {
        if (change == 'u') {
            currentLocation.moveUp();
        }
        else if (change == 'd') {
            currentLocation.moveDown();
        }
        else if (change == 'l') {
            currentLocation.moveLeft();
        }
        else if (change == 'r') {
            currentLocation.moveRight();
        }
    }

    public boolean isCurrentLocation(int first, int second) {
        return currentLocation.isSamePair(first, second);
    }

    public void setCurrentLocation(int first, int second) {
        currentLocation = new Pair(first, second);
    }

    public boolean isHit(int x, int y) {
        if (shipBoard[x][y] == 'B') {
            health--;
            return true;
        }
        return false;
    }

    public boolean hasAlreadyBeenShot(int x, int y) {
        if (shipBoard[x][y] == 'M' || shipBoard[x][y] == 'H') {
            return true;
        }
        return false;
    }

    public boolean hasHealth() {
        return health != 0;
    }

    public void setFiringBoardValue(int x, int y, char c) {
        firingBoard[x][y] = c;
    }

    public void setShipBoardValue(int x, int y, char c) {
        shipBoard[x][y] = c;
    }

    public char[][] getFiringBoard() {
        return firingBoard;
    }

    public char[][] getShipBoard() {
        return shipBoard;
    }

    private void copyBoard(char[][] from, char[][] to) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                to[i][j] = from[i][j];
            }
        }
    }

    public boolean setCurrentBoatAtCurrentLocation() {
        char[][] resetBoard = new char[9][9];
        copyBoard(shipBoard, resetBoard);
        Ship currentShip = ships[currentBoatCount];
        currentShip.setStartingPosition(currentLocation);
        int first = currentLocation.getFirst();
        int second = currentLocation.getSecond();
        if (currentShip.getOrientation() == 'v') {
            for (int i = 0; i < currentShip.getHealth(); i++) {
                if (first + i <= 8 && shipBoard[first + i][second] != 'B') {
                    shipBoard[first + i][second] = 'B';
                }
                else {
                    copyBoard(resetBoard, shipBoard);
                    return false;
                }
            }
        }
        else {
            for (int i = 0; i < currentShip.getHealth(); i++) {
                if (second + i <= 8 && shipBoard[first][second + i] != 'B') {
                    shipBoard[first][second + i] = 'B';
                }
                else {
                    copyBoard(resetBoard, shipBoard);
                    return false;
                }
            }
        }
        return true;
    }

    public void decreaseBoatCount() {
        currentBoatCount--;
    }

    public void changeCurrentBoatOrientation() {
        ships[currentBoatCount].changeOrientation();
    }

    public String getCurrentBoatInfo() {
        Ship currentShip = ships[currentBoatCount];
        String result = "Placing " + currentShip.getName();
        if (currentShip.getOrientation() == 'v') {
            result += " vertically";
        }
        else {
            result += " horizontally";
        }
        result += " with a size of " + ships[currentBoatCount].getHealth();
        return result;
    }
}
