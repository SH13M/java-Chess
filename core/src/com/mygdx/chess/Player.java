package com.mygdx.chess;

public class Player {
    public char turn;
    public String turn_text;
    public String game_state;

    public Player() {
        turn = 'w';
        turn_text = "WHITE";
        game_state = "PLAYING";
    }

    public void switchTurns() {
        if (turn == 'w') {
            turn = 'b';
            turn_text = "BLACK";
        } else {
            turn = 'w';
            turn_text = "WHITE";
        }
    }
}
