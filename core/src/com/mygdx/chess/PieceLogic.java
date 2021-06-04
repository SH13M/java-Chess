package com.mygdx.chess;

public class PieceLogic {
    public Player player;
    public Pawn pawn;
    public Knight knight;
    public Rook rook;
    public Bishop bishop;
    public King king;

    public boolean[][] own_taken;
    public boolean[][] opp_taken;
    public boolean[][] valid;


    public PieceLogic() {
        pawn = new Pawn();
        knight = new Knight();
        rook = new Rook();
        bishop = new Bishop();
        king = new King();
        player = new Player();

        own_taken = new boolean[8][8];
        opp_taken = new boolean[8][8];
        own_taken = new boolean[8][8];
        valid = new boolean[8][8];
    }

    public void getPawnValidMoves(int x, int y, int[][] board,
                                  boolean[][] white_en_passant, boolean[][] black_en_passant) {
        if (!king.isChecked) {
            pawn.getValidMoves(x, y, board, player, own_taken, opp_taken, valid, white_en_passant, black_en_passant);
        } else if (king.numChecked < 2) {
            pawn.getValidCheckMoves(x, y, player, own_taken, opp_taken, valid, king.king_seen);
        }
    }

    public void getKnightValidMoves(int x, int y, int[][] board) {
        if (!king.isChecked) {
            knight.getValidMoves(x, y, board, player, own_taken, opp_taken, valid);
        } else if (king.numChecked < 2) {
            knight.getValidCheckMoves(x, y, own_taken, valid, king.king_seen);
        }
    }

    public void getBishopValidMoves(int x, int y, int[][] board) {
        if (!king.isChecked) {
            bishop.getValidMoves(x, y, board, player, own_taken, opp_taken, valid);
        } else if (king.numChecked < 2) {
            bishop.getValidCheckMoves(x, y, own_taken, opp_taken, valid, king.king_seen);
        }

    }

    public void getRookValidMoves(int x, int y, int[][] board) {
        if (!king.isChecked) {
            rook.getValidMoves(x, y, board, player, own_taken, opp_taken, valid);
        } else if (king.numChecked < 2) {
            rook.getValidCheckMoves(x, y, own_taken, opp_taken, valid, king.king_seen);
        }

    }

    public void getQueenValidMoves(int x, int y, int[][] board) {
        // queen logic is rook and bishop logic combined
        if (!king.isChecked) {
            bishop.getValidMoves(x, y, board, player, own_taken, opp_taken, valid);
            rook.getValidMoves(x, y, board, player, own_taken, opp_taken, valid);
        } else if (king.numChecked < 2) {
            bishop.getValidCheckMoves(x, y, own_taken, opp_taken, valid, king.king_seen);
            rook.getValidCheckMoves(x, y, own_taken, opp_taken, valid, king.king_seen);
        }
    }

    public void getKingValidMoves(int x, int y, int[][] board,
                                  boolean wkm, boolean wkrm, boolean wqrm,
                                  boolean bkm, boolean bkrm, boolean bqrm) {
        king.getValidMoves(x, y, board, player, own_taken, opp_taken, valid,
                wkm, wkrm, wqrm, bkm, bkrm, bqrm);
    }
}