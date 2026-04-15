package edu.txstate.chess.gui;

import edu.txstate.chess.board.*;
import edu.txstate.chess.pieces.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class ChessGUI extends JFrame {
    private final Board board;
    private Piece.Color currentTurn;
    private final JButton[][] buttons;
    private Position selectedPosition;
    private final JLabel statusLabel;
    private java.awt.Color lightSquareColor;
    private java.awt.Color darkSquareColor;
    private int buttonSize;

    public ChessGUI() {
        board = new Board();
        currentTurn = Piece.Color.WHITE;
        buttons = new JButton[8][8];
        selectedPosition = null;
        statusLabel = new JLabel("Current turn: White");
        lightSquareColor = new java.awt.Color(240, 217, 181);
        darkSquareColor = new java.awt.Color(181, 136, 99);
        buttonSize = 80;

        setTitle("Chess Game GUI - Phase 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setJMenuBar(createMenuBar());
        add(statusLabel, BorderLayout.NORTH);
        add(createBoardPanel(), BorderLayout.CENTER);

        refreshBoard();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem saveGame = new JMenuItem("Save Game");
        JMenuItem loadGame = new JMenuItem("Load Game");
        newGame.addActionListener(e -> newGame());
        saveGame.addActionListener(e -> saveGame());
        loadGame.addActionListener(e -> loadGame());
        gameMenu.add(newGame);
        gameMenu.add(saveGame);
        gameMenu.add(loadGame);

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem openSettings = new JMenuItem("Customize Board");
        openSettings.addActionListener(e -> openSettings());
        settingsMenu.add(openSettings);

        menuBar.add(gameMenu);
        menuBar.add(settingsMenu);
        return menuBar;
    }

    private JPanel createBoardPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 8));
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(buttonSize, buttonSize));
                button.setFocusPainted(false);
                button.setFont(new Font("Arial", Font.BOLD, 24));
                final int r = row;
                final int c = col;
                button.addActionListener(e -> handleSquareClick(r, c));
                buttons[row][col] = button;
                panel.add(button);
            }
        }
        return panel;
    }

    private void handleSquareClick(int row, int col) {
        Position clicked = new Position(row, col);
        Piece piece = board.getPiece(clicked);

        if (selectedPosition == null) {
            if (piece == null || piece.getColor() != currentTurn) {
                return;
            }
            selectedPosition = clicked;
            refreshBoard();
            return;
        }

        MoveResult result = board.movePiece(selectedPosition, clicked, currentTurn);
        if (!result.isSuccess()) {
            if (piece != null && piece.getColor() == currentTurn) {
                selectedPosition = clicked;
                refreshBoard();
            } else {
                JOptionPane.showMessageDialog(this, result.getMessage());
            }
            return;
        }

        selectedPosition = null;
        currentTurn = currentTurn == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
        refreshBoard();
        evaluateGameState();
    }

    private void evaluateGameState() {
        if (board.isCheckmate(currentTurn)) {
            String winner = currentTurn == Piece.Color.WHITE ? "Black" : "White";
            JOptionPane.showMessageDialog(this, "Checkmate! " + winner + " wins.");
            return;
        }
        if (board.isStalemate(currentTurn)) {
            JOptionPane.showMessageDialog(this, "Stalemate! The game is a draw.");
            return;
        }
        if (board.isInCheck(currentTurn)) {
            JOptionPane.showMessageDialog(this, (currentTurn == Piece.Color.WHITE ? "White" : "Black") + " is in check.");
        }
    }

    private void newGame() {
        board.initialize();
        currentTurn = Piece.Color.WHITE;
        selectedPosition = null;
        refreshBoard();
    }

    private void saveGame() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(chooser.getSelectedFile()))) {
            out.writeObject(board.saveState(currentTurn));
            JOptionPane.showMessageDialog(this, "Game saved successfully.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving game.");
        }
    }

    private void loadGame() {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()))) {
            BoardState state = (BoardState) in.readObject();
            board.loadState(state);
            currentTurn = state.getCurrentTurn();
            selectedPosition = null;
            refreshBoard();
            JOptionPane.showMessageDialog(this, "Game loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error loading game.");
        }
    }

    private void openSettings() {
        SettingsDialog dialog = new SettingsDialog(this);
        dialog.setVisible(true);
        if (dialog.isApplied()) {
            lightSquareColor = dialog.getLightColor();
            darkSquareColor = dialog.getDarkColor();
            buttonSize = dialog.getBoardButtonSize();
            for (int r = 0; r < 8; r++) {
                for (int c = 0; c < 8; c++) {
                    buttons[r][c].setPreferredSize(new Dimension(buttonSize, buttonSize));
                }
            }
            pack();
            refreshBoard();
        }
    }

    private void refreshBoard() {
        for (int row = 7; row >= 0; row--) {
            for (int col = 0; col < 8; col++) {
                JButton button = buttons[row][col];
                Piece piece = board.getPiece(row, col);
                button.setText(piece == null ? "" : piece.getSymbol());
                button.setBackground((row + col) % 2 == 0 ? lightSquareColor : darkSquareColor);
                if (selectedPosition != null && selectedPosition.getRow() == row && selectedPosition.getCol() == col) {
                    button.setBackground(Color.YELLOW);
                }
            }
        }
        String turnText = currentTurn == Piece.Color.WHITE ? "White" : "Black";
        if (board.isInCheck(currentTurn)) {
            statusLabel.setText("Current turn: " + turnText + " (CHECK)");
        } else {
            statusLabel.setText("Current turn: " + turnText);
        }
    }
}