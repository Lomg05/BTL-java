package ui;

import GameState.GameState;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private GameState gameState;
    private int xOffsetCenter = Constants.MenuButton.MENU_BUTTON_WIDTH / 2;
    private BufferedImage[] images;
    private Boolean mouseOver = false, mousePressed = false;
    private Rectangle bounds;
    public MenuButton(int x , int y , int rowIndex, GameState state) {
        this.xPos = x;
        this.yPos = y;
        this.rowIndex = rowIndex;
        this.gameState = state;
        loadImages();
        InitBounds();
    }
    private void InitBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, Constants.MenuButton.MENU_BUTTON_WIDTH, Constants.MenuButton.MENU_BUTTON_HEIGHT);
    }
    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage img = LoadSave.loadImage(LoadSave.MENU_BUTTON);
        for ( int i = 0 ; i < images.length; i++){
            images[i] = img.getSubimage(i * Constants.MenuButton.MENU_BUTTON_DEFAULT_WIDTH, rowIndex * Constants.MenuButton.MENU_BUTTON_DEFAULT_HEIGHT,
                    Constants.MenuButton.MENU_BUTTON_DEFAULT_WIDTH,
                    Constants.MenuButton.MENU_BUTTON_DEFAULT_HEIGHT);
        }

    }
    public void render(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos,
                Constants.MenuButton.MENU_BUTTON_WIDTH, Constants.MenuButton.MENU_BUTTON_HEIGHT, null);
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

    public Boolean getMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(Boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public Boolean getMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(Boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
    public void applyGameState() {
        GameState.state = gameState;
    }
    public void resetBoolean(){
        mouseOver = false;
        mousePressed = false;
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public GameState getGameState() {
        return gameState;
    }
}
