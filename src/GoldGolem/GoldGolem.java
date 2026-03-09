package GoldGolem;

import BlueGolem.BlueGolem;
import Levels.LevelManager;
import Player.Player;
import ui.TextDamePool;
import utilz.LoadSave;
import utilz.Maths;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GoldGolem extends BlueGolem {
    public GoldGolem(float x, float y, LevelManager levelManager, Player player, TextDamePool pool) {
        super(x, y, levelManager, player, pool);
        maxHealth = 350; currentHealth = maxHealth;
        damage = 60;
        loadSpriteSheet();
        loadAnimations();
    }
    protected void loadSpriteSheet() {
        sprite_sheet = LoadSave.loadImage(LoadSave.GOLDGOLEM);
    }
    private void loadAnimations() {
        int rows = sprite_sheet.getHeight() / 64;
        int cols = sprite_sheet.getWidth() / 90;
        animations = new BufferedImage[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                animations[i][j] = sprite_sheet.getSubimage(j * 90, i * 64, 90, 64);
            }
        }
    }
}
