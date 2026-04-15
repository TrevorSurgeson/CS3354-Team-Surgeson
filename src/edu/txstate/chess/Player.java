package edu.txstate.chess;

import edu.txstate.chess.pieces.Piece;

public class Player {
    private final Piece.Color color;

    public Player(Piece.Color color) {
        this.color = color;
    }

    public Piece.Color getColor() {
        return color;
    }
}