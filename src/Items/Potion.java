package Items;

import AudioPlayer.AudioPlayer;
import GameState.*;

import java.awt.*;

public class Potion extends Items{
    //Effect
    private float scale_damage = 0.2f; private float timeEffectDamage = 10.0f;
    private float scale_heal = 0.5f; private float timeEffectHeal = 7.0f;
    public Potion(float x, float y, String type , Playing playing) {
        super(x, y, type, playing);
    }
    protected void playerInteract(){
        if (playing.getPlayer().getBound().intersects(bounds)){
            if (type == "BLUE"){
                playing.getGameController().getAudioPlayer().playPoolEffect(
                        playing.getGameController().getAudioPlayer().getName(AudioPlayer.PICK_ITEM)
                );
                playing.getPlayer().applyEffectFromPotion("BLUE", scale_heal, timeEffectHeal);
                playing.getPlayer().setHealingApplied(true);
            } else if (type == "RED"){
                playing.getGameController().getAudioPlayer().playPoolEffect(
                        playing.getGameController().getAudioPlayer().getName(AudioPlayer.PICK_ITEM)
                );
                playing.getPlayer().applyEffectFromPotion("RED",scale_damage, timeEffectDamage);
                playing.getPlayer().setDamageApplied(true);
            }
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