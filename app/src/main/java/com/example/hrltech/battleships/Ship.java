package com.example.hrltech.battleships;

import com.example.hrltech.battleships.Pair;

public class Ship {
    private String name;
    private int health;
    private char orientation;
    private Pair startingPosition;

    public Ship(String name, int health) {
        this.name = name;
        this.health = health;
        orientation = 'v';
    }

    public void setStartingPosition(Pair currentLocation) {
        startingPosition = currentLocation;
    }

    public Pair getStartingPosition() {
        return startingPosition;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public char getOrientation() {
        return orientation;
    }

    public void changeOrientation() {
        if (orientation == 'v') {
            orientation = 'h';
        }
        else {
            orientation = 'v';
        }
    }
}
