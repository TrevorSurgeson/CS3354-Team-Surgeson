package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {
    public Knight(Color color) {
        super(color);
    }

    @Override
    public List<Position> possibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        int[][] deltas = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        for (int[] d : deltas) {
            int r = from.getRow() + d[0];
            int c = from.getCol() + d[1];
            if (!board.isInsideBoard(r, c)) {
                continue;
            }
            Position p = new Position(r, c);
            Piece target = board.getPiece(p);
            if (target == null || target.getColor() != getColor()) {
                moves.add(p);
            }
        }
        return moves;
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wN" : "bN";
    }
}