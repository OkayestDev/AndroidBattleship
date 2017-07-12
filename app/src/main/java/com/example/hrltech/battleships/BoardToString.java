package com.example.hrltech.battleships;

public class BoardToString {
    private Player player;

    public BoardToString(Player player) {
        this.player = player;
    }

    public String boardToString(char[][] board, boolean showCursor) {
        String result = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (player.isCurrentLocation(i, j) && showCursor) {
                    result += "X ";
                }
                else {
                    result += board[i][j] + " ";
                }
            }
            result += "\n";
        }
        return result;
    }
}
