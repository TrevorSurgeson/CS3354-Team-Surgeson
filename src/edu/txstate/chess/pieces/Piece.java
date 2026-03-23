package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.List;

public abstract class Piece {

    public enum Color { WHITE, BLACK }

    private final Color color;

    protected Piece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract List<Position> possibleMoves(Board board, Position from);

    public abstract String getSymbol();
}