package Game;

import javax.swing.*;

public class GameWindow {
    private JFrame frame;
    public GameWindow(GamePanel gamePanel) {
        frame = new JFrame();
        frame.setTitle("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
