package com.example.hrltech.battleships;

public class Pair {
    private int first;
    private int second;

    public Pair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    public int getFirst() {
       return first;
    }

    public int getSecond() {
        return second;
    }

    public boolean isSamePair(int first, int second) {
        return this.first == first && this.second == second;
    }

    public void moveLeft() {
        if (second - 1 >= 0) {
            second--;
        }
    }

    public void moveRight() {
        if (second + 1 <= 8) {
            second++;
        }
    }

    public void moveUp() {
        if (first - 1 >= 0) {
            first--;
        }
    }

    public void moveDown() {
        if (first + 1 <= 8) {
            first++;
        }
    }
}
