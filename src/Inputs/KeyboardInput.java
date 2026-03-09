package Inputs;
import Game.GamePanel;
import GameState.GameState;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInput implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGameController().getMenu().KeyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGameController().getPlaying().KeyPressed(e);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                gamePanel.getGameController().getMenu().KeyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGameController().getPlaying().KeyReleased(e);
                break;
        }
    }
}
