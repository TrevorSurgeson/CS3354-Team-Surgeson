package edu.txstate.chess;

import edu.txstate.chess.board.Board;
import edu.txstate.chess.pieces.Piece;
import edu.txstate.chess.util.InputParser;
import java.util.Scanner;

public class Game {

    private final Board board;
    private final Player whitePlayer;
    private final Player blackPlayer;
    private Piece.Color currentTurn;

    public Game() {
        this.board = new Board();
        this.whitePlayer = new Player(Piece.Color.WHITE);
        this.blackPlayer = new Player(Piece.Color.BLACK);
        this.currentTurn = Piece.Color.WHITE;
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                board.display();
                System.out.println();
                System.out.println("Current turn: " + (currentTurn == Piece.Color.WHITE ? "White" : "Black"));
                System.out.print("Enter move (e.g., E2 E4) or 'quit': ");
                String line = scanner.nextLine();
                if (line == null) {
                    break;
                }
                if (line.trim().equalsIgnoreCase("quit")) {
                    running = false;
                    continue;
                }
                InputParser.ParsedMove move = InputParser.parseMove(line);
                if (move == null) {
                    System.out.println("Invalid format. Use e.g. E2 E4.");
                    continue;
                }
                boolean ok = board.movePiece(move.getFrom(), move.getTo(), currentTurn);
                if (!ok) {
                    System.out.println("Illegal move for current player.");
                    continue;
                }
                currentTurn = currentTurn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
            }
        }
        System.out.println("Game ended.");
    }

    public static void main(String[] args) {
        new Game().start();
    }
}