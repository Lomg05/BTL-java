package ui;

import Game.Game;
import GameState.GameState;
import GameState.Playing;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOverOverlay {
    private Playing playing;
    private UrmButtom menuButton, nextButton;
    private BufferedImage img;
    private int bgX, bgY, bgW, bgH;
    public GameOverOverlay(Playing playing) {
        this.playing = playing;
        initImage();
        initButton();
    }

    private void initButton() {
        int menuX = (int) (bgX + (10 * Game.GAME_SCALE * 2.5));
        int nextX = (int) (bgX + (72 * Game.GAME_SCALE * 2.5));
        int menuY = (int) (bgY + (57 * Game.GAME_SCALE * 2.5));
        menuButton = new UrmButtom(menuX,menuY, Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,2);
        nextButton = new UrmButtom(nextX,menuY,Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,1);
    }
    private void initImage() {
        img = LoadSave.loadImage(LoadSave.GAME_OVER);
        bgW = (int) (img.getWidth() * Game.GAME_SCALE * 2.5);
        bgH = (int) (img.getHeight() * Game.GAME_SCALE * 2.5);
        bgX = Game.GAME_WIDTH / 2 - bgW/ 2;
        bgY = (int) (100 * Game.GAME_SCALE);
    }
    public void render(Graphics g) {
        g.drawImage(img,bgX,bgY,bgW,bgH,null);
        menuButton.render(g);
        nextButton.render(g);
    }
    public void update() {
        menuButton.update();
        nextButton.update();
    }
    public void MouseMove(MouseEvent e) {
        nextButton.setMouseOver(false);
        menuButton.setMouseOver(false);

        if (isIn(nextButton,e)){
            nextButton.setMouseOver(true);
        } else if (isIn(menuButton,e)){
            menuButton.setMouseOver(true);
        }
    }
    public void MousePressed(MouseEvent e) {
        if (isIn(nextButton,e)){
            nextButton.setMousePressed(true);
        } else if (isIn(menuButton,e)){
            menuButton.setMousePressed(true);
        }
    }
    public void MouseReleased(MouseEvent e) {
        if (isIn(nextButton,e)){
            if (nextButton.getMousePressed()){
                playing.replay();
                playing.setGameState(GameState.PLAYING);
            }
        } else if (isIn(menuButton,e)){
            if (menuButton.getMousePressed()){
                playing.replay();
                playing.setGameState(GameState.MENU);
            }
        }
        menuButton.resetBool();
        nextButton.resetBool();
    }
    private Boolean isIn(UrmButtom button, MouseEvent e) {
        return button.getBounds().contains(e.getX(), e.getY());
    }
}
