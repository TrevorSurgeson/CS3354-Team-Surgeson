package edu.txstate.chess.board;

import edu.txstate.chess.pieces.Piece;
import java.io.Serializable;

public class BoardState implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Piece[][] squares;
    private final Piece.Color currentTurn;
    private final Position enPassantTarget;

    public BoardState(Piece[][] squares, Piece.Color currentTurn, Position enPassantTarget) {
        this.squares = squares;
        this.currentTurn = currentTurn;
        this.enPassantTarget = enPassantTarget;
    }

    public Piece[][] getSquares() {
        return squares;
    }

    public Piece.Color getCurrentTurn() {
        return currentTurn;
    }

    public Position getEnPassantTarget() {
        return enPassantTarget;
    }
}