package ui;

import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import utilz.Constants.PauseButton.*;
public class SoundButton extends PauseButton {
    private BufferedImage[][] soundsImg;
    private Boolean mouseOver = false, mousePressed = false;
    private Boolean muted = false;
    private int rowIndex = 0, colIndex = 0;
    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);
        loadSoundImgs();
    }
    private void loadSoundImgs() {
        BufferedImage soundImg = LoadSave.loadImage(LoadSave.SOUND_BUTTON);
        soundsImg = new BufferedImage[2][3];
        for (int j = 0; j < soundsImg.length; j++) {
            for ( int i = 0; i < soundsImg[j].length; i ++){
                soundsImg[j][i] = soundImg.getSubimage(i * Constants.PauseButton.SOUND_SIZE_DEFAULT_WIDTH,
                        j * Constants.PauseButton.SOUND_SIZE_DEFAULT_HEIGHT,
                        Constants.PauseButton.SOUND_SIZE_DEFAULT_WIDTH,
                        Constants.PauseButton.SOUND_SIZE_DEFAULT_HEIGHT);
            }
        }
    }
    public void render(Graphics g) {
        g.drawImage(soundsImg[rowIndex][colIndex], x, y,width,height,null);
    }
    public void update() {
        if (muted){
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }
        colIndex = 0;
        if (mouseOver){
            colIndex = 1;
        }
        if (mousePressed){
            colIndex = 2;
        }
    }
    public Boolean getMuted() {
        return muted;
    }

    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    public Boolean getMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(Boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public Boolean getMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(Boolean mouseOver) {
        this.mouseOver = mouseOver;
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
    }

}
