package com.mygdx.chess;

public class Pawn {
    private King k = new King();
    public boolean pawn_moved;
    public boolean inPromo;

    public Pawn() {
        inPromo = false;
    }

    public void getValidMoves(int x, int y, int[][] board, Player player, boolean[][] own_taken,
                              boolean[][] opp_taken, boolean[][] valid, boolean[][] white_en_passant,
                              boolean[][] black_en_passant) {
        own_taken[y][x] = false;
        k.scanForCheck(board, player, own_taken, opp_taken);
        own_taken[y][x] = true;

        if (k.isChecked) { // checking for pins
            checkPawn(y, player);

            // determine special 2 valid squares for the first move
            if (!pawn_moved) {
                if (player.turn == 'w') {
                    if (!own_taken[y + 1][x] && !own_taken[y + 2][x] && !opp_taken[y + 1][x] && !opp_taken[y + 2][x]) {
                        if (k.king_seen[y + 2][x]) valid[y + 2][x] = true;
                    }
                } else {
                    if (!own_taken[y - 1][x] && !own_taken[y - 2][x] && !opp_taken[y - 1][x] && !opp_taken[y - 2][x]) {
                        if (k.king_seen[y - 2][x]) valid[y - 2][x] = true;
                    }
                }
            }
            // checks if moving one square forward is valid
            if (player.turn == 'w') {
                if (y + 1 < 8 && !own_taken[y + 1][x] && !opp_taken[y + 1][x]) {
                    if (k.king_seen[y + 1][x]) valid[y + 1][x] = true;
                }
            } else {
                if (y - 1 >= 0 && !own_taken[y - 1][x] && !opp_taken[y - 1][x]) {
                    if (k.king_seen[y - 1][x]) valid[y - 1][x] = true;
                }
            }
            // check for possible diagonal captures
            if (player.turn == 'w') {
                // right
                if (x + 1 < 8 && y + 1 < 8 && opp_taken[y + 1][x + 1]) {
                    if (k.king_seen[y + 1][x + 1]) valid[y + 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y + 1 < 8 && opp_taken[y + 1][x - 1]) {
                    if (k.king_seen[y + 1][x - 1])  valid[y + 1][x - 1] = true;
                }
            } else {
                // right
                if (x + 1 < 8 && y - 1 >= 0 && opp_taken[y - 1][x + 1]) {
                    if (k.king_seen[y - 1][x + 1]) valid[y - 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y - 1 >= 0 && opp_taken[y - 1][x - 1]) {
                    if (k.king_seen[y - 1][x + 1]) valid[y - 1][x - 1] = true;
                }
            }
        } else {
            checkPawn(y, player);

            // determine special 2 valid squares for the first move
            if (!pawn_moved) {
                if (player.turn == 'w') {
                    if (!own_taken[y + 1][x] && !own_taken[y + 2][x] && !opp_taken[y + 1][x] && !opp_taken[y + 2][x]) {
                        valid[y + 2][x] = true;
                    }
                } else {
                    if (!own_taken[y - 1][x] && !own_taken[y - 2][x] && !opp_taken[y - 1][x] && !opp_taken[y - 2][x]) {
                        valid[y - 2][x] = true;
                    }
                }
            }
            // checks if moving one square forward is valid
            if (player.turn == 'w') {
                if (y + 1 < 8 && !own_taken[y + 1][x] && !opp_taken[y + 1][x]) {
                    valid[y + 1][x] = true;
                }
            } else {
                if (y - 1 >= 0 && !own_taken[y - 1][x] && !opp_taken[y - 1][x]) {
                    valid[y - 1][x] = true;
                }
            }
            // check for possible diagonal captures
            if (player.turn == 'w') {
                // right
                if (x + 1 < 8 && y + 1 < 8 && opp_taken[y + 1][x + 1]) {
                    valid[y + 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y + 1 < 8 && opp_taken[y + 1][x - 1]) {
                    valid[y + 1][x - 1] = true;
                }
            } else {
                // right
                if (x + 1 < 8 && y - 1 >= 0 && opp_taken[y - 1][x + 1]) {
                    valid[y - 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y - 1 >= 0 && opp_taken[y - 1][x - 1]) {
                    valid[y - 1][x - 1] = true;
                }
            }
            // check for en passant
            if (player.turn == 'w') {
                // right
                if (x + 1 < 8 && y + 1 < 8 && !own_taken[y + 1][x + 1]) {
                    if (black_en_passant[y + 1][x + 1]) valid[y + 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y + 1 < 8 && !own_taken[y + 1][x - 1]) {
                    if (black_en_passant[y + 1][x - 1]) valid[y + 1][x - 1] = true;
                }
            } else {
                // right
                if (x + 1 < 8 && y - 1 >= 0 && !own_taken[y - 1][x + 1]) {
                    if (white_en_passant[y - 1][x + 1]) valid[y - 1][x + 1] = true;
                }
                // left
                if (x - 1 >= 0 && y - 1 >= 0 && !own_taken[y - 1][x - 1]) {
                    if (white_en_passant[y - 1][x - 1]) valid[y - 1][x - 1] = true;
                }
            }
        }
    }

    // moves are only valid in check when the move can block the king to stop the check
    public void getValidCheckMoves(int x, int y, Player player,  boolean[][] own_taken,
                                   boolean[][] opp_taken, boolean[][] valid,
                                   boolean[][] king_seen) {
        checkPawn(y, player);

        // determine special 2 valid squares for the first move
        if (!pawn_moved) {
            if (player.turn == 'w') {
                if (!own_taken[y + 1][x] && !own_taken[y + 2][x] && !opp_taken[y + 1][x] && !opp_taken[y + 2][x]) {
                    if (king_seen[y + 2][x]) valid[y + 2][x] = true;
                }
            } else {
                if (!own_taken[y - 1][x] && !own_taken[y - 2][x] && !opp_taken[y - 1][x] && !opp_taken[y - 2][x]) {
                    if (king_seen[y - 2][x]) valid[y - 2][x] = true;
                }
            }
        }
        // checks if moving one square forward is valid
        if (player.turn == 'w') {
            if (y + 1 < 8 && !own_taken[y + 1][x] && !opp_taken[y + 1][x]) {
                if (king_seen[y + 1][x]) valid[y + 1][x] = true;
            }
        } else {
            if (y - 1 >= 0 && !own_taken[y - 1][x] && !opp_taken[y - 1][x]) {
                if (king_seen[y - 1][x]) valid[y - 1][x] = true;
            }
        }
        // check for possible diagonal captures
        if (player.turn == 'w') {
            // right
            if (x + 1 < 8 && y + 1 < 8 && opp_taken[y + 1][x + 1]) {
                if (king_seen[y + 1][x + 1]) valid[y + 1][x + 1] = true;
            }
            // left
            if (x - 1 >= 0 && y + 1 < 8 && opp_taken[y + 1][x - 1]) {
                if (king_seen[y + 1][x - 1]) valid[y + 1][x - 1] = true;
            }
        } else {
            // right
            if (x + 1 < 8 && y - 1 >= 0 && opp_taken[y - 1][x + 1]) {
                if (king_seen[y - 1][x + 1]) valid[y - 1][x + 1] = true;
            }
            // left
            if (x - 1 >= 0 && y - 1 >= 0 && opp_taken[y - 1][x - 1]) {
                if (king_seen[y - 1][x - 1]) valid[y - 1][x - 1] = true;
            }
        }
    }

    // checks if the pawn has moved yet or not
    public void checkPawn(int y, Player player) {
        if (player.turn == 'w') {
            if (y == 1) {
                pawn_moved = false;
            } else {
                pawn_moved = true;
            }
        } else {
            if (y == 6) {
                pawn_moved = false;
            } else {
                pawn_moved = true;
            }
        }
    }

    // checks if a pawn will get promoted
    public void checkPromo(int y, Player player) {
        if (player.turn == 'w') {
            if (y == 7) inPromo = true;
        } else if (player.turn == 'b') {
            if (y == 0) inPromo = true;
        } else {
            inPromo = false;
        }
    }
}
