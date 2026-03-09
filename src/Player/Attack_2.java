package Player;

import AudioPlayer.AudioPlayer;
import Levels.LevelManager;
import utilz.Constants;

import java.awt.*;

public class Attack_2 extends PlayerState{
    public Attack_2(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }
    @Override
    protected void update(float delta) {
        if (player.getIntersect) {
            player.currentHealth += player.healScale * player.damage;
            player.healInDameDeal = (int) (player.healScale * player.damage);
            if (player.currentHealth > player.maxHealth) player.currentHealth = player.maxHealth;
        } else {
            player.healInDameDeal = 0;
        }
        player.setLockDirection(true);
        player.setFinishedAttack(false);
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void onEnter() {
        player.damage = (int) (player.default_dame_2 + player.damageScale * player.default_dame_2);
        player.playing.getGameController().getAudioPlayer().playPoolEffect(AudioPlayer.ATTACK_2);
        playAnimation(Constants.PlayerConstants.ATTACK_2, Constants.PlayerAniConstants.ATTACK_2);
        player.setHitbox_active(false);
        aniSpeed = (int) ( 5 - 5 * player.damageScale);
    }

    public void updateState(float delta) {
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
        }
        if (aniFrame >= 3){
            player.setHitbox_active(true);
        } else {
            player.setHitbox_active(false);
        }

        if (finished){
            player.setFinishedAttack(true);
            if (player.getAttack_stage() == 1){
                playerStateMachine.Change_to_next_state(playerStateMachine.Attack_1);
            } else {
                if (player.getLeft() || player.getRight()){
                    playerStateMachine.Change_to_next_state(playerStateMachine.Running);
                } else {
                    playerStateMachine.Change_to_next_state(playerStateMachine.Idle);
                }
            }
        }
    }
}
