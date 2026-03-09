package Items;

import Game.Game;
import GameState.Playing;
import Player.Player;
import utilz.Constants;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Items {
    protected BufferedImage sprite;
    protected BufferedImage[][] potion;
    protected int aniTick = 0; protected int aniSpeed = 8; protected int aniFrame = 0;
    protected float x,y;
    protected String type;
    protected Rectangle bounds; protected Boolean bounceOnce = true; protected Boolean bounce = false ; protected int dir = 1;
    protected float bounceVelocity = 300.f; protected float yMax ; protected float yMin; float gravity = 1500.f;
    protected Boolean active = true;
    protected Boolean hoover = false; protected int hoverDir; protected int hooverOffset; protected float hoverTimer = 0.0f;
    protected float velocityY;
    protected int items_index; protected int aniTime;
    protected Playing playing;
    public Items(float x , float y , String type, Playing playing) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.playing = playing;
        yMax = this.y;
        InitSheet();
        InitAnimation();
        InitBound();
    }

    protected void InitSheet(){
        sprite = LoadSave.loadImage(LoadSave.ITEMS);
        if (type == "BLUE"){
            items_index = 0;
            aniTime = Constants.ItemAniConstants.BLUE_POTION;
        } else if (type == "RED"){
            items_index = 1;
            aniTime = Constants.ItemAniConstants.RED_POTION;
        } else if (type == "HAMBURGER"){
            items_index = 2;
            aniTime = Constants.ItemAniConstants.HAMBURGER;
        }
    }
    protected void InitBound(){
        bounds = new Rectangle((int) (9 * Game.GAME_SCALE * 1.5f), (int) (14 * Game.GAME_SCALE * 1.5f));
    }
    protected void InitAnimation(){
        int rows = sprite.getHeight() / 16;
        int cols = sprite.getWidth() / 12;
        potion = new BufferedImage[rows][cols];
        for ( int i = 0; i < rows; i++ ){
            for ( int j = 0; j < cols; j++ ){
                potion[i][j] = sprite.getSubimage(j * 12, i * 16, 12, 16);
            }
        }
    }
    protected void updateAnimation(int stateFrame){
        bounds.setLocation((int)x, (int)y);
        aniTick++;
        if (aniTick >= aniSpeed){
            aniFrame = (aniFrame + 1) % stateFrame;
            aniTick = 0;
        }
    }
    protected void drawAnimation(Graphics g , int offsetX , int offsetY){
        g.drawImage(potion[items_index][aniFrame], (int) (bounds.x - 2 * Game.GAME_SCALE * 1.5 - offsetX),
                (int) (bounds.y - 2 * Game.GAME_SCALE * 1.5 - offsetY),
                (int)(potion[0][0].getWidth() * Game.GAME_SCALE * 1.5f),
                (int)(potion[0][0].getHeight() * Game.GAME_SCALE * 1.5f),null);
    }

    protected void bounce(float delta){
        if (bounce) return;

        velocityY += gravity * delta;
        y += velocityY * delta;

        if (y >= yMax){

            if (bounceOnce){
                velocityY = -bounceVelocity;
                bounceOnce = false;
            } else {
                bounce = true;
                hoover = true;
            }
        }
    }
    protected void hoover(float delta){
        if (hoover) {
            hoverTimer += delta;
            hooverOffset = (int) (Math.sin(hoverTimer * 5) * 10);
        }
        bounds.y += hooverOffset - 4;
    }

    public Boolean getActive() {
        return active;
    }
}
