package edu.txstate.chess.util;

import edu.txstate.chess.board.Position;

public final class InputParser {
    private InputParser() { }

    public static ParsedMove parseMove(String line) {
        if (line == null) return null;
        String trimmed = line.trim();
        if (trimmed.isEmpty()) return null;
        String[] parts = trimmed.split("\\\\s+");
        if (parts.length != 2) return null;
        Position from = parseSquare(parts[0]);
        Position to = parseSquare(parts[1]);
        if (from == null || to == null) return null;
        return new ParsedMove(from, to);
    }

    private static Position parseSquare(String s) {
        if (s.length() != 2) return null;
        char fileChar = Character.toUpperCase(s.charAt(0));
        char rankChar = s.charAt(1);
        if (fileChar < 'A' || fileChar > 'H') return null;
        if (rankChar < '1' || rankChar > '8') return null;
        int col = fileChar - 'A';
        int row = rankChar - '1';
        return new Position(row, col);
    }

    public static final class ParsedMove {
        private final Position from;
        private final Position to;

        public ParsedMove(Position from, Position to) {
            this.from = from;
            this.to = to;
        }

        public Position getFrom() {
            return from;
        }

        public Position getTo() {
            return to;
        }
    }
}