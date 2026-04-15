package edu.txstate.chess.gui;

import javax.swing.*;
import java.awt.*;

public class SettingsDialog extends JDialog {
    private final JComboBox<String> lightColorBox;
    private final JComboBox<String> darkColorBox;
    private final JComboBox<String> sizeBox;
    private boolean applied;

    public SettingsDialog(JFrame parent) {
        super(parent, "Settings", true);
        setLayout(new GridLayout(4, 2, 10, 10));

        lightColorBox = new JComboBox<>(new String[]{"Classic Light", "Cream", "Light Gray", "Tan"});
        darkColorBox = new JComboBox<>(new String[]{"Classic Dark", "Brown", "Dark Gray", "Green"});
        sizeBox = new JComboBox<>(new String[]{"Small", "Medium", "Large"});
        JButton applyButton = new JButton("Apply");
        JButton cancelButton = new JButton("Cancel");

        applyButton.addActionListener(e -> {
            applied = true;
            setVisible(false);
        });
        cancelButton.addActionListener(e -> setVisible(false));

        add(new JLabel("Light Squares:"));
        add(lightColorBox);
        add(new JLabel("Dark Squares:"));
        add(darkColorBox);
        add(new JLabel("Board Size:"));
        add(sizeBox);
        add(applyButton);
        add(cancelButton);

        pack();
        setLocationRelativeTo(parent);
    }

    public boolean isApplied() {
        return applied;
    }

    public Color getLightColor() {
        return switch ((String) lightColorBox.getSelectedItem()) {
            case "Cream" -> new Color(245, 240, 220);
            case "Light Gray" -> new Color(220, 220, 220);
            case "Tan" -> new Color(210, 180, 140);
            default -> new Color(240, 217, 181);
        };
    }

    public Color getDarkColor() {
        return switch ((String) darkColorBox.getSelectedItem()) {
            case "Brown" -> new Color(139, 69, 19);
            case "Dark Gray" -> new Color(105, 105, 105);
            case "Green" -> new Color(85, 107, 47);
            default -> new Color(181, 136, 99);
        };
    }

    public int getBoardButtonSize() {
        return switch ((String) sizeBox.getSelectedItem()) {
            case "Small" -> 64;
            case "Large" -> 96;
            default -> 80;
        };
    }
}