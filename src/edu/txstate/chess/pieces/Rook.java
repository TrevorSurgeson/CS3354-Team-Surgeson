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

        int row = from.getRow();
        int col = from.getCol();

        for (int r = row + 1; r < 8; r++) {
            if (!addSquare(board, moves, r, col)) break;
        }
        for (int r = row - 1; r >= 0; r--) {
            if (!addSquare(board, moves, r, col)) break;
        }
        for (int c = col + 1; c < 8; c++) {
            if (!addSquare(board, moves, row, c)) break;
        }
        for (int c = col - 1; c >= 0; c--) {
            if (!addSquare(board, moves, row, c)) break;
        }

        return moves;
    }

    private boolean addSquare(Board board, List<Position> moves, int row, int col) {
        if (!board.isInsideBoard(row, col)) {
            return false;
        }
        Position p = new Position(row, col);
        Piece target = board.getPiece(p);
        if (target == null) {
            moves.add(p);
            return true;
        } else {
            if (target.getColor() != getColor()) {
                moves.add(p);
            }
            return false;
        }
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wR" : "bR";
    }
}