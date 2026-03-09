package Player;

import AudioPlayer.AudioPlayer;
import Levels.LevelManager;
import utilz.Constants;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class Air_Attack extends PlayerState{
    public Air_Attack(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }

    @Override
    protected void update(float delta) {
        player.setLockDirection(true);
        updateAniFrames(stateTime);
        updateMovementInState(delta);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY, state);
    }

    @Override
    protected void onEnter() {
        player.playing.getGameController().getAudioPlayer().playPoolEffect(AudioPlayer.ATTACK);
        player.damage = 10;
        playAnimation(Constants.PlayerConstants.AIR_ATTACK, Constants.PlayerAniConstants.AIR_ATTACK);
        aniSpeed = 5;
        player.setHitbox_active(false);
    }

    private void updateState(float delta){
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
        }
        if (aniFrame >= 3){
            player.setHitbox_active(true);
        } else {
            player.setHitbox_active(false);
        }

        float newVelY = Maths.Lerp(player.getVelY(), Physics.Gravity * 2, delta * 5);
        player.setVelY(newVelY);

        if (finished){
            if (player.getVelY() >= 0){
                playerStateMachine.Change_to_next_state(playerStateMachine.Fall);
            } else {
                playerStateMachine.Change_to_next_state(playerStateMachine.Jump);
            }
        } else {
            if (player.checkCollisionFloor()){
                if (player.getLeft() || player.getRight()){
                    playerStateMachine.Change_to_next_state(playerStateMachine.Running);
                } else {
                    playerStateMachine.Change_to_next_state(playerStateMachine.Idle);
                }
            }
        }

    }
}
