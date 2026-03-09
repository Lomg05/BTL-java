package ui;

import Game.*;
import utilz.Constants;

import java.awt.*;
import java.awt.event.MouseEvent;

public class AudioOptions {
    private VolumeButton soundVolumn;
    private VolumeButton sfxVolumn;
    private SoundButton musicButton , sfxButton;
    private int bgX, bgY;
    private GameController gameController;
    public AudioOptions(GameController gameController) {
        bgX = Game.GAME_WIDTH / 2 - Constants.PauseButton.BACKGROUND_WIDTH / 2;
        bgY = 50;
        this.gameController = gameController;
        creatVolumeButton();
        creatSoundButton();
    }
    private void creatVolumeButton() {
        int sliderX = (int)(bgX + 32 * Game.GAME_SCALE * 2.5);
        int sliderY = (int)(bgY + 61 * Game.GAME_SCALE * 2.5);
        int anotherY = (int)(bgY + 87 * Game.GAME_SCALE * 2.5);
        soundVolumn = new VolumeButton(sliderX, sliderY, Constants.VolumeButton.SLIDER_WIDTH, Constants.VolumeButton.VOLUME_SIZE_HEIGHT);
        sfxVolumn = new VolumeButton(sliderX,anotherY, Constants.VolumeButton.SLIDER_WIDTH, Constants.VolumeButton.VOLUME_SIZE_HEIGHT);
    }
    private void creatSoundButton() {
        int soundX = (int)(bgX + 71 * Game.GAME_SCALE * 2.5);
        int soundY = (int)(bgY + 8 * Game.GAME_SCALE * 2.5);
        int sfxY = (int)(bgY + 30 * Game.GAME_SCALE * 2.5);
        musicButton = new SoundButton(soundX,soundY, Constants.PauseButton.SOUND_SIZE_WIDTH, Constants.PauseButton.SOUND_SIZE_HEIGHT);
        sfxButton = new SoundButton(soundX, sfxY, Constants.PauseButton.SOUND_SIZE_WIDTH, Constants.PauseButton.SOUND_SIZE_HEIGHT);
    }
    public void update() {
        soundVolumn.update();
        sfxVolumn.update();
        sfxButton.update();
        musicButton.update();
    }
    public void render(Graphics g) {
        soundVolumn.render(g);
        sfxVolumn.render(g);
        sfxButton.render(g);
        musicButton.render(g);
    }
    public void MouseMoved(MouseEvent e) {
        soundVolumn.setMouseOver(false);
        sfxVolumn.setMouseOver(false);
        sfxButton.setMouseOver(false);
        musicButton.setMouseOver(false);
        if (isIn(e,soundVolumn)){
            soundVolumn.setMouseOver(true);
        } else if (isIn(e,sfxVolumn)){
            sfxVolumn.setMouseOver(true);
        } else if (isIn(e,musicButton)){
            musicButton.setMouseOver(true);
        } else if (isIn(e,sfxButton)){
            sfxButton.setMouseOver(true);
        }
    }
    public void MousePressed(MouseEvent e) {
        if (isIn(e,soundVolumn)) {
            soundVolumn.setMousePressed(true);
        } else if (isIn(e,sfxVolumn)) {
            sfxVolumn.setMousePressed(true);
        } else if (isIn(e,musicButton)) {
            musicButton.setMousePressed(true);
        } else if (isIn(e,sfxButton)) {
            sfxButton.setMousePressed(true);
        }
    }
    public void MouseReleased(MouseEvent e) {
        if (isIn(e,musicButton)) {
            musicButton.setMousePressed(false);
            musicButton.setMuted(!musicButton.getMuted());
            gameController.getAudioPlayer().toggleSongMute();
        } else if (isIn(e,sfxButton)) {
            sfxButton.setMousePressed(false);
            sfxButton.setMuted(!sfxButton.getMuted());
            gameController.getAudioPlayer().toggleEffectMute();
        }
        soundVolumn.resetBools();
        sfxVolumn.resetBools();
        musicButton.resetBools();
        sfxButton.resetBools();
    }
    public void MouseDragged(MouseEvent e) {
        if (soundVolumn.getMousePressed()) {
            soundVolumn.changeX(e.getX());
            float floatvalue = soundVolumn.getFloatvalue();
            gameController.getAudioPlayer().setSongVolume(floatvalue);
            if (floatvalue <= 0.1f) {
                musicButton.setMuted(true);
            } else {
                musicButton.setMuted(false);
            }
        } else if (sfxVolumn.getMousePressed()) {
            sfxVolumn.changeX(e.getX());
            float floatvalue = sfxVolumn.getFloatvalue();
            gameController.getAudioPlayer().setEffectVolume(floatvalue);
            if (floatvalue <= 0.1f) {
                sfxButton.setMuted(true);
            } else {
                sfxButton.setMuted(false);
            }
        }
    }
    private Boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}
