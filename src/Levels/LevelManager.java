package Levels;

import GameState.Playing;
import utilz.LoadSave;
import Game.Game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelManager {
    private BufferedImage[] level_sprites;
    private ArrayList<Level> levels;
    private int index = 0;
    private Playing playing;
    public LevelManager(Playing playing) {
        loadLevelSprite();
        levels = new ArrayList<>();
        this.playing = playing;
        loadAllLevels();
    }
    private void loadAllLevels(){
        Level level_1 = new Level(LoadSave.LEVEL_1,200,50,20);
        levels.add(level_1);
        Level level_2 = new Level(LoadSave.LEVEL_2,0,0,30);
        levels.add(level_2);
        Level level_3 = new Level(LoadSave.LEVEL_3,200,50,40);
        levels.add(level_3);
    }

    private void loadLevelSprite(){
        BufferedImage tile_map = LoadSave.loadImage(LoadSave.TILE_MAP_DATA);
        int rows = tile_map.getHeight() / Game.TILE_DEFAULT;
        int cols = tile_map.getWidth() / Game.TILE_DEFAULT;
        level_sprites = new BufferedImage[rows*cols];
        for ( int i = 0 ; i < rows ; i++){
            for ( int j = 0 ; j < cols ; j++){
                int index = i * cols + j;
                level_sprites[index] = tile_map.getSubimage(j * Game.TILE_DEFAULT, i * Game.TILE_DEFAULT, Game.TILE_DEFAULT, Game.TILE_DEFAULT);
            }
        }
    }
    public void completeLevel(){
        if (playing.getPlayer().getEnemy_kill_num() >= levels.get(index).getMisson() && index < levels.size() - 1){
            playing.setLevelComplete(true);
            playing.gameComplete = false;
        } else if (playing.getPlayer().getEnemy_kill_num() >= levels.get(index).getMisson() && index >= levels.size() - 1){
            playing.setLevelComplete(false);
            playing.gameComplete = true;
        } else {
            playing.setLevelComplete(false);
            playing.gameComplete = false;
        }
    }
    public void loadNextLevel(){
        index++;
        if ( index >= levels.size()){
            index = 0;
        }
        playing.resetAll();
        playing.setLevelComplete(false);
        playing.getEnemyManager().setSpawnPoint(levels.get(index).getEnemySpawnPoints());
        playing.getEnemyManager().addEnemy();

        if (playing.gameComplete){
            playing.gameComplete = false;
        }
    }
    public Level getCurrentLevel() {
        return levels.get(index);
    }
    public BufferedImage getTileGrid(int index){
        return level_sprites[index];
    }
    public void update(){
        completeLevel();
    }
    public void resetIndexWhenGameOver(){
        index = 0;
    }
}
