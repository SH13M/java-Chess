package com.mygdx.chess;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Chess extends ApplicationAdapter {
	// render
	private SpriteBatch batch;
	private OrthographicCamera cam;
	public int[][] board;
	private int current_draw;
	private BoardLogic logic;

	// textures
	private Texture boardimg;
	private Rectangle boardrect;
	private Texture pieces;
	private TextureRegion wPawn;
	private TextureRegion wRook;
	private TextureRegion wKnight;
	private TextureRegion wBishop;
	private TextureRegion wKing;
	private TextureRegion wQueen;
	private TextureRegion bPawn;
	private TextureRegion bRook;
	private TextureRegion bKnight;
	private TextureRegion bBishop;
	private TextureRegion bKing;
	private TextureRegion bQueen;

	// text
	private BitmapFont font;

	// bg music
	private Music bg;
	private boolean isPaused;

	@Override
	public void create () {
		batch = new SpriteBatch();
		board  = new int[8][8];
		logic = new BoardLogic();

		// instantiate text
		font = new BitmapFont();

		// set up camera
		cam = new OrthographicCamera();
		cam.setToOrtho(false, 800, 600);

		// instantiate the board
		boardimg = new Texture(Gdx.files.internal("board.png"));
		boardrect = new Rectangle();
		boardrect.width = 512;
		boardrect.height = 512;
		boardrect.x = 800/2 - 512/2;
		boardrect.y = 600/2 - 512/2;
		// place the pieces in their starting positions
		initboard();

		// instantiate piece textures
		pieces = new Texture(Gdx.files.internal("pieces.png"));
		wKing = new TextureRegion(pieces, 0, 0, 64, 64);
		bKing = new TextureRegion(pieces, 0, 64, 64, 64);
		wQueen = new TextureRegion(pieces, 64, 0, 64, 64);
		bQueen = new TextureRegion(pieces, 64, 64, 64, 64);
		wBishop = new TextureRegion(pieces, 128, 0, 64, 64);
		bBishop = new TextureRegion(pieces, 128, 64, 64, 64);
		wKnight = new TextureRegion(pieces, 192, 0, 64, 64);
		bKnight = new TextureRegion(pieces, 192, 64, 64, 64);
		wRook = new TextureRegion(pieces, 256, 0, 64, 64);
		bRook = new TextureRegion(pieces, 256, 64, 64, 64);
		wPawn = new TextureRegion(pieces, 320, 0, 64, 64);
		bPawn = new TextureRegion(pieces, 320, 64, 64, 64);

		// get music, lower volume, loop
		bg = Gdx.audio.newMusic(Gdx.files.internal("Jake Chudnow - Moon Men (Instrumental).mp3"));
		bg.setVolume(0.02f);
		bg.setLooping(true);
		bg.play();
		isPaused = false;
	}

	@Override
	public void render () {
		ScreenUtils.clear(.1f, .1f, .1f, 1);
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.draw(boardimg, boardrect.x, boardrect.y);
		drawPieces();
		if (logic.p.player.game_state == "PLAYING") {
			font.draw(batch, "TURN: " + logic.p.player.turn_text, 5, 595);
			font.draw(batch, logic.p.player.game_state, 5, 575);
		} else {
			font.draw(batch, logic.p.player.game_state, 5, 595);
		}

		logic.highlightHoveredSquare(batch);

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && logic.canMove) {
			if (!logic.p.pawn.inPromo) logic.checkSquare(board);
		}
		if (logic.p.pawn.inPromo) {
			logic.promoLoop(batch, logic.promoX, logic.promoY, logic.p.player, board);
			if (!logic.p.pawn.inPromo) logic.moved = true;
		}
		if (logic.moved) {
			logic.switchAndCheck(board);
			logic.checkWin(board);
		}
		if (logic.pieceClicked) {
			// band-aid fix for the game breaking bug mentioned in things file
			// might not need some of these but the game seems to work so I'm leaving them here
			logic.tools.clear2dArray(logic.p.own_taken);
			logic.tools.clear2dArray(logic.p.opp_taken);
			logic.tools.clear2dArray(logic.p.valid);

			logic.highlightValidMoves(board, batch);
		}
		batch.end();
		logic.getSquare(Gdx.input.getX(), Gdx.input.getY());
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		boardimg.dispose();
		pieces.dispose();
		bg.dispose();
	}

	private void initboard() {
		// 0=empty, 1=pawn, 2=bishop, 3=knight, 4=rook, 5=king, 6=queen [+]white [-]black

		// place pawns
		for (int i=0; i<8; i++) {
			board[1][i] = 1;
			board[6][i] = -1;
		}

		// place white pieces
		board[0][0] = 4;
		board[0][1] = 3;
		board[0][2] = 2;
		board[0][3] = 6;
		board[0][4] = 5;
		board[0][5] = 2;
		board[0][6] = 3;
		board[0][7] = 4;

		// place black pieces
		board[7][0] = -4;
		board[7][1] = -3;
		board[7][2] = -2;
		board[7][3] = -6;
		board[7][4] = -5;
		board[7][5] = -2;
		board[7][6] = -3;
		board[7][7] = -4;
	}

	private void drawPieces() {
		// loops through the grid and draws a piece if there is one, co-ords start at bottom left of board
		for (int y=0; y<board.length; y++) {
			for (int x = 0; x<board[0].length; x++) {
				current_draw = board[y][x];
				switch (current_draw) {
					case 1:
						batch.draw(wPawn, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case 2:
						batch.draw(wBishop, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case 3:
						batch.draw(wKnight, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case 4:
						batch.draw(wRook, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case 5:
						batch.draw(wKing, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case 6:
						batch.draw(wQueen, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;

					case -1:
						batch.draw(bPawn, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case -2:
						batch.draw(bBishop, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case -3:
						batch.draw(bKnight, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case -4:
						batch.draw(bRook, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case -5:
						batch.draw(bKing, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
					case -6:
						batch.draw(bQueen, 176 + x*64 - 64/2, 76 + y*64 - 64/2);
						break;
				}
			}
		}
	}
}
