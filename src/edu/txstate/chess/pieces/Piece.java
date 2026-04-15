package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.io.Serializable;
import java.util.List;

public abstract class Piece implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Color { WHITE, BLACK }

    private final Color color;
    private boolean hasMoved;

    protected Piece(Color color) {
        this.color = color;
        this.hasMoved = false;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public abstract List<Position> possibleMoves(Board board, Position from);

    public abstract String getSymbol();
}