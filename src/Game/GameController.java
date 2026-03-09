package Game;
import AudioPlayer.AudioPlayer;
import GameState.GameState;
import GameState.Playing;
import GameState.Menu;
import GameState.MenuOption;
import ui.AudioOptions;

import java.awt.*;


public class GameController {
    private GamePanel gamePanel;
    private GameWindow gameWindow;
    private Menu menu;
    private Playing playing;
    private MenuOption menuOption;
    private AudioPlayer audioPlayer;
    private AudioOptions audioOptions;
    public GameController() {
        audioOptions = new AudioOptions(this);
        playing = new Playing(this);
        menu = new Menu(this);
        menuOption = new MenuOption(this);
        audioPlayer = new AudioPlayer();
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
    }

    public void draw(Graphics g) {
        switch (GameState.state){
            case MENU -> menu.render(g);
            case PLAYING -> playing.render(g);
            case OPTIONS -> menuOption.render(g);
            case QUIT -> System.exit(0);
        }
    }

    public void render(){
        gamePanel.repaint();
    }
    public void update(float delta){
        switch (GameState.state){
            case MENU -> menu.update(delta);
            case PLAYING -> playing.update(delta);
            case OPTIONS -> menuOption.update(delta);
            case QUIT -> System.exit(0);
        }
    }

    public Menu getMenu() {
        return menu;
    }
    public MenuOption getMenuOption() {return menuOption;}
    public Playing getPlaying() {
        return playing;
    }
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
}
