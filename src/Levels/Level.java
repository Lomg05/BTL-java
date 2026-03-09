package Levels;

import BlueGolem.BlueGolem;
import Game.Game;
import Player.Player;
import utilz.LoadSave;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Level {
    private MapInfo mapInfo;
    private int playerSpawnPointX;
    private int playerSpawnPointY;
    private int misson;
    public Level(String map_name,int playerSpawnPointX,int playerSpawnPointY, int misson) {
        mapInfo = LoadSave.getLevelData(map_name);
        mapInfo.Map_Width = mapInfo.tileInWidth * Game.MAP_TILE_SIZE;
        mapInfo.Map_Height = mapInfo.tileInHeight * Game.MAP_TILE_SIZE;
        this.playerSpawnPointX = playerSpawnPointX;
        this.playerSpawnPointY = playerSpawnPointY;
        this.misson = misson;
        setBoundsPosition();
    }

    private void setBoundsPosition(){
        for (Rectangle bound : mapInfo.bounds){
            bound.setLocation((int)(bound.x * Game.GAME_SCALE * 2),(int)(bound.y * Game.GAME_SCALE * 2));
            bound.setSize((int)(bound.width * Game.GAME_SCALE * 2),(int)(bound.height * Game.GAME_SCALE * 2));
        }
    }

    public Map<String,int[][]> getMap_data() {
        return mapInfo.data;
    }
    public int getMap_width() {
        return mapInfo.Map_Width;
    }
    public int getMap_height() {
        return mapInfo.Map_Height;
    }
    public List<Rectangle> getTileBounds() {
        return mapInfo.bounds;
    }
    public ArrayList<Point> getEnemySpawnPoints(){
        return new ArrayList<>(mapInfo.enemySpawnPoints);
    }
    public int getMisson() {
        return misson;
    }
    public int getPlayerSpawnPointX() {
        return playerSpawnPointX;
    }
    public int getPlayerSpawnPointY() {
        return playerSpawnPointY;
    }
}
