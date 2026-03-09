package GameState;

import AudioPlayer.AudioPlayer;
import ui.MenuButton;

import java.awt.event.MouseEvent;
import Game.*;
public class State {
    protected GameController gameController;
    public State(GameController gameController) {
        this.gameController = gameController;
    }
    public GameController getGameController() {
        return gameController;
    }
    public Boolean isIn(MouseEvent e , MenuButton mb){
        return mb.getBounds().contains(e.getX(), e.getY());
    }
    public void setGameState(GameState gs){
        switch(gs){
            case MENU -> gameController.getAudioPlayer().playSong(AudioPlayer.MENU);
            case PLAYING -> gameController.getAudioPlayer().playSong(AudioPlayer.GAME);
        }
        GameState.state = gs;
    }
}
