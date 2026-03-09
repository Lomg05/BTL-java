package ui;

import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UrmButtom extends PauseButton{
    private BufferedImage[] imgs;
    private int rowIndex, index;
    private Boolean mouseOver = false, mousePressed = false;
    public UrmButtom(int x, int y, int width, int height , int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImg();
    }
    private void loadImg() {
        BufferedImage img = LoadSave.loadImage(LoadSave.URM_BUTTOM);
        imgs = new BufferedImage[3];
        for ( int i = 0; i < imgs.length; i++ ) {
            imgs[i] = img.getSubimage(i * Constants.URMButton.URM_SIZE_DEFAULT_WIDTH,
                    rowIndex * Constants.URMButton.URM_SIZE_DEFAULT_HEIGHT,
                    Constants.URMButton.URM_SIZE_DEFAULT_WIDTH, Constants.URMButton.URM_SIZE_DEFAULT_HEIGHT);
        }
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
    public void render(Graphics g) {
        g.drawImage(imgs[index], x, y, width, height, null);
    }

    public void resetBool(){
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
}
