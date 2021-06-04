package com.mygdx.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BoardLogic extends ApplicationAdapter {
    public int x;
    public int y;
    public int clicked_x;
    public int clicked_y;
    public int promoX;
    public int promoY;
    public boolean pieceClicked;
    public boolean moved;
    public boolean canMove;

    private boolean white_king_moved;
    private boolean white_krook_moved;
    private boolean white_qrook_moved;
    private boolean black_king_moved;
    private boolean black_krook_moved;
    private boolean black_qrook_moved;
    final private boolean[][] black_en_passant;
    final private boolean[][] white_en_passant;

    public PieceLogic p;
    public PieceTools tools;
    final private Texture highlight;
    final private TextureRegion orange_highlight;
    final private TextureRegion blue_outline;
    final private TextureRegion green_outline;
    final private Texture promotion;
    final private TextureRegion white_promotion;
    final private TextureRegion black_promotion;
    final private Sound place_piece;
    final private Sound check;
    final private Sound checkmate;
    final private Sound draw;


    public BoardLogic() {
        // assets
        highlight = new Texture(Gdx.files.internal("highlight.png"));
        orange_highlight = new TextureRegion(highlight, 0, 0, 64, 64);
        blue_outline = new TextureRegion(highlight, 64, 0, 64, 64);
        green_outline = new TextureRegion(highlight, 128, 0, 64, 64);
        promotion = new Texture(Gdx.files.internal("promotion options.png"));
        white_promotion = new TextureRegion(promotion, 0, 0, 64, 256);
        black_promotion = new TextureRegion(promotion, 64, 0, 64, 256);
        place_piece = Gdx.audio.newSound(Gdx.files.internal("place.wav"));
        check = Gdx.audio.newSound(Gdx.files.internal("check.mp3"));
        checkmate = Gdx.audio.newSound(Gdx.files.internal("checkmate.mp3"));
        draw = Gdx.audio.newSound(Gdx.files.internal("tie.mp3"));
        // misc
        tools = new PieceTools();
        p = new PieceLogic();
        pieceClicked = false;
        moved = false;
        canMove = true;
        // special move checkers
        white_king_moved = false;
        white_krook_moved = false;
        white_qrook_moved = false;
        black_king_moved = false;
        black_krook_moved = false;
        black_qrook_moved = false;
        white_en_passant = new boolean[8][8];
        black_en_passant = new boolean[8][8];
    }

    public void getSquare(int cursorX, int cursorY) {
        // map values for regions of pixels
        if (cursorX >= 144 && cursorX < 208) {
            this.x = 0;
        } else if (cursorX >= 208 && cursorX < 272) {
            this.x = 1;
        } else if (cursorX >= 272 && cursorX < 336) {
            this.x = 2;
        } else if (cursorX >= 336 && cursorX < 400) {
            this.x = 3;
        } else if (cursorX >= 400 && cursorX < 464) {
            this.x = 4;
        } else if (cursorX >= 464 && cursorX < 528) {
            this.x = 5;
        } else if (cursorX >= 528 && cursorX < 592) {
            this.x = 6;
        } else if (cursorX >= 592 && cursorX < 656) {
            this.x = 7;
        } else this.x = -1;

        if (cursorY*-1+600 >= 44 && cursorY*-1+600 < 108) {
            this.y = 0;
        } else if (cursorY*-1+600 >= 108 && cursorY*-1+600 < 172) {
            this.y = 1;
        } else if (cursorY*-1+600 >= 172 && cursorY*-1+600 < 236) {
            this.y = 2;
        } else if (cursorY*-1+600 >= 236 && cursorY*-1+600 < 300) {
            this.y = 3;
        } else if (cursorY*-1+600 >= 300 && cursorY*-1+600 < 364) {
            this.y = 4;
        } else if (cursorY*-1+600 >= 364 && cursorY*-1+600 < 428) {
            this.y = 5;
        } else if (cursorY*-1+600 >= 428 && cursorY*-1+600 < 492) {
            this.y = 6;
        } else if (cursorY*-1+600 >= 492 && cursorY*-1+600 < 556) {
            this.y = 7;
        } else this.y = -1;
    }

    public void highlightHoveredSquare(SpriteBatch batch) {
        if (this.x >=0 && this.x <8 && this.y >=0 && this.y <8) {
            batch.draw(orange_highlight, 144 + this.x*64, 44 + this.y*64);
        }
    }

    public void highlightValidMoves(int[][] board, SpriteBatch batch) {
        if (pieceClicked) {
            int current_piece = board[this.clicked_y][this.clicked_x];
            batch.draw(green_outline, 144 + clicked_x*64, 44 + clicked_y*64);

            switch  (current_piece) {
                case 1:
                case -1:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getPawnValidMoves(clicked_x, clicked_y, board, white_en_passant, black_en_passant);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
                case 2:
                case -2:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getBishopValidMoves(clicked_x, clicked_y, board);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
                case 3:
                case -3:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getKnightValidMoves(clicked_x, clicked_y, board);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
                case 4:
                case -4:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getRookValidMoves(clicked_x, clicked_y, board);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
                case 5:
                case -5:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getKingValidMoves(clicked_x, clicked_y, board,
                            white_king_moved, white_krook_moved, white_qrook_moved,
                            black_king_moved, black_krook_moved, black_qrook_moved);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
                case 6:
                case -6:
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    //p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
                    p.getQueenValidMoves(clicked_x, clicked_y, board);
                    highlightLoop(batch);
                    //highlightCheckLoop(batch);
                    break;
            }
        }
    }

    // analyzes a selected square
    public void checkSquare(int[][] board) {
        if (this.x >=0 && this.x <8 && this.y >=0 && this.y <8) {
            int temp_selected = board[this.y][this.x];
            int selected = 0;

            // prevents player from moving opponent's pieces on their turn
            if (p.player.turn == 'w') {
                if (temp_selected > 0) {
                    selected = board[this.y][this.x];
                }
            } else {
                if (temp_selected < 0) {
                    selected = board[this.y][this.x];
                }
            }

            if (selected != 0) {
                pieceClicked = true;
                this.clicked_x = this.x;
                this.clicked_y = this.y;
            } else if (p.valid[this.y][this.x] && pieceClicked) { // if a valid square is clicked, moves the piece to that square
                board[this.y][this.x] = board[clicked_y][clicked_x];

                // check if pawn's first move is 2 squares and enables en passant if so
                if (board[this.y][this.x] == 1 && this.y == 3 && clicked_y == 1) {
                    white_en_passant[y-1][x] = true;
                }
                if (board[this.y][this.x] == -1 && this.y == 4 && clicked_y == 6) {
                    black_en_passant[y+1][x] = true;
                }

                // removes pawn if en passant taken
                if (board[this.y][this.x] == 1 && black_en_passant[this.y][this.x]) {
                    board[this.y - 1][this.x] = 0;
                }
                if (board[this.y][this.x] == -1 && white_en_passant[this.y][this.x]) {
                    board[this.y + 1][this.x] = 0;
                }

                // check if move is a king castle and moves rook if so
                if (board[this.y][this.x] == 5 && !white_king_moved
                        && this.y == 0 && this.x == 6) {
                    board[0][5] = 4;
                    board[0][7] = 0;
                    white_krook_moved = true;
                }
                if (board[this.y][this.x] == 5 && !white_king_moved
                        && this.y == 0 && this.x == 2) {
                    board[0][3] = 4;
                    board[0][0] = 0;
                    white_qrook_moved = true;
                }
                if (board[this.y][this.x] == -5 && !black_king_moved
                        && this.y == 7 && this.x == 6) {
                    board[7][5] = -4;
                    board[7][7] = 0;
                    black_krook_moved = true;
                }
                if (board[this.y][this.x] == -5 && !black_king_moved
                        && this.y == 7 && this.x == 2) {
                    board[7][3] = -4;
                    board[7][0] = 0;
                    black_qrook_moved = true;
                }

                // check if king's first move
                if (board[this.y][this.x] == 5 && !white_king_moved) white_king_moved = true;
                if (board[this.y][this.x] == -5 && !black_king_moved) black_king_moved = true;
                // check if rook's first move
                if (clicked_y == 0 && clicked_x == 7
                        && board[this.y][this.x] == 4
                        && !white_krook_moved) {
                    white_krook_moved = true;
                }
                if (clicked_y == 7 && clicked_x == 7
                        && board[this.y][this.x] == -4
                        && !black_krook_moved) {
                    black_krook_moved = true;
                }
                if (clicked_y == 0 && clicked_x == 0
                        && board[this.y][this.x] == 4
                        && !white_qrook_moved) {
                    white_krook_moved = true;
                }
                if (clicked_y == 7 && clicked_x == 0
                        && board[this.y][this.x] == -4
                        && !black_qrook_moved) {
                    black_krook_moved = true;
                }

                board[clicked_y][clicked_x] = 0;
                place_piece.play();

                // check if a moved pawn can be promoted
                if (board[this.y][this.x] == 1 || board[this.y][this.x] == -1) {
                    this.promoX = this.x; this.promoY = this.y;
                    p.pawn.checkPromo(this.y, p.player);
                }
                if (!p.pawn.inPromo) moved = true;

            } else {
                pieceClicked = false;
            }
        }
    }

    // loop use to render promo options and choose promo
    public void promoLoop(SpriteBatch batch, int x, int y, Player player, int[][] board) {
        if (player.turn == 'w') {
            batch.draw(white_promotion, 700, 600/2 - 256/2);
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                board[y][x] = 6;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                board[y][x] = 4;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                board[y][x] = 2;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                board[y][x] = 3;
                p.pawn.inPromo = false;
            }
        } else {
            batch.draw(black_promotion, 700, 600/2 - 256/2);
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                board[y][x] = -6;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                board[y][x] = -4;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3)) {
                board[y][x] = -2;
                p.pawn.inPromo = false;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_4)) {
                board[y][x] = -3;
                p.pawn.inPromo = false;
            }
        }
    }

    // loop use to render highlighted valid squares
    private void highlightLoop(SpriteBatch batch) {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                boolean draw_check = p.valid[y][x];
                if (draw_check) {
                    batch.draw(blue_outline, 144 + x*64, 44 + y*64);
                }
            }
        }
    }

    // test loop used to draw squares that have vision of the king when in check
    // wont be used in the final program
    private void highlightCheckLoop(SpriteBatch batch) {
        for (int y = 0; y< p.valid.length; y++) {
            for (int x = 0; x< p.valid[0].length; x++) {
                boolean draw_check = p.king.king_seen[y][x];
                if (draw_check) {
                    batch.draw(orange_highlight, 144 + x*64, 44 + y*64);
                }
            }
        }
    }

    public void switchAndCheck(int[][] board) {
        p.player.switchTurns();
        tools.getOwnPieceValues(board, p.player, p.own_taken);
        tools.getOpponentPieceValues(board, p.player, p.opp_taken);
        p.king.scanForCheck(board, p.player, p.own_taken, p.opp_taken);
        if (p.king.isChecked && p.player.game_state.equals("PLAYING")) {
            check.play(0.5f);
        }
        pieceClicked = false;
        moved = false;
        if (p.player.turn == 'w') {
            tools.clear2dArray(white_en_passant);
        } else {
            tools.clear2dArray(black_en_passant);
        }
    }

    public void checkWin(int[][] board) {
        if (scanOnlyKings(board)) {
            p.player.game_state = "DRAW";
            draw.play();
            canMove = false;
        } else if (!p.king.isChecked && !scanPieces(board)) {
            p.player.game_state = "STALEMATE";
            draw.play();
            canMove = false;
        } else if (p.king.isChecked && !scanPieces(board)) {
            checkmate.play();
            if (p.player.turn == 'w') p.player.game_state = "BLACK WINS";
            else p.player.game_state = "WHITE WINS";
            canMove = false;
        }
    }

    private boolean scanPieces(int[][] board) {
        boolean hasValidMoves = false;
        int pValue;

        if (p.player.turn == 'w') pValue = 1;
        else pValue = -1;

        for (int y=0; y<board.length; y++) {
            for (int x=0; x<board[0].length; x++) {
                if (board[y][x] == 1*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getPawnValidMoves(x, y, board, white_en_passant, black_en_passant);
                    if (scanValid()) hasValidMoves = true;
                }
                if (board[y][x] == 2*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getBishopValidMoves(x, y, board);
                    if (scanValid()) hasValidMoves = true;
                }
                if (board[y][x] == 3*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getKnightValidMoves(x, y, board);
                    if (scanValid()) hasValidMoves = true;
                }
                if (board[y][x] == 4*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getRookValidMoves(x, y, board);
                    if (scanValid()) hasValidMoves = true;
                }
                if (board[y][x] == 5*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getKingValidMoves(x, y, board,
                            white_king_moved, white_krook_moved, white_qrook_moved,
                            black_king_moved, black_krook_moved, black_qrook_moved);
                    if (scanValid()) hasValidMoves = true;
                }
                if (board[y][x] == 6*pValue) {
                    tools.clear2dArray(p.valid);
                    tools.clear2dArray(p.own_taken);
                    tools.clear2dArray(p.opp_taken);
                    tools.getOwnPieceValues(board, p.player, p.own_taken);
                    tools.getOpponentPieceValues(board, p.player, p.opp_taken);
                    p.getQueenValidMoves(x, y, board);
                    if (scanValid()) hasValidMoves = true;
                }
            }
        }

        return hasValidMoves;
    }

    public boolean scanValid() {
        boolean hasValid = false;
        for (int y=0; y<p.valid.length; y++) {
            for (int x=0; x<p.valid[0].length; x++) {
                if (p.valid[y][x]) {
                    hasValid = true;
                }
            }
        }
        return hasValid;
    }

    private boolean scanOnlyKings(int[][] board) {
        boolean onlyKings = true;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[0].length; x++) {
                if (!(board[y][x] == 0 || board[y][x] == 5 || board[y][x] == -5)) {
                    onlyKings = false;
                }
            }
        }
        return onlyKings;
    }

    public void dispose() {
        highlight.dispose();
        promotion.dispose();
        place_piece.dispose();
        check.dispose();
        draw.dispose();
        checkmate.dispose();
    }
}
