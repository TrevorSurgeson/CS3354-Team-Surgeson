package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {
    public King(Color color) {
        super(color);
    }

    @Override
    public List<Position> possibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = from.getRow() + dr;
                int c = from.getCol() + dc;
                if (!board.isInsideBoard(r, c)) continue;
                Position p = new Position(r, c);
                Piece target = board.getPiece(p);
                if (target == null || target.getColor() != getColor()) {
                    moves.add(p);
                }
            }
        }
        moves.addAll(board.getCastlingMoves(from));
        return moves;
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wK" : "bK";
    }
}