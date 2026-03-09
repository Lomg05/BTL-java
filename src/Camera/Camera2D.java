package Camera;

import BlueGolem.BlueGolem;
import EnemyManager.EnemyManager;
import Player.Player;
import Game.Game;
import Levels.LevelManager;
import ui.TextDamePool;
import utilz.Constants;
import utilz.LoadSave;
import utilz.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public class Camera2D {
    private int CameraX, CameraY;
    private int width = Game.GAME_WIDTH;
    private int height = Game.GAME_HEIGHT;
    private int xLimit,yLimit;
    private int maxX;
    private int maxY;
    private Player player;
    private EnemyManager enemyManager;
    private LevelManager level;
    private int offsetX, offsetY;
    private BufferedImage background;
    private BufferedImage treefront;
    private BufferedImage treeback;
    private TextDamePool textDamePool;
    public Camera2D(Player player , LevelManager level , EnemyManager enemyManager, TextDamePool pool) {
        System.out.println(maxX + " " + maxY);
        this.player = player;
        this.level = level;
        this.enemyManager = enemyManager;
        this.CameraX = player.getBound().x;
        this.CameraY = player.getBound().y;
        maxX = level.getCurrentLevel().getMap_width() - width;
        maxY = level.getCurrentLevel().getMap_height()- height;
        xLimit = Game.GAME_WIDTH / 2;
        yLimit = Game.GAME_HEIGHT / 2;
        InitBackGround();
        this.textDamePool = pool;
    }
    private void InitBackGround(){
        background = LoadSave.loadImage(LoadSave.BACKGROUND_COLOR);
        treeback = LoadSave.loadImage(LoadSave.TREE_BACK);
        treefront = LoadSave.loadImage(LoadSave.TREE_FRONT);
    }
    private void renderBackground(Graphics g, int offsetX, int offsetY){
        g.drawImage(background,0,0, (int) (Constants.Background.BACKGROUND_COLOR_WIDTH * 2),
                (int) (Constants.Background.BACKGROUND_COLOR_HEIGHT * 2),null);
        for ( int i = 0 ; i < 3 ; ++i) {
            g.drawImage(treeback, (int) (i * (int) (Constants.Background.BACKGROUND_COLOR_WIDTH * 2) - offsetX), 0, (int) (Constants.Background.BACKGROUND_COLOR_WIDTH * 2),
                    (int) (Constants.Background.BACKGROUND_COLOR_HEIGHT * 2), null);
        }
        for ( int i = 0 ; i < 3 ; ++i) {
            g.drawImage(treefront, (int) (i * (int) (Constants.Background.BACKGROUND_COLOR_WIDTH * 2) - offsetX * 0.8), 0, (int) (Constants.Background.BACKGROUND_COLOR_WIDTH * 2),
                    (int) (Constants.Background.BACKGROUND_COLOR_HEIGHT * 2), null);
        }
    }
    public void update() {
        CameraFollowPlayer();
    }

    public void render(Graphics g) {
        renderBackground(g, offsetX, offsetY);
        DrawMapInViewPort(g);
        DebugBox(g);
        DebugHitBox(g);
        drawPlayerInViewPort(g);
    }


    private void CameraFollowPlayer() {
        CameraX = player.getBound().x;
        CameraY = player.getBound().y;
        int tmp_x = 0;
        int tmp_y = 0;
        if (CameraX > xLimit) {
            tmp_x = CameraX - xLimit;
        }

        if (CameraY > yLimit) {
            tmp_y = CameraY - yLimit;
        }

        if (tmp_x > maxX) {
            tmp_x = maxX;
        }
        if (tmp_y > maxY) {
            tmp_y = maxY;
        }

        offsetX = (int)Maths.Lerp(offsetX, (float)tmp_x, 0.05f);
        offsetY = (int)Maths.Lerp(offsetY, (float)tmp_y, 0.05f);
    }

    private void DebugBox(Graphics g) {
        g.setColor(player.bound_color);
        g.fillRect((int)(player.getBound().x - offsetX),
                (int)(player.getBound().y - offsetY),
                player.getBound().width,
                player.getBound().height);
    }

     private void DebugHitBox(Graphics g) {
        g.setColor(player.hitbox_color);
        g.fillRect(player.getHitbox().x - offsetX, player.getHitbox().y - offsetY, player.getHitbox().width, player.getHitbox().height);

     }
    private void DrawMapInViewPort(Graphics g) {
        Map<String, int[][]> map = level.getCurrentLevel().getMap_data();
        List<Rectangle> bounds = level.getCurrentLevel().getTileBounds();
        //Draw map
        for (String layer : map.keySet()) {
            int[][] layer_data = map.get(layer);
            for ( int i = 0 ; i < layer_data.length ; i++ ) {
                for ( int j = 0 ; j < layer_data[i].length ; j++) {
                    g.drawImage(level.getTileGrid(layer_data[i][j]),
                            j * Game.MAP_TILE_SIZE - offsetX,
                            i * Game.MAP_TILE_SIZE - offsetY,
                            Game.MAP_TILE_SIZE,
                            Game.MAP_TILE_SIZE, null);
                }
            }
        }
        //Debug
        for (Rectangle bound : bounds) {
            g.setColor(new Color(0,0,130,80));
            g.fillRect(bound.x - offsetX, bound.y - offsetY, bound.width, bound.height);
        }
    }

    private void drawPlayerInViewPort(Graphics g) {
        enemyManager.render(g,offsetX,offsetY);
        player.render(g,offsetX,offsetY);
        textDamePool.render(g,offsetX,offsetY);
    }
    public float getOffsetX() {
        return offsetX;
    }
    public float getOffsetY() {
        return offsetY;
    }
}
