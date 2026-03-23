package edu.txstate.chess.board;

import edu.txstate.chess.pieces.*;
import edu.txstate.chess.pieces.Piece.Color;

public class Board {

    private final Piece[][] squares = new Piece[8][8];

    public Board() {
        initialize();
    }

    public final void initialize() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                squares[r][c] = null;
            }
        }

        squares[0][0] = new Rook(Color.WHITE);
        squares[0][1] = new Knight(Color.WHITE);
        squares[0][2] = new Bishop(Color.WHITE);
        squares[0][3] = new Queen(Color.WHITE);
        squares[0][4] = new King(Color.WHITE);
        squares[0][5] = new Bishop(Color.WHITE);
        squares[0][6] = new Knight(Color.WHITE);
        squares[0][7] = new Rook(Color.WHITE);
        for (int c = 0; c < 8; c++) {
            squares[1][c] = new Pawn(Color.WHITE);
        }

        squares[7][0] = new Rook(Color.BLACK);
        squares[7][1] = new Knight(Color.BLACK);
        squares[7][2] = new Bishop(Color.BLACK);
        squares[7][3] = new Queen(Color.BLACK);
        squares[7][4] = new King(Color.BLACK);
        squares[7][5] = new Bishop(Color.BLACK);
        squares[7][6] = new Knight(Color.BLACK);
        squares[7][7] = new Rook(Color.BLACK);
        for (int c = 0; c < 8; c++) {
            squares[6][c] = new Pawn(Color.BLACK);
        }
    }

    public Piece getPiece(Position p) {
        return squares[p.getRow()][p.getCol()];
    }

    public void setPiece(Position p, Piece piece) {
        squares[p.getRow()][p.getCol()] = piece;
    }

    public boolean isInsideBoard(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public boolean movePiece(Position from, Position to, Color currentTurn) {
        Piece moving = getPiece(from);
        if (moving == null) {
            return false;
        }
        if (moving.getColor() != currentTurn) {
            return false;
        }
        Piece target = getPiece(to);
        if (target != null && target.getColor() == currentTurn) {
            return false;
        }

        var moves = moving.possibleMoves(this, from);
        boolean legal = false;
        for (Position p : moves) {
            if (p.getRow() == to.getRow() && p.getCol() == to.getCol()) {
                legal = true;
                break;
            }
        }
        if (!legal) {
            return false;
        }

        setPiece(to, moving);
        setPiece(from, null);
        return true;
    }

    public void display() {
        System.out.println("    A  B  C  D  E  F  G  H");
        for (int row = 7; row >= 0; row--) {
            System.out.print((row + 1) + " ");
            for (int col = 0; col < 8; col++) {
                Piece piece = squares[row][col];
                if (piece == null) {
                    System.out.print(" ##");
                } else {
                    System.out.print(" " + piece.getSymbol());
                }
            }
            System.out.println(" " + (row + 1));
        }
        System.out.println("    A  B  C  D  E  F  G  H");
    }
}