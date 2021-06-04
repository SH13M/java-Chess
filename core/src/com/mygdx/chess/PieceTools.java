package com.mygdx.chess;

public class PieceTools {

    // creates a 2d array that stores positions of the current player's pieces on the board
    public void getOwnPieceValues(int[][] board, Player player, boolean[][] own_taken) {
        clear2dArray(own_taken);
        if (player.turn == 'w') {
            for (int y=0; y<own_taken.length; y++) {
                for (int x=0; x<own_taken[0].length; x++) {
                    if (board[y][x] > 0) {
                        own_taken[y][x] = true;
                    }
                }
            }
        } else {
            for (int y=0; y<own_taken.length; y++) {
                for (int x=0; x<own_taken[0].length; x++) {
                    if (board[y][x] < 0) {
                        own_taken[y][x] = true;
                    }
                }
            }
        }
    }

    // creates a 2d array that stores positions of the opponent's pieces on the board
    public void getOpponentPieceValues(int[][] board, Player player, boolean[][] opp_taken) {
        clear2dArray(opp_taken);
        if (player.turn == 'w') {
            for (int y=0; y<opp_taken.length; y++) {
                for (int x=0; x<opp_taken[0].length; x++) {
                    if (board[y][x] < 0) {
                        opp_taken[y][x] = true;
                    }
                }
            }
        } else {
            for (int y=0; y<opp_taken.length; y++) {
                for (int x=0; x<opp_taken[0].length; x++) {
                    if (board[y][x] > 0) {
                        opp_taken[y][x] = true;
                    }
                }
            }
        }
    }

    // resets the boolean 2d array that is passed through the method
    public void clear2dArray(boolean[][] array) {
        for (int y=0; y<array.length; y++) {
            for (int x=0; x<array[0].length; x++) {
                array[y][x] = false;
            }
        }
    }
}
