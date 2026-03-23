package edu.txstate.chess.pieces;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.board.Position;
import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public List<Position> possibleMoves(Board board, Position from) {
        List<Position> moves = new ArrayList<>();
        int direction = getColor() == Color.WHITE ? 1 : -1;
        int startRow = getColor() == Color.WHITE ? 1 : 6;

        int oneStepRow = from.getRow() + direction;
        if (board.isInsideBoard(oneStepRow, from.getCol()) && board.getPiece(new Position(oneStepRow, from.getCol())) == null) {
            moves.add(new Position(oneStepRow, from.getCol()));
            int twoStepRow = from.getRow() + 2 * direction;
            if (from.getRow() == startRow && board.isInsideBoard(twoStepRow, from.getCol()) && board.getPiece(new Position(twoStepRow, from.getCol())) == null) {
                moves.add(new Position(twoStepRow, from.getCol()));
            }
        }

        int leftCol = from.getCol() - 1;
        int rightCol = from.getCol() + 1;
        if (board.isInsideBoard(oneStepRow, leftCol)) {
            Position diagLeft = new Position(oneStepRow, leftCol);
            var target = board.getPiece(diagLeft);
            if (target != null && target.getColor() != getColor()) {
                moves.add(diagLeft);
            }
        }
        if (board.isInsideBoard(oneStepRow, rightCol)) {
            Position diagRight = new Position(oneStepRow, rightCol);
            var target = board.getPiece(diagRight);
            if (target != null && target.getColor() != getColor()) {
                moves.add(diagRight);
            }
        }
        return moves;
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "wp" : "bp";
    }
}