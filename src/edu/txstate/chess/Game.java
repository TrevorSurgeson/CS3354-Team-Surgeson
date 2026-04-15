package edu.txstate.chess;

import edu.txstate.chess.gui.ChessGUI;
import javax.swing.SwingUtilities;

public class Game {
    public void start() {
        SwingUtilities.invokeLater(ChessGUI::new);
    }

    public static void main(String[] args) {
        new Game().start();
    }
}