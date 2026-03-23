package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.Collections;
import java.util.List;

public final class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> possibleMoves(Board board, Position from) {
        return Collections.emptyList();
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wK" : "bK";
    }
}