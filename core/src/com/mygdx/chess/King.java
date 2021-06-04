package com.mygdx.chess;

public class King extends PieceTools {
    public int kingX, kingY;
    public boolean isChecked;
    public int numChecked;
    public final boolean[][] king_seen;
    private boolean pseudoChecked;

    public King() {
        pseudoChecked = false;
        king_seen = new boolean[8][8];
        isChecked = false;
        numChecked = 0;
    }

    public void getValidMoves(int x, int y, int[][] board, Player player,
                              boolean[][] own_taken, boolean[][] opp_taken, boolean[][] valid,
                              boolean wkm, boolean wkrm, boolean wqrm,
                              boolean bkm, boolean bkrm, boolean bqrm) {
        // top
        if (y + 1 < 8 && !own_taken[y + 1][x]) {
            scanForPseudoCheck(x, y+1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y + 1][x] = true;
        }
        // bottom
        if (y - 1 >= 0 && !own_taken[y - 1][x]) {
            scanForPseudoCheck(x, y-1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y - 1][x] = true;
        }
        // right
        if (x + 1 < 8 && !own_taken[y][x + 1]) {
            scanForPseudoCheck(x+1, y, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y][x + 1] = true;
        }
        // left
        if (x - 1 >= 0 && !own_taken[y][x - 1]) {
            scanForPseudoCheck(x-1, y, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y][x - 1] = true;
        }
        // top right
        if (y + 1 < 8 && x + 1 < 8 && !own_taken[y + 1][x + 1]) {
            scanForPseudoCheck(x+1, y+1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y + 1][x + 1] = true;
        }
        // top left
        if (y + 1 < 8 && x - 1 >= 0 && !own_taken[y + 1][x - 1]) {
            scanForPseudoCheck(x-1, y+1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y + 1][x - 1] = true;
        }
        // bottom right
        if (y - 1 >= 0 && x + 1 < 8 && !own_taken[y - 1][x + 1]) {
            scanForPseudoCheck(x+1, y-1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y - 1][x + 1] = true;
        }
        // bottom left
        if (y - 1 >= 0 && x - 1 >= 0 && !own_taken[y - 1][x - 1]) {
            scanForPseudoCheck(x-1, y-1, board, player, own_taken, opp_taken);
            if (!pseudoChecked) valid[y - 1][x - 1] = true;
        }


        // white king castles king side
        if (!isChecked && player.turn == 'w' && !wkm && !wkrm && board[0][5] == 0 && board[0][6] == 0) {
            scanForPseudoCheck(5,0, board, player, own_taken, opp_taken);
            if (!pseudoChecked) {
                scanForPseudoCheck(6,0, board, player, own_taken, opp_taken);
                if (!pseudoChecked) valid[0][6] = true;
            }
        }
        // white king castles queen side
        if (!isChecked && player.turn == 'w' && !wkm && !wqrm
                && board[0][1] == 0 && board[0][2] == 0 && board[0][3] == 0) {
            scanForPseudoCheck(1,0, board, player, own_taken, opp_taken);
            if (!pseudoChecked) {
                scanForPseudoCheck(2,0, board, player, own_taken, opp_taken);
                if (!pseudoChecked) {
                    scanForPseudoCheck(3,0, board, player, own_taken, opp_taken);
                    if (!pseudoChecked) valid[0][2] = true;
                }
            }
        }
        // black king castles king side
        if (!isChecked && player.turn == 'b' && !bkm && !bkrm && board[7][5] == 0 && board[7][6] == 0) {
            scanForPseudoCheck(5,7, board, player, own_taken, opp_taken);
            if (!pseudoChecked) {
                scanForPseudoCheck(6,7, board, player, own_taken, opp_taken);
                if (!pseudoChecked) valid[7][6] = true;
            }
        }
        // black king castles queen side
        if (!isChecked && player.turn == 'b' && !bkm && !bqrm
                && board[7][1] == 0 && board[7][2] == 0 && board[7][3] == 0) {
            scanForPseudoCheck(1,7, board, player, own_taken, opp_taken);
            if (!pseudoChecked) {
                scanForPseudoCheck(2,7, board, player, own_taken, opp_taken);
                if (!pseudoChecked) {
                    scanForPseudoCheck(3,7, board, player, own_taken, opp_taken);
                    if (!pseudoChecked) valid[7][2] = true;
                }
            }
        }
    }

    private void getCurrentKingPos(int[][] board, Player player) {
        if(player.turn == 'w') {
            for (int y=0; y<board.length; y++) {
                for (int x=0; x<board[0].length; x++) {
                    if (board[y][x] == 5) {
                        this.kingX = x;
                        this.kingY = y;
                        break;
                    }
                }
            }
        } else {
            for (int y=0; y<board.length; y++) {
                for (int x=0; x<board[0].length; x++) {
                    if (board[y][x] == -5) {
                        this.kingX = x;
                        this.kingY = y;
                        break;
                    }
                }
            }
        }
    }

    // check system, method assumes that opponent's and player's pieces values are already stored
    public void scanForCheck(int[][] board, Player player, boolean[][] own_taken, boolean[][] opp_taken) {
        int scanX, scanY, tempX, tempY;
        boolean blocked;
        clear2dArray(king_seen);
        getCurrentKingPos(board, player);
        isChecked = false; numChecked = 0;


        // check for pawn attacks
        scanX = kingX; scanY = kingY;
        if (player.turn == 'w') { // right of white king
            if (scanX+1 < 8 && scanY+1 < 8 && opp_taken[scanY+1][scanX+1] && board[scanY+1][scanX+1] == -1) {
                king_seen[scanY+1][scanX+1] = true;
                isChecked = true;
                numChecked++;
            }
        } else { // right of black king
            if (scanX+1 < 8 && scanY-1 >= 0 && opp_taken[scanY-1][scanX+1] && board[scanY-1][scanX+1] == 1) {
                king_seen[scanY-1][scanX+1] = true;
                isChecked = true;
                numChecked++;
            }
        }
        if (player.turn == 'w') { // left of white king
            if (scanX-1 >= 0 && scanY+1 < 8 && opp_taken[scanY+1][scanX-1] && board[scanY+1][scanX-1] == -1) {
                king_seen[scanY+1][scanX-1] = true;
                isChecked = true;
                numChecked++;
            }
        } else { // left of black king
            if (scanX-1 >= 0 && scanY-1 >= 0 && opp_taken[scanY-1][scanX-1] && board[scanY-1][scanX-1] == 1) {
                king_seen[scanY-1][scanX-1] = true;
                isChecked = true;
                numChecked++;
            }
        }


        // check for bishop/queen attacks
        // check up-right diagonal
        scanX = kingX; scanY = kingY; tempX = kingX; tempY = kingY; blocked = false;
        while (scanY+1 < 8 && scanX+1 < 8) { // check if diagonal is blocked
            scanY++; scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < 8 && scanX+1 < 8) { // marks first enemy queen/bishop that isn't blocked piece seen
            scanY++; scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX; tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < tempY && scanX+1 < tempX) { // if there is an attack, mark squares that has vision of king
            scanY++; scanX++;
            king_seen[scanY][scanX] = true;
        }

        // check down-left diagonal
        scanX = kingX; scanY = kingY; tempX = kingX; tempY = kingY; blocked = false;
        while (scanY-1 >= 0 && scanX-1 >= 0) { // check if diagonal is blocked
            scanY--; scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 >= 0 && scanX-1 >= 0) { // marks first enemy queen/bishop that isn't blocked piece seen
            scanY--; scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX; tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 > tempY && scanX-1 > tempX) { // if there is an attack, mark squares that has vision of king
            scanY--; scanX--;
            king_seen[scanY][scanX] = true;
        }

        // check up-left diagonal
        scanX = kingX; scanY = kingY; tempX = kingX; tempY = kingY; blocked = false;
        while (scanY+1 < 8 && scanX-1 >= 0) { // check if diagonal is blocked
            scanY++; scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < 8 && scanX-1 >= 0) { // marks first enemy queen/bishop that isn't blocked piece seen
            scanY++; scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX; tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < tempY && scanX-1 > tempX) { // if there is an attack, mark squares that has vision of king
            scanY++; scanX--;
            king_seen[scanY][scanX] = true;
        }

        // check down-right diagonal
        scanX = kingX; scanY = kingY; tempX = kingX; tempY = kingY; blocked = false;
        while (scanY-1 >= 0 && scanX+1 < 8) { // check if diagonal is blocked
            scanY--; scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 >= 0 && scanX+1 < 8) { // marks first enemy queen/bishop that isn't blocked piece seen
            scanY--; scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX; tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 > tempY && scanX+1 <tempX) { // if there is an attack, mark squares that has vision of king
            scanY--; scanX++;
            king_seen[scanY][scanX] = true;
        }


        // check for rook/queen attacks
        // check up
        scanX = kingX; scanY = kingY; tempY = kingY; blocked = false;
        while (scanY+1 < 8) { // check if diagonal is blocked
            scanY++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < 8) { // marks first enemy queen/rook that isn't blocked piece seen
            scanY++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY+1 < tempY) { // if there is an attack, mark squares that has vision of king
            scanY++;
            king_seen[scanY][scanX] = true;
        }

        // check down
        scanX = kingX; scanY = kingY; tempY = kingY; blocked = false;
        while (scanY-1 >= 0) { // check if diagonal is blocked
            scanY--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 >= 0) { // marks first enemy queen/rook that isn't blocked piece seen
            scanY--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempY = scanY;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanY-1 > tempY) { // if there is an attack, mark squares that has vision of king
            scanY--;
            king_seen[scanY][scanX] = true;
        }

        // check left
        scanX = kingX; scanY = kingY; tempX = kingX; blocked = false;
        while (scanX-1 >= 0) { // check if diagonal is blocked
            scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanX-1 >= 0) { // marks first enemy queen/rook that isn't blocked piece seen
            scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanX-1 > tempX) { // if there is an attack, mark squares that has vision of king
            scanX--;
            king_seen[scanY][scanX] = true;
        }

        // check right
        scanX = kingX; scanY = kingY; tempX = kingX; blocked = false;
        while (scanX+1 < 8) { // check if diagonal is blocked
            scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanX+1 < 8) { // marks first enemy queen/rook that isn't blocked piece seen
            scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                king_seen[scanY][scanX] = true;
                isChecked = true;
                numChecked++;
                tempX = scanX;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }
        scanX = kingX; scanY = kingY;
        while (scanX+1 <tempX) { // if there is an attack, mark squares that has vision of king
            scanX++;
            king_seen[scanY][scanX] = true;
        }


        // check for knight attacks
        scanX = kingX; scanY = kingY;
        // check if up 2 left 1 is attacking
        if (scanX-1 >= 0 && scanY+2 < 8
                && opp_taken[scanY+2][scanX-1]
                && (board[scanY+2][scanX-1] == 3
                || board[scanY+2][scanX-1] == -3)) {
            king_seen[scanY+2][scanX-1] = true;
            isChecked = true;
            numChecked++;
        }
        // check if up 2 right 1 is attacking
        if (scanX+1 < 8 && scanY+2 < 8
                && opp_taken[scanY+2][scanX+1]
                && (board[scanY+2][scanX+1] == 3
                || board[scanY+2][scanX+1] == -3)) {
            king_seen[scanY+2][scanX+1] = true;
            isChecked = true;
            numChecked++;
        }
        // check if down 2 left 1 is attacking
        if (scanX-1 >= 0 && scanY-2 >= 0
                && opp_taken[scanY-2][scanX-1]
                && (board[scanY-2][scanX-1] == 3
                || board[scanY-2][scanX-1] == -3)) {
            king_seen[scanY-2][scanX-1] = true;
            isChecked = true;
            numChecked++;
        }
        // check if down 2 right 1 is attacking
        if (scanX+1 < 8 && scanY-2 >= 0
                && opp_taken[scanY-2][scanX+1]
                && (board[scanY-2][scanX+1] == 3
                || board[scanY-2][scanX+1] == -3)) {
            king_seen[scanY-2][scanX+1] = true;
            isChecked = true;
            numChecked++;
        }
        // check if up 1 left 2 is attacking
        if (scanX-2 >= 0 && scanY+1<8
                && opp_taken[scanY+1][scanX-2]
                && (board[scanY+1][scanX-2] == 3
                || board[scanY+1][scanX-2] == -3)) {
            king_seen[scanY+1][scanX-2] = true;
            isChecked = true;
            numChecked++;
        }
        // check if up 1 right 2 is attacking
        if (scanX+2 < 8 && scanY+1<8
                && opp_taken[scanY+1][scanX+2]
                && (board[scanY+1][scanX+2] == 3
                || board[scanY+1][scanX+2] == -3)) {
            king_seen[scanY+1][scanX+2] = true;
            isChecked = true;
            numChecked++;
        }
        // check if down 1 left 2 is attacking
        if (scanX-2 >= 0 && scanY-1 >= 0
                && opp_taken[scanY-1][scanX-2]
                && (board[scanY-1][scanX-2] == 3
                || board[scanY-1][scanX-2] == -3)) {
            king_seen[scanY-1][scanX-2] = true;
            isChecked = true;
            numChecked++;
        }
        // check if down 1 right 2 is attacking
        if (scanX+2 < 8 && scanY-1 >= 0
                && opp_taken[scanY-1][scanX+2]
                && (board[scanY-1][scanX+2] == 3
                || board[scanY-1][scanX+2] == -3)) {
            king_seen[scanY-1][scanX+2] = true;
            isChecked = true;
            numChecked++;
        }
    }

    // scan for check at a square, but does not store king seen squares, used for king valid move calculation
    private void scanForPseudoCheck(int x, int y, int[][] board, Player player,
                                   boolean[][] own_taken, boolean[][] opp_taken) {
        int scanX, scanY;
        boolean blocked;
        pseudoChecked = false;
        getCurrentKingPos(board, player);
        own_taken[kingY][kingX] = false; // temp. sets position to false so king doesn't block itself when scanning for checks


        // check for king "attack"
        // top
        if (y + 1 < 8 && opp_taken[y + 1][x]
                && (board[y + 1][x] == 5 || board[y + 1][x] == -5)) {
            pseudoChecked = true;
        }
        // bottom
        if (y - 1 >= 0 && opp_taken[y - 1][x]
                && (board[y - 1][x] == 5 || board[y - 1][x] == -5)) {
            pseudoChecked = true;
        }
        // right
        if (x + 1 < 8 && opp_taken[y][x + 1]
                && (board[y][x + 1] == 5 || board[y][x + 1] == -5)) {
            pseudoChecked = true;
        }
        // left
        if (x - 1 >= 0 && opp_taken[y][x - 1]
                && (board[y][x - 1] == 5 || board[y][x - 1] == -5)) {
            pseudoChecked = true;
        }
        // top right
        if (y + 1 < 8 && x + 1 < 8 && opp_taken[y + 1][x + 1]
                && (board[y + 1][x + 1] == 5 || board[y + 1][x + 1] == -5)) {
            pseudoChecked = true;
        }
        // top left
        if (y + 1 < 8 && x - 1 >= 0 && opp_taken[y + 1][x - 1]
                && (board[y + 1][x - 1] == 5 || board[y + 1][x - 1] == -5)) {
            pseudoChecked = true;
        }
        // bottom right
        if (y - 1 >= 0 && x + 1 < 8 && opp_taken[y - 1][x + 1]
                && (board[y - 1][x + 1] == 5 || board[y - 1][x + 1] == -5)) {
            pseudoChecked = true;
        }
        // bottom left
        if (y - 1 >= 0 && x - 1 >= 0 && opp_taken[y - 1][x - 1]
                && (board[y - 1][x - 1] == 5 || board[y - 1][x - 1] == -5)) {
            pseudoChecked = true;
        }


        // check for pawn attacks
        scanX = x; scanY = y;
        if (player.turn == 'w') { // right of white king
            if (scanX+1 < 8 && scanY+1 < 8 && opp_taken[scanY+1][scanX+1] && board[scanY+1][scanX+1] == -1) {
                pseudoChecked = true;
            }
        } else { // right of black king
            if (scanX+1 < 8 && scanY-1 >= 0 && opp_taken[scanY-1][scanX+1] && board[scanY-1][scanX+1] == 1) {
                pseudoChecked = true;
            }
        }
        if (player.turn == 'w') { // left of white king
            if (scanX-1 >= 0 && scanY+1 < 8 && opp_taken[scanY+1][scanX-1] && board[scanY+1][scanX-1] == -1) {
                pseudoChecked = true;
            }
        } else { // left of black king
            if (scanX-1 >= 0 && scanY-1 >= 0 && opp_taken[scanY-1][scanX-1] && board[scanY-1][scanX-1] == 1) {
                pseudoChecked = true;
            }
        }


        // check for bishop/queen attacks
        // check up-right diagonal
        scanX = x; scanY = y; blocked = false;
        while (scanY+1 < 8 && scanX+1 < 8) { // check if diagonal is blocked
            scanY++; scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY+1 < 8 && scanX+1 < 8) { // check for unblocked enemy queen/bishop
            scanY++; scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check down-left diagonal
        scanX = x; scanY = y; blocked = false;
        while (scanY-1 >= 0 && scanX-1 >= 0) { // check if diagonal is blocked
            scanY--; scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY-1 >= 0 && scanX-1 >= 0) { // check for unblocked enemy queen/bishop
            scanY--; scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check up-left diagonal
        scanX = x; scanY = y; blocked = false;
        while (scanY+1 < 8 && scanX-1 >= 0) { // check if diagonal is blocked
            scanY++; scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY+1 < 8 && scanX-1 >= 0) { // check for unblocked enemy queen/bishop
            scanY++; scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check down-right diagonal
        scanX = x; scanY = y; blocked = false;
        while (scanY-1 >= 0 && scanX+1 < 8) { // check if diagonal is blocked
            scanY--; scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY-1 >= 0 && scanX+1 < 8) { // check for unblocked enemy queen/bishop
            scanY--; scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 2
                    || board[scanY][scanX] == -2
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 2
                    || board[scanY][scanX] != -2
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }


        // check for rook/queen attacks
        // check up
        scanX = x; scanY = y; blocked = false;
        while (scanY+1 < 8) { // check if diagonal is blocked
            scanY++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY+1 < 8) { // check for unblocked enemy rook/queen
            scanY++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check down
        scanX = x; scanY = y; blocked = false;
        while (scanY-1 >= 0) { // check if diagonal is blocked
            scanY--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanY-1 >= 0) { // check for unblocked enemy rook/queen
            scanY--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check left
        scanX = x; scanY = y; blocked = false;
        while (scanX-1 >= 0) { // check if diagonal is blocked
            scanX--;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanX-1 >= 0) { // check for unblocked enemy rook/queen
            scanX--;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }

        // check right
        scanX = x; scanY = y; blocked = false;
        while (scanX+1 < 8) { // check if diagonal is blocked
            scanX++;
            if (opp_taken[scanY][scanX]) {
                break;
            }
            if (own_taken[scanY][scanX]) {
                blocked = true;
                break;
            }
        }
        scanX = x; scanY = y;
        while (scanX+1 < 8) { // check for unblocked enemy rook/queen
            scanX++;

            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] == 4
                    || board[scanY][scanX] == -4
                    || board[scanY][scanX] == 6
                    || board[scanY][scanX] == -6)) {
                pseudoChecked = true;
                break;
            }
            if (opp_taken[scanY][scanX] && !blocked
                    && (board[scanY][scanX] != 4
                    || board[scanY][scanX] != -4
                    || board[scanY][scanX] != 6
                    || board[scanY][scanX] != -6)) {
                break;
            }
        }


        // check for knight attacks
        scanX = x; scanY = y;
        // check if up 2 left 1 is attacking
        if (scanX-1 >= 0 && scanY+2 < 8
                && opp_taken[scanY+2][scanX-1]
                && (board[scanY+2][scanX-1] == 3
                || board[scanY+2][scanX-1] == -3)) {
            pseudoChecked = true;
        }
        // check if up 2 right 1 is attacking
        if (scanX+1 < 8 && scanY+2 < 8
                && opp_taken[scanY+2][scanX+1]
                && (board[scanY+2][scanX+1] == 3
                || board[scanY+2][scanX+1] == -3)) {
            pseudoChecked = true;
        }
        // check if down 2 left 1 is attacking
        if (scanX-1 >= 0 && scanY-2 >= 0
                && opp_taken[scanY-2][scanX-1]
                && (board[scanY-2][scanX-1] == 3
                || board[scanY-2][scanX-1] == -3)) {
            pseudoChecked = true;
        }
        // check if down 2 right 1 is attacking
        if (scanX+1 < 8 && scanY-2 >= 0
                && opp_taken[scanY-2][scanX+1]
                && (board[scanY-2][scanX+1] == 3
                || board[scanY-2][scanX+1] == -3)) {
            pseudoChecked = true;
        }
        // check if up 1 left 2 is attacking
        if (scanX-2 >= 0 && scanY+1<8
                && opp_taken[scanY+1][scanX-2]
                && (board[scanY+1][scanX-2] == 3
                || board[scanY+1][scanX-2] == -3)) {
            pseudoChecked = true;
        }
        // check if up 1 right 2 is attacking
        if (scanX+2 < 8 && scanY+1<8
                && opp_taken[scanY+1][scanX+2]
                && (board[scanY+1][scanX+2] == 3
                || board[scanY+1][scanX+2] == -3)) {
            pseudoChecked = true;
        }
        // check if down 1 left 2 is attacking
        if (scanX-2 >= 0 && scanY-1 >= 0
                && opp_taken[scanY-1][scanX-2]
                && (board[scanY-1][scanX-2] == 3
                || board[scanY-1][scanX-2] == -3)) {
            pseudoChecked = true;
        }
        // check if down 1 right 2 is attacking
        if (scanX+2 < 8 && scanY-1 >= 0
                && opp_taken[scanY-1][scanX+2]
                && (board[scanY-1][scanX+2] == 3
                || board[scanY-1][scanX+2] == -3)) {
            pseudoChecked = true;
        }

        own_taken[kingY][kingX] = true;
    }
}