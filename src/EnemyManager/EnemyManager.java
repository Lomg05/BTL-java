package EnemyManager;

import BlueGolem.BlueGolem;
import Game.Game;
import GameState.Playing;
import GoldGolem.GoldGolem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EnemyManager {
    private ArrayList<BlueGolem> blueGolems;
    private ArrayList<GoldGolem> goldGolems;
    private float spawnTimer = 0.0f; private ArrayList<Point> spawnPoint;
    private final ArrayList<BlueGolem> toRemoveBlue = new ArrayList<>();
    private final ArrayList<GoldGolem> toRemoveGold = new ArrayList<>();
    private Random rand = new Random();
    private Boolean spawned = false;
    private Playing playing;
    public EnemyManager(Playing playing) {
        this.playing = playing;
        spawnPoint = new ArrayList<>();
        rand = new Random();
        blueGolems = new ArrayList<>();
        goldGolems = new ArrayList<>();
        spawnPoint = playing.getLevelManager().getCurrentLevel().getEnemySpawnPoints();
        addEnemy();
    }
    public void update(float delta) {
        updateEnemy(delta);
        for (BlueGolem blueGolem : new ArrayList<>(blueGolems) ) {
            blueGolem.update(delta);
        }
        for (GoldGolem goldGolem : new ArrayList<>(goldGolems) ) {
            goldGolem.update(delta);

        }
        removeEnemy();
        playing.getObjecManager().update(delta);
    }
    public void render(Graphics g , int offsetX , int offsetY) {
        ArrayList<BlueGolem> copy = new ArrayList<>(blueGolems);
        for (BlueGolem blueGolem : copy) {
            blueGolem.render(g, offsetX, offsetY);
        }
        for (GoldGolem goldGolem : goldGolems) {
            goldGolem.render(g, offsetX, offsetY);
        }
        playing.getObjecManager().render(g, offsetX, offsetY);
    }
    public void addEnemy() {
        if (spawned) return;
        for (Point point : spawnPoint) {
            int x = (int) (point.x * Game.GAME_SCALE * 2);
            int y = (int) (point.y * Game.GAME_SCALE * 2);
            int indx = rand.nextInt(2);
            if (indx == 0) {
                BlueGolem blueGolem = new BlueGolem(x, y + 25, playing.getLevelManager(), playing.getPlayer(), playing.getPool());
                blueGolems.add(blueGolem);
            } else if (indx == 1) {
                GoldGolem goldGolem = new GoldGolem(x, y + 25, playing.getLevelManager(), playing.getPlayer(), playing.getPool());
                goldGolems.add(goldGolem);
            }
        }
        spawned = true;
    }

    private void updateEnemy(float delta){
        spawnTimer += delta * 2;
        if (blueGolems.size() + goldGolems.size() < spawnPoint.size()){
            if (spawnTimer > 20.0f) {
                int index = rand.nextInt(spawnPoint.size());
                Point point = spawnPoint.get(index);
                int x = (int) (point.x * Game.GAME_SCALE * 2);
                int y = (int) (point.y * Game.GAME_SCALE * 2);
                int idx = rand.nextInt(2);
                if (idx == 0) {
                    BlueGolem blueGolem = new BlueGolem(x, y + 25, playing.getLevelManager(),
                            playing.getPlayer(), playing.getPool());
                    blueGolems.add(blueGolem);
                } else if (idx == 1) {
                    GoldGolem goldGolem = new GoldGolem(x, y + 25,
                            playing.getLevelManager(), playing.getPlayer(), playing.getPool());
                    goldGolems.add(goldGolem);
                }
                spawnTimer = 0.0f;
            }
        }
    }
    private void removeEnemy() {
        toRemoveBlue.clear();
        toRemoveGold.clear();
        for (int i = blueGolems.size() - 1; i >= 0; i--) {
            BlueGolem g = blueGolems.get(i);
            if (g.getFinisedDeath()) {
                playing.getPlayer().setEnemy_kill_num(playing.getPlayer().getEnemy_kill_num() + 1);
                int random = rand.nextInt(4);
                if (random == 0) {
                    playing.getObjecManager().addPotion("BLUE",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                } else if (random == 1) {
                    playing.getObjecManager().addPotion("RED",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                } else if (random == 2) {
                    playing.getObjecManager().addHamburger("HAMBURGER",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                }
                toRemoveBlue.add(g);
            }
        }
        for (int i = goldGolems.size() - 1; i >= 0; i--) {
            GoldGolem g = goldGolems.get(i);
            if (g.getFinisedDeath()) {
                playing.getPlayer().setEnemy_kill_num(playing.getPlayer().getEnemy_kill_num() + 1);
                int random = rand.nextInt(4);
                if (random == 0) {
                    playing.getObjecManager().addPotion("BLUE",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                } else if (random == 1) {
                    playing.getObjecManager().addPotion("RED",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                } else if (random == 2) {
                    playing.getObjecManager().addHamburger("HAMBURGER",g.getBounds().x + g.getBounds().width / 2,
                            g.getBounds().y + g.getBounds().height / 2);
                }
                toRemoveGold.add(g);
            }
        }
        blueGolems.removeAll(toRemoveBlue);
        goldGolems.removeAll(toRemoveGold);
    }
    public void removeAll() {
        toRemoveBlue.clear();
        toRemoveGold.clear();
        blueGolems.clear();
        goldGolems.clear();
        spawnPoint.clear();
        playing.getObjecManager().removeAll();
        spawned = false;
    }
    public void setSpawnPoint(ArrayList<Point> spawnPoint) {
        this.spawnPoint = spawnPoint;
    }
}