package GameState;

import Game.Game;
import Game.GameController;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButtom;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class MenuOption extends State implements StateMethods {
    private BufferedImage background;
    private int bgX, bgY, bgW, bgH;
    private UrmButtom menuB;
    private AudioOptions audioOptions;

    public MenuOption(GameController gameController) {
        super(gameController);
        loadBackground();
        creatUrmButton();
        audioOptions = gameController.getAudioOptions();
    }


    private void creatUrmButton() {
        int menuX = (int)(bgX + 41 * Game.GAME_SCALE * 2.5);
        int bY = (int)(bgY + 113 * Game.GAME_SCALE * 2.5);
        menuB = new UrmButtom(menuX,bY, Constants.URMButton.URM_SIZE_WIDTH, Constants.URMButton.URM_SIZE_HEIGHT,2);
    }

    private void loadBackground() {
        background = LoadSave.loadImage(LoadSave.PAUSE_BACKGROUND);
        bgW = Constants.PauseButton.BACKGROUND_WIDTH;
        bgH = Constants.PauseButton.BACKGROUND_HEIGHT;
        bgX = Game.GAME_WIDTH / 2 - bgW / 2 ;
        bgY = 50;
    }

    @Override
    public void update(float delta) {
        menuB.update();
        audioOptions.update();
        gameController.getMenu().updateFrame();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gameController.getMenu().getBackground(), 0,0,
                (int) (gameController.getMenu().getBackground().getWidth() * Game.GAME_SCALE * 1.3),
                (int) (gameController.getMenu().getBackground().getHeight() * Game.GAME_SCALE * 1.3),null);
        g.drawImage(background, bgX, bgY, bgW, bgH, null);
        menuB.render(g);
        audioOptions.render(g);
    }

    @Override
    public void MouseDragged(MouseEvent e) {
        audioOptions.MouseDragged(e);
    }

    @Override
    public void MousePressed(MouseEvent e) {
        if (isIn(e,menuB)) {
            menuB.setMousePressed(true);
        }  else {
            audioOptions.MousePressed(e);
        }
    }

    @Override
    public void MouseReleased(MouseEvent e) {
        if (isIn(e,menuB)) {
            if (menuB.getMousePressed()) {
                menuB.setMousePressed(false);
                GameState.state = GameState.MENU;
            }
        }
        menuB.resetBool();
        audioOptions.MouseReleased(e);
    }

    @Override
    public void MouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if (isIn(e,menuB)){
            menuB.setMouseOver(true);
        } else {
            audioOptions.MouseMoved(e);
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }

    private Boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
