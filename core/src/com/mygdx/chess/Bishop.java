package com.mygdx.chess;

public class Bishop {
    private King k = new King();

    public void getValidMoves(int x, int y, int[][] board, Player player, boolean[][] own_taken,
                              boolean[][] opp_taken, boolean[][] valid) {
        own_taken[y][x] = false;
        k.scanForCheck(board, player, own_taken, opp_taken);
        own_taken[y][x] = true;

        if (k.isChecked) { // checking for pins
            int scanX, scanY;
            boolean blocked;

            // check valid moves up-right diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8 && scanX + 1 < 8) { // marks squares valid until they get blocked
                scanY++;
                scanX++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8 && scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanY++;
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves down-left diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks squares valid until they get blocked
                scanY--;
                scanX--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanY--;
                scanX--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves up-left diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks squares valid until they get blocked
                scanY++;
                scanX--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanY++;
                scanX--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves down-right diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks squares valid until they get blocked
                scanY--;
                scanX++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanY--;
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
        } else {
            int scanX, scanY;
            boolean blocked;

            // check valid moves up-right diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8 && scanX + 1 < 8) { // marks squares valid until they get blocked
                scanY++;
                scanX++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8 && scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanY++;
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves down-left diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks squares valid until they get blocked
                scanY--;
                scanX--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanY--;
                scanX--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves up-left diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks squares valid until they get blocked
                scanY++;
                scanX--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanY++;
                scanX--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves down-right diagonal
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks squares valid until they get blocked
                scanY--;
                scanX++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanY--;
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
        }
    }

    public void getValidCheckMoves(int x, int y, boolean[][] own_taken, boolean[][] opp_taken,
                                   boolean[][] valid, boolean[][] king_seen) {
        int scanX, scanY;
        boolean blocked;

        // check valid moves up-right diagonal
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY + 1 < 8 && scanX + 1 < 8) { // marks squares valid until they get blocked
            scanY++;
            scanX++;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY + 1 < 8 && scanX + 1 < 8) { // marks first enemy piece seen as valid
            scanY++;
            scanX++;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves down-left diagonal
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks squares valid until they get blocked
            scanY--;
            scanX--;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY - 1 >= 0 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
            scanY--;
            scanX--;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves up-left diagonal
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks squares valid until they get blocked
            scanY++;
            scanX--;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY + 1 < 8 && scanX - 1 >= 0) { // marks first enemy piece seen as valid
            scanY++;
            scanX--;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves down-right diagonal
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks squares valid until they get blocked
            scanY--;
            scanX++;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY - 1 >= 0 && scanX + 1 < 8) { // marks first enemy piece seen as valid
            scanY--;
            scanX++;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
    }
}
