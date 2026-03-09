package ObjectManager;

import BlueGolem.BlueGolem;
import GameState.Playing;
import Items.Hamburger;
import Items.Potion;
import Player.Player;

import java.awt.*;
import java.util.ArrayList;

public class ObjecManager {
    private ArrayList<Hamburger> hamburgers;
    private ArrayList<Potion> Potions;
    private Playing playing;
    public ObjecManager(Playing playing) {
        this.playing = playing;
        hamburgers = new ArrayList<>();
        Potions = new ArrayList<>();
    }
    public void addHamburger(String type, float x , float y) {
        Hamburger hamburger = new Hamburger(x,y,type,playing);
        hamburgers.add(hamburger);
    }
    public void addPotion(String type, float x , float y) {
        Potion potion = new Potion(x,y,type,playing);
        Potions.add(potion);
    }
    private void removeInactiveItems() {
        for (int i = hamburgers.size() - 1; i >= 0; i--) {
            if (!hamburgers.get(i).getActive()) {
                hamburgers.remove(i);
            }
        }

        for (int i = Potions.size() - 1; i >= 0; i--) {
            if (!Potions.get(i).getActive()) {
                Potions.remove(i);
            }
        }
    }
    public void update(float delta) {
        for (Hamburger hamburger : hamburgers) {
            hamburger.update(delta);
        }
        for (Potion potion : Potions) {
            potion.update(delta);
        }
        removeInactiveItems();
    }

    public void render(Graphics g, int offsetX, int offsetY) {
        for (Hamburger hamburger : hamburgers) {
            hamburger.render(g, offsetX, offsetY);
        }
        for (Potion potion : Potions) {
            potion.render(g, offsetX, offsetY);
        }
    }
    public void removeAll() {
        hamburgers.clear();
        Potions.clear();
    }
}
