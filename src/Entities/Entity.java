package Entities;

import ColliderBox.CharacterBody2D;
import Levels.LevelManager;

public abstract class Entity extends CharacterBody2D {
    public Entity(float x, float y , LevelManager levelManager) {
        super(x, y, levelManager);
    }
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }
}
