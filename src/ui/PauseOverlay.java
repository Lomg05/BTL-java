package ui;

import Game.Game;
import GameState.GameState;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import GameState.Playing;
public class PauseOverlay {
    private BufferedImage background;
    private int bgX, bgY, bgW, bgH;
    private UrmButtom menuB, replayB, unpauseB;
    private Playing playing;
    private AudioOptions audioOptions;
    public PauseOverlay(Playing playing) {
        loadBackground();
        creatUrmButton();
        this.playing = playing;
        audioOptions = playing.getGameController().getAudioOptions();
    }


    private void creatUrmButton() {
        int menuX = (int)(bgX + 6 * Game.GAME_SCALE * 2.5);
        int replayX = (int)(bgX + 41 * Game.GAME_SCALE * 2.5);
        int unpauseX = (int)(bgX + 77 * Game.GAME_SCALE * 2.5);
        int bY = (int)(bgY + 113 * Game.GAME_SCALE * 2.5);

        menuB = new UrmButtom(menuX,bY, Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,2);
        replayB = new UrmButtom(replayX,bY, Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,1);
        unpauseB = new UrmButtom(unpauseX,bY, Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,0);
    }

    private void loadBackground() {
        background = LoadSave.loadImage(LoadSave.PAUSE_BACKGROUND);
        bgW = Constants.PauseButton.BACKGROUND_WIDTH;
        bgH = Constants.PauseButton.BACKGROUND_HEIGHT;
        bgX = Game.GAME_WIDTH / 2 - bgW / 2 ;
        bgY = 50;
    }
    public void update() {
        menuB.update();
        replayB.update();
        unpauseB.update();
        audioOptions.update();
    }
    public void render(Graphics g) {
        g.drawImage(background, bgX, bgY, bgW, bgH, null);
        menuB.render(g);
        replayB.render(g);
        unpauseB.render(g);
        audioOptions.render(g);
    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        replayB.setMouseOver(false);
        unpauseB.setMouseOver(false);
         if (isIn(e,menuB)){
            menuB.setMouseOver(true);
        } else if (isIn(e,replayB)){
            replayB.setMouseOver(true);
        } else if (isIn(e,unpauseB)){
            unpauseB.setMouseOver(true);
        } else {
            audioOptions.MouseMoved(e);
        }
    }
    public void mousePressed(MouseEvent e) {
        if (isIn(e,menuB)) {
            menuB.setMousePressed(true);
        } else if (isIn(e,replayB)) {
            replayB.setMousePressed(true);
        } else if (isIn(e,unpauseB)) {
            unpauseB.setMousePressed(true);
        } else {
            audioOptions.MousePressed(e);
        }
    }
    public void mouseReleased(MouseEvent e) {
         if (isIn(e,menuB)) {
            if (menuB.getMousePressed()) {
                menuB.setMousePressed(false);
                playing.setGameState(GameState.MENU);
                playing.unpause();
            }
        } else if (isIn(e,replayB)) {
            if (replayB.getMousePressed()) {
                replayB.setMousePressed(false);
                playing.replay();
                playing.unpause();
            }
        } else if (isIn(e,unpauseB)) {
            if (unpauseB.getMousePressed()) {
                unpauseB.setMousePressed(false);
                playing.unpause();
            }
        }
        menuB.resetBool();
        replayB.resetBool();
        unpauseB.resetBool();
        audioOptions.MouseReleased(e);
    }
    public void mouseDragged(MouseEvent e) {
        audioOptions.MouseDragged(e);
    }
    private Boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
