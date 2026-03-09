package GameState;

import AudioPlayer.AudioPlayer;
import Game.*;
import ui.MenuButton;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Menu extends State implements StateMethods{
    private MenuButton[] buttons;
    private BufferedImage background_button;
    private BufferedImage background;
    private BufferedImage[] background_animated;
    private int aniTick = 0, aniFrame = 0, aniSpeed = 2, aniTime = 118;
    public Menu(GameController gameController){
        super(gameController);
        buttons = new MenuButton[3];
        loadButtons();
        loadBackground();
    }

    private void loadButtons(){
        buttons[0] = new MenuButton(Game.GAME_WIDTH / 2 + 10,(int)(105 * Game.GAME_SCALE * 1.5),0 , GameState.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH / 2 + 10,(int)(150 * Game.GAME_SCALE * 1.5),1 , GameState.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH / 2 + 10,(int)(195 * Game.GAME_SCALE * 1.5),2 , GameState.QUIT);
    }
    private void loadBackground(){
        background_button = LoadSave.loadImage(LoadSave.MENU_BUTTON_BACKGROUND);
        background = LoadSave.loadImage(LoadSave.MENU_BACKGROUND);
        int cols = background.getWidth() / 620;
        background_animated = new BufferedImage[cols];
        for(int i = 0; i < background_animated.length; i++){
            background_animated[i] = background.getSubimage(i * 620, 0, 620, 349);
        }
    }
    public void updateFrame(){
        aniTick++;
        if(aniTick >= aniSpeed){
            aniTick = 0;
            aniFrame = (aniFrame + 1) % aniTime;
        }
    }
    @Override
    public void update(float delta) {
        updateFrame();
        for (MenuButton button : buttons) {
            button.update();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(background_animated[aniFrame],0,0,
                (int) (background_animated[0].getWidth() * Game.GAME_SCALE * 1.3),
                (int) (background_animated[0].getHeight() * Game.GAME_SCALE * 1.3),null);
        g.drawImage(background_button,
                (int) (Game.GAME_WIDTH / 2 - background_button.getWidth() / 2 * Game.GAME_SCALE * 3),
                50, (int) (background_button.getWidth() * Game.GAME_SCALE * 3),
                (int) (background_button.getHeight() * Game.GAME_SCALE * 3), null);
        for (MenuButton button : buttons) {
            button.render(g);
        }
    }

    @Override
    public void MouseDragged(MouseEvent e) {

    }

    @Override
    public void MousePressed(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isIn(e,button)) {
                button.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void MouseReleased(MouseEvent e) {
        for (MenuButton button : buttons) {
            if (isIn(e,button)) {
                if (button.getMousePressed()) {
                    button.applyGameState();
                }
                if (button.getGameState() == GameState.PLAYING) {
                    gameController.getAudioPlayer().playSong(AudioPlayer.GAME);
                }
                break;
            }
        }
        for (MenuButton button : buttons) {
            button.resetBoolean();
        }
    }

    @Override
    public void MouseMoved(MouseEvent e) {
        for (MenuButton button : buttons) {
            button.setMouseOver(false);
        }
        for (MenuButton button : buttons) {
            if (isIn(e,button)) {
                button.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {

    }

    @Override
    public void KeyReleased(KeyEvent e) {

    }
    public BufferedImage getBackground(){
        return background_animated[aniFrame];
    }
}
