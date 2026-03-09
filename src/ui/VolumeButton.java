package ui;

import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class VolumeButton extends PauseButton {
    private BufferedImage[] imgs;
    private BufferedImage slider;
    private int index =0 ;
    private Boolean mouseOver = false, mousePressed = false;
    private int buttonX, minX, maxX;
    private float floatvalue;
    public VolumeButton(int x, int y, int width, int height) {
        super(x + width / 2, y, Constants.VolumeButton.VOLUME_SIZE_WIDTH, height);
        bounds.x -=  Constants.VolumeButton.VOLUME_SIZE_WIDTH / 2;
        buttonX = x + width/ 2;
        this.x = x;
        this.width = width;
        minX = x + Constants.VolumeButton.VOLUME_SIZE_WIDTH / 2;
        maxX = x + width - Constants.VolumeButton.VOLUME_SIZE_WIDTH / 2 - 1;
        loadImages();
    }
    private void loadImages() {
        BufferedImage img = LoadSave.loadImage(LoadSave.VOLUME);
        imgs = new BufferedImage[3];
        for ( int i = 0; i < imgs.length; i++ ) {
            imgs[i] = img.getSubimage(i * Constants.VolumeButton.VOLUME_SIZE_DEFAULT_WIDTH, 0,
                    Constants.VolumeButton.VOLUME_SIZE_DEFAULT_WIDTH, Constants.VolumeButton.VOLUME_SIZE_DEFAULT_HEIGHT);
        }
        slider = LoadSave.loadImage(LoadSave.SLIDER);
    }
    public void update() {
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
        }
    }
    public void changeX(int x) {
        if (x < minX) {
            buttonX = minX;
        } else if (x > maxX) {
            buttonX = maxX;
        } else {
            buttonX = x;
        }
        updatefloatValue();
        bounds.x = buttonX - Constants.VolumeButton.VOLUME_SIZE_WIDTH / 2;
    }

    private void updatefloatValue() {
        float range = maxX - minX;
        float value = buttonX - minX;
        floatvalue = value / range;
    }

    public void render(Graphics g) {
        g.drawImage(slider, x, y, width, height, null);
        g.drawImage(imgs[index], buttonX - Constants.VolumeButton.VOLUME_SIZE_WIDTH / 2, y, Constants.VolumeButton.VOLUME_SIZE_WIDTH, height, null);
    }
    public void resetBools(){
        mouseOver = false;
        mousePressed = false;
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
    public float getFloatvalue() {
        return floatvalue;
    }
}
