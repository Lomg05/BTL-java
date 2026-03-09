package Items;

import AudioPlayer.AudioPlayer;
import GameState.Playing;

import java.awt.*;

public class Hamburger extends Items{
    private float heal = 20;
    public Hamburger(float x, float y, String type, Playing playing) {
        super(x, y, type, playing);
    }
    protected void playerInteract(){
        if (playing.getPlayer().getBound().intersects(bounds) && active){
            playing.getGameController().getAudioPlayer().playPoolEffect(
                    playing.getGameController().getAudioPlayer().getName(AudioPlayer.PICK_ITEM)
            );
            playing.getPlayer().setHealApplied(true);
            playing.getPlayer().applyEffectFromHamburger(heal);
            active = false;
        }
    }
    public void update(float delta){
        updateAnimation(aniTime);
        bounce(delta);
        hoover(delta);
        playerInteract();
    }
    public void render(Graphics g , int offsetX , int offsetY){
        //g.setColor(Color.BLUE);
        //g.fillRect(bounds.x - offsetX, bounds.y-offsetY, bounds.width, bounds.height);
        drawAnimation(g , offsetX , offsetY);
    }
}
