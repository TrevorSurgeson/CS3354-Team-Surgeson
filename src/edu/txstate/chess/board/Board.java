package edu.txstate.chess.board;

import edu.txstate.chess.pieces.*;
import edu.txstate.chess.pieces.Piece.Color;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private final Piece[][] squares = new Piece[8][8];
    private Position enPassantTarget;

    public Board() {
        initialize();
    }

    public final void initialize() {
        clear();
        enPassantTarget = null;

        squares[0][0] = new Rook(Color.WHITE);
        squares[0][1] = new Knight(Color.WHITE);
        squares[0][2] = new Bishop(Color.WHITE);
        squares[0][3] = new Queen(Color.WHITE);
        squares[0][4] = new King(Color.WHITE);
        squares[0][5] = new Bishop(Color.WHITE);
        squares[0][6] = new Knight(Color.WHITE);
        squares[0][7] = new Rook(Color.WHITE);
        for (int c = 0; c < 8; c++) squares[1][c] = new Pawn(Color.WHITE);

        squares[7][0] = new Rook(Color.BLACK);
        squares[7][1] = new Knight(Color.BLACK);
        squares[7][2] = new Bishop(Color.BLACK);
        squares[7][3] = new Queen(Color.BLACK);
        squares[7][4] = new King(Color.BLACK);
        squares[7][5] = new Bishop(Color.BLACK);
        squares[7][6] = new Knight(Color.BLACK);
        squares[7][7] = new Rook(Color.BLACK);
        for (int c = 0; c < 8; c++) squares[6][c] = new Pawn(Color.BLACK);
    }

    public void clear() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = null;
            }
        }
    }

    public Piece getPiece(Position p) {
        return squares[p.getRow()][p.getCol()];
    }

    public Piece getPiece(int row, int col) {
        return squares[row][col];
    }

    public void setPiece(Position p, Piece piece) {
        squares[p.getRow()][p.getCol()] = piece;
    }

    public void setPiece(int row, int col, Piece piece) {
        squares[row][col] = piece;
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public Position getEnPassantTarget() {
        return enPassantTarget;
    }

    public List<Position> getCastlingMoves(Position kingPos) {
        List<Position> moves = new ArrayList<>();
        Piece piece = getPiece(kingPos);
        if (!(piece instanceof King) || piece.hasMoved()) {
            return moves;
        }
        if (isInCheck(piece.getColor())) {
            return moves;
        }

        int row = kingPos.getRow();

        if (canCastle(kingPos, row, 7, 1)) moves.add(new Position(row, 6));
        if (canCastle(kingPos, row, 0, -1)) moves.add(new Position(row, 2));
        return moves;
    }

    private boolean canCastle(Position kingPos, int row, int rookCol, int direction) {
        Piece rook = getPiece(row, rookCol);
        if (!(rook instanceof Rook) || rook.getColor() != getPiece(kingPos).getColor() || rook.hasMoved()) {
            return false;
        }

        int start = Math.min(kingPos.getCol(), rookCol) + 1;
        int end = Math.max(kingPos.getCol(), rookCol) - 1;
        for (int c = start; c <= end; c++) {
            if (getPiece(row, c) != null) {
                return false;
            }
        }

        for (int c = kingPos.getCol(); c != kingPos.getCol() + 2 * direction + direction; c += direction) {
            Position step = new Position(row, c);
            if (wouldLeaveKingInCheck(kingPos, step, getPiece(kingPos).getColor())) {
                return false;
            }
        }
        return true;
    }

    public List<Position> getLegalMoves(Position from, Color currentTurn) {
        List<Position> legal = new ArrayList<>();
        Piece piece = getPiece(from);
        if (piece == null || piece.getColor() != currentTurn) {
            return legal;
        }
        for (Position move : piece.possibleMoves(this, from)) {
            if (!wouldLeaveKingInCheck(from, move, currentTurn)) {
                legal.add(move);
            }
        }
        return legal;
    }

    public boolean wouldLeaveKingInCheck(Position from, Position to, Color currentTurn) {
        BoardState snapshot = saveState(currentTurn);
        makeMoveInternal(from, to, false);
        boolean inCheck = isInCheck(currentTurn);
        loadState(snapshot);
        return inCheck;
    }

    public MoveResult movePiece(Position from, Position to, Color currentTurn) {
        Piece moving = getPiece(from);
        if (moving == null) {
            return new MoveResult(false, "No piece selected.");
        }
        if (moving.getColor() != currentTurn) {
            return new MoveResult(false, "That is not your piece.");
        }

        List<Position> legalMoves = getLegalMoves(from, currentTurn);
        boolean found = false;
        for (Position p : legalMoves) {
            if (p.equals(to)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return new MoveResult(false, "Illegal move.");
        }

        makeMoveInternal(from, to, true);
        return new MoveResult(true, "Move completed.");
    }

    private void makeMoveInternal(Position from, Position to, boolean updateMovedFlag) {
        Piece moving = getPiece(from);
        Piece target = getPiece(to);

        if (moving instanceof Pawn && enPassantTarget != null && to.equals(enPassantTarget) && target == null && from.getCol() != to.getCol()) {
            int capturedRow = moving.getColor() == Color.WHITE ? to.getRow() - 1 : to.getRow() + 1;
            setPiece(capturedRow, to.getCol(), null);
        }

        if (moving instanceof King && Math.abs(to.getCol() - from.getCol()) == 2) {
            if (to.getCol() == 6) {
                Piece rook = getPiece(from.getRow(), 7);
                setPiece(from.getRow(), 5, rook);
                setPiece(from.getRow(), 7, null);
                if (rook != null && updateMovedFlag) rook.setHasMoved(true);
            } else if (to.getCol() == 2) {
                Piece rook = getPiece(from.getRow(), 0);
                setPiece(from.getRow(), 3, rook);
                setPiece(from.getRow(), 0, null);
                if (rook != null && updateMovedFlag) rook.setHasMoved(true);
            }
        }

        setPiece(to, moving);
        setPiece(from, null);

        if (updateMovedFlag && moving != null) {
            moving.setHasMoved(true);
        }

        enPassantTarget = null;
        if (moving instanceof Pawn && Math.abs(to.getRow() - from.getRow()) == 2) {
            enPassantTarget = new Position((from.getRow() + to.getRow()) / 2, from.getCol());
        }
    }

    public Position findKing(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = squares[r][c];
                if (p instanceof King && p.getColor() == color) {
                    return new Position(r, c);
                }
            }
        }
        return null;
    }

    public boolean isInCheck(Color color) {
        Position kingPos = findKing(color);
        if (kingPos == null) {
            return false;
        }
        Color opponent = color == Color.WHITE ? Color.BLACK : Color.WHITE;
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = squares[r][c];
                if (p != null && p.getColor() == opponent) {
                    List<Position> moves;
                    if (p instanceof King) {
                        moves = getKingAttackSquares(new Position(r, c));
                    } else {
                        moves = p.possibleMoves(this, new Position(r, c));
                    }
                    for (Position move : moves) {
                        if (move.equals(kingPos)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private List<Position> getKingAttackSquares(Position from) {
        List<Position> moves = new ArrayList<>();
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int r = from.getRow() + dr;
                int c = from.getCol() + dc;
                if (isInsideBoard(r, c)) {
                    moves.add(new Position(r, c));
                }
            }
        }
        return moves;
    }

    public boolean hasAnyLegalMove(Color color) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece p = squares[r][c];
                if (p != null && p.getColor() == color) {
                    if (!getLegalMoves(new Position(r, c), color).isEmpty()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(Color color) {
        return isInCheck(color) && !hasAnyLegalMove(color);
    }

    public boolean isStalemate(Color color) {
        return !isInCheck(color) && !hasAnyLegalMove(color);
    }

    public BoardState saveState(Color currentTurn) {
        Piece[][] copy = new Piece[8][8];
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                copy[r][c] = clonePiece(squares[r][c]);
            }
        }
        Position ep = enPassantTarget == null ? null : new Position(enPassantTarget.getRow(), enPassantTarget.getCol());
        return new BoardState(copy, currentTurn, ep);
    }

    public void loadState(BoardState state) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = clonePiece(state.getSquares()[r][c]);
            }
        }
        enPassantTarget = state.getEnPassantTarget() == null ? null : new Position(state.getEnPassantTarget().getRow(), state.getEnPassantTarget().getCol());
    }

    private Piece clonePiece(Piece piece) {
        if (piece == null) return null;
        Piece copy;
        if (piece instanceof Pawn) copy = new Pawn(piece.getColor());
        else if (piece instanceof Rook) copy = new Rook(piece.getColor());
        else if (piece instanceof Knight) copy = new Knight(piece.getColor());
        else if (piece instanceof Bishop) copy = new Bishop(piece.getColor());
        else if (piece instanceof Queen) copy = new Queen(piece.getColor());
        else copy = new King(piece.getColor());
        copy.setHasMoved(piece.hasMoved());
        return copy;
    }
}