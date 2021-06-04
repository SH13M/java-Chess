package com.mygdx.chess;

public class Rook {
    private King k = new King();

    public void getValidMoves(int x, int y, int[][] board, Player player, boolean[][] own_taken,
                              boolean[][] opp_taken, boolean[][] valid) {
        own_taken[y][x] = false;
        k.scanForCheck(board, player, own_taken, opp_taken);
        own_taken[y][x] = true;

        if (k.isChecked) { // checking for pins
            int scanX, scanY;
            boolean blocked;

            // check valid moves above
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8) { // marks squares valid until they get blocked
                scanY++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8) { // marks first enemy piece seen as valid
                scanY++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves below
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0) { // marks squares valid until they get blocked
                scanY--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0) { // marks first enemy piece seen as valid
                scanY--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves to the left
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanX + 1 < 8) { // marks squares valid until they get blocked
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
            while (scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves to the right
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanX - 1 >= 0) { // marks squares valid until they get blocked
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
            while (scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanX--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    if (k.king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                    break;
                }
            }
        } else {
            int scanX, scanY;
            boolean blocked;

            // check valid moves above
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY + 1 < 8) { // marks squares valid until they get blocked
                scanY++;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY + 1 < 8) { // marks first enemy piece seen as valid
                scanY++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves below
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanY - 1 >= 0) { // marks squares valid until they get blocked
                scanY--;
                if (own_taken[scanY][scanX]) {
                    blocked = true;
                }
                if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                    valid[scanY][scanX] = true;
                } else break;
            }
            scanX = x;
            scanY = y;
            while (scanY - 1 >= 0) { // marks first enemy piece seen as valid
                scanY--;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves to the left
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanX + 1 < 8) { // marks squares valid until they get blocked
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
            while (scanX + 1 < 8) { // marks first enemy piece seen as valid
                scanX++;
                if (opp_taken[scanY][scanX] && !blocked) {
                    valid[scanY][scanX] = true;
                    break;
                }
            }
            // check valid moves to the right
            scanX = x;
            scanY = y;
            blocked = false;
            while (scanX - 1 >= 0) { // marks squares valid until they get blocked
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
            while (scanX - 1 >= 0) { // marks first enemy piece seen as valid
                scanX--;
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

        // check valid moves above
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY + 1 < 8) { // marks squares valid until they get blocked
            scanY++;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY + 1 < 8) { // marks first enemy piece seen as valid
            scanY++;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves below
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanY - 1 >= 0) { // marks squares valid until they get blocked
            scanY--;
            if (own_taken[scanY][scanX]) {
                blocked = true;
            }
            if (!own_taken[scanY][scanX] && !opp_taken[scanY][scanX]) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
            } else break;
        }
        scanX = x;
        scanY = y;
        while (scanY - 1 >= 0) { // marks first enemy piece seen as valid
            scanY--;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves to the left
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanX + 1 < 8) { // marks squares valid until they get blocked
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
        while (scanX + 1 < 8) { // marks first enemy piece seen as valid
            scanX++;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
        // check valid moves to the right
        scanX = x;
        scanY = y;
        blocked = false;
        while (scanX - 1 >= 0) { // marks squares valid until they get blocked
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
        while (scanX - 1 >= 0) { // marks first enemy piece seen as valid
            scanX--;
            if (opp_taken[scanY][scanX] && !blocked) {
                if (king_seen[scanY][scanX]) valid[scanY][scanX] = true;
                break;
            }
        }
    }
}
