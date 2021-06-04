package com.mygdx.chess;

public class Knight {
    private King k = new King();

    public void getValidMoves(int x, int y, int[][] board, Player player, boolean[][] own_taken,
                              boolean[][] opp_taken, boolean[][] valid) {
        own_taken[y][x] = false;
        k.scanForCheck(board, player, own_taken, opp_taken);
        own_taken[y][x] = true;

        if (k.isChecked) { // checking for pins
            // check if up 2 left 1 is valid
            if (x - 1 >= 0 && y + 2 < 8 && !own_taken[y + 2][x - 1]) {
                if (k.king_seen[y + 2][x - 1]) valid[y + 2][x - 1] = true;
            }
            // check if up 2 right 1 is valid
            if (x + 1 < 8 && y + 2 < 8 && !own_taken[y + 2][x + 1]) {
                if (k.king_seen[y + 2][x + 1]) valid[y + 2][x + 1] = true;
            }
            // check if down 2 left 1 is valid
            if (x - 1 >= 0 && y - 2 >= 0 && !own_taken[y - 2][x - 1]) {
                if (k.king_seen[y - 2][x - 1]) valid[y - 2][x - 1] = true;
            }
            // check if down 2 right 1 is valid
            if (x + 1 < 8 && y - 2 >= 0 && !own_taken[y - 2][x + 1]) {
                if (k.king_seen[y - 2][x + 1]) valid[y - 2][x + 1] = true;
            }
            // check if up 1 left 2 is valid
            if (x - 2 >= 0 && y + 1 < 8 && !own_taken[y + 1][x - 2]) {
                if (k.king_seen[y + 1][x - 2]) valid[y + 1][x - 2] = true;
            }
            // check if up 1 right 2 is valid
            if (x + 2 < 8 && y + 1 < 8 && !own_taken[y + 1][x + 2]) {
                if (k.king_seen[y + 1][x + 2]) valid[y + 1][x + 2] = true;
            }
            // check if down 1 left 2 is valid
            if (x - 2 >= 0 && y - 1 >= 0 && !own_taken[y - 1][x - 2]) {
                if (k.king_seen[y - 1][x - 2]) valid[y - 1][x - 2] = true;
            }
            // check if down 1 right 2 is valid
            if (x + 2 < 8 && y - 1 >= 0 && !own_taken[y - 1][x + 2]) {
                if (k.king_seen[y - 1][x + 2]) valid[y - 1][x + 2] = true;
            }
        } else {
            // check if up 2 left 1 is valid
            if (x - 1 >= 0 && y + 2 < 8 && !own_taken[y + 2][x - 1]) {
                valid[y + 2][x - 1] = true;
            }
            // check if up 2 right 1 is valid
            if (x + 1 < 8 && y + 2 < 8 && !own_taken[y + 2][x + 1]) {
                valid[y + 2][x + 1] = true;
            }
            // check if down 2 left 1 is valid
            if (x - 1 >= 0 && y - 2 >= 0 && !own_taken[y - 2][x - 1]) {
                valid[y - 2][x - 1] = true;
            }
            // check if down 2 right 1 is valid
            if (x + 1 < 8 && y - 2 >= 0 && !own_taken[y - 2][x + 1]) {
                valid[y - 2][x + 1] = true;
            }
            // check if up 1 left 2 is valid
            if (x - 2 >= 0 && y + 1 < 8 && !own_taken[y + 1][x - 2]) {
                valid[y + 1][x - 2] = true;
            }
            // check if up 1 right 2 is valid
            if (x + 2 < 8 && y + 1 < 8 && !own_taken[y + 1][x + 2]) {
                valid[y + 1][x + 2] = true;
            }
            // check if down 1 left 2 is valid
            if (x - 2 >= 0 && y - 1 >= 0 && !own_taken[y - 1][x - 2]) {
                valid[y - 1][x - 2] = true;
            }
            // check if down 1 right 2 is valid
            if (x + 2 < 8 && y - 1 >= 0 && !own_taken[y - 1][x + 2]) {
                valid[y - 1][x + 2] = true;
            }
        }
    }

    public void getValidCheckMoves(int x, int y, boolean[][] own_taken, boolean[][] valid, boolean[][] king_seen) {
        // check if up 2 left 1 is valid
        if (x - 1 >= 0 && y + 2 < 8 && !own_taken[y + 2][x - 1]) {
            if (king_seen[y + 2][x - 1]) valid[y + 2][x - 1] = true;
        }
        // check if up 2 right 1 is valid
        if (x + 1 < 8 && y + 2 < 8 && !own_taken[y + 2][x + 1]) {
            if (king_seen[y + 2][x + 1]) valid[y + 2][x + 1] = true;
        }
        // check if down 2 left 1 is valid
        if (x - 1 >= 0 && y - 2 >= 0 && !own_taken[y - 2][x - 1]) {
            if (king_seen[y - 2][x - 1]) valid[y - 2][x - 1] = true;
        }
        // check if down 2 right 1 is valid
        if (x + 1 < 8 && y - 2 >= 0 && !own_taken[y - 2][x + 1]) {
            if (king_seen[y - 2][x + 1]) valid[y - 2][x + 1] = true;
        }
        // check if up 1 left 2 is valid
        if (x - 2 >= 0 && y + 1 < 8 && !own_taken[y + 1][x - 2]) {
            if (king_seen[y + 1][x - 2]) valid[y + 1][x - 2] = true;
        }
        // check if up 1 right 2 is valid
        if (x + 2 < 8 && y + 1 < 8 && !own_taken[y + 1][x + 2]) {
            if (king_seen[y + 1][x + 2]) valid[y + 1][x + 2] = true;
        }
        // check if down 1 left 2 is valid
        if (x - 2 >= 0 && y - 1 >= 0 && !own_taken[y - 1][x - 2]) {
            if (king_seen[y - 1][x - 2]) valid[y - 1][x - 2] = true;
        }
        // check if down 1 right 2 is valid
        if (x + 2 < 8 && y - 1 >= 0 && !own_taken[y - 1][x + 2]) {
            if (king_seen[y - 1][x + 2]) valid[y - 1][x + 2] = true;
        }
    }
}
