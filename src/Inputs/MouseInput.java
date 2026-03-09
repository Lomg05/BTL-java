package Inputs;

import Game.GamePanel;
import GameState.GameState;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener , MouseMotionListener {
    private GamePanel gamePanel;
    public MouseInput(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGameController().getMenu().MousePressed(e);
            case OPTIONS -> gamePanel.getGameController().getMenuOption().MousePressed(e);
            case PLAYING -> gamePanel.getGameController().getPlaying().MousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGameController().getMenu().MouseReleased(e);
            case OPTIONS -> gamePanel.getGameController().getMenuOption().MouseReleased(e);
            case PLAYING -> gamePanel.getGameController().getPlaying().MouseReleased(e);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGameController().getMenu().MouseDragged(e);
            case OPTIONS -> gamePanel.getGameController().getMenuOption().MouseDragged(e);
            case PLAYING -> gamePanel.getGameController().getPlaying().MouseDragged(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        switch (GameState.state) {
            case MENU -> gamePanel.getGameController().getMenu().MouseMoved(e);
            case OPTIONS -> gamePanel.getGameController().getMenuOption().MouseMoved(e);
            case PLAYING -> gamePanel.getGameController().getPlaying().MouseMoved(e);
        }
    }
}
