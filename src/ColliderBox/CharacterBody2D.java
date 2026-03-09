package ColliderBox;

import Levels.LevelManager;
import utilz.Physics;

import java.awt.*;

public class CharacterBody2D {
    protected float x, y;
    protected float velX = 0, velY = 0;
    protected LevelManager levelManager;
    protected Rectangle collisionBox;
    public CharacterBody2D(float x, float y , LevelManager levelManager) {
        this.x = x;
        this.y = y;
        this.levelManager = levelManager;
    }


    private void Collision(float delta){
        float newX = Math.round(x + velX * delta);
        float newY = Math.round(y + velY * delta);
        Boolean collideX = false;
        Boolean collideY = false;

        Rectangle furtureBoundsY = new Rectangle((int)x, (int)newY, collisionBox.width, collisionBox.height);
        for ( Rectangle tile : levelManager.getCurrentLevel().getTileBounds()){
            if (tile.intersects(furtureBoundsY)){
                collideY = true;
                String direction = Physics.VerticalCollision(furtureBoundsY,tile);
                if (direction.equals("bottom")){
                    newY = tile.y - collisionBox.height;
                    break;
                } else if (direction.equals("top")){
                    newY = tile.y + tile.height;
                }
            }
        }

        y = newY;
        if (collideY) velY = 0;

        Rectangle furtureBoundsX = new Rectangle((int)newX, (int)y, collisionBox.width, collisionBox.height);
        for ( Rectangle tile : levelManager.getCurrentLevel().getTileBounds()){
            if (tile.intersects(furtureBoundsX)){
                Rectangle intersection = tile.intersection(furtureBoundsX);
                collideX = true;
                String direction = Physics.HorizontalCollision(furtureBoundsX,tile);
                if (direction.equals("left")){
                    newX = tile.x + tile.width;
                } else if (direction.equals("right")){
                    newX = tile.x - collisionBox.width;
                }
                break;
            }
        }
        x = newX;
        if (collideX) velX = 0;
    }
    public Boolean checkCollisionFloor(){
        for ( Rectangle tiles : levelManager.getCurrentLevel().getTileBounds()){
            Rectangle tmp = new Rectangle((int)x,(int)y,collisionBox.width,collisionBox.height);
            tmp.y += 1;
            if ( tiles.intersects(tmp)){
                String dir = Physics.VerticalCollision(tmp,tiles);
                if (dir.equals("bottom")){
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkCollisionCelling(){
        for ( Rectangle tiles : levelManager.getCurrentLevel().getTileBounds()){
            Rectangle tmp = new Rectangle((int)x,(int)y,collisionBox.width,collisionBox.height);
            tmp.y -= 1;
            if ( tiles.intersects(tmp)){
                String dir = Physics.VerticalCollision(tmp,tiles);
                if (dir.equals("top")){
                    return true;
                }
            }
        }
        return false;
    }
    public Boolean checkCollisionLeftWall(){
        for ( Rectangle tiles : levelManager.getCurrentLevel().getTileBounds()){
            Rectangle tmp = new Rectangle((int)x,(int)y,collisionBox.width,collisionBox.height);
            tmp.x -= 1;
            if ( tiles.intersects(tmp)){
                String dir = Physics.HorizontalCollision(tmp,tiles);
                if (dir.equals("left")){
                    return true;
                }
            }
        }
        return false;
    }

    public Boolean checkCollisionRightWall(){
        for ( Rectangle tiles : levelManager.getCurrentLevel().getTileBounds()){
            Rectangle tmp = new Rectangle((int)x,(int)y,collisionBox.width,collisionBox.height);
            tmp.x += 1;
            if ( tiles.intersects(tmp)){
                String dir = Physics.HorizontalCollision(tmp,tiles);
                if (dir.equals("right")){
                    return true;
                }
            }
        }
        return false;
    }
    public void update(float delta){
        Collision(delta);
        collisionBox.setLocation((int)(x), (int)(y));
    }
}
