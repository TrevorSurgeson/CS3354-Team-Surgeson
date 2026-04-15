package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.ArrayList;
import java.util.List;

public final class Rook extends Piece {
    public Rook(Color color) {
        super(color);
    }

    @Override
    public List<Position> possibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        addLine(board, moves, from, 1, 0);
        addLine(board, moves, from, -1, 0);
        addLine(board, moves, from, 0, 1);
        addLine(board, moves, from, 0, -1);
        return moves;
    }

    private void addLine(Board board, List<Position> moves, Position from, int dr, int dc) {
        int r = from.getRow() + dr;
        int c = from.getCol() + dc;
        while (board.isInsideBoard(r, c)) {
            Position p = new Position(r, c);
            Piece target = board.getPiece(p);
            if (target == null) {
                moves.add(p);
            } else {
                if (target.getColor() != getColor()) {
                    moves.add(p);
                }
                break;
            }
            r += dr;
            c += dc;
        }
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wR" : "bR";
    }
}