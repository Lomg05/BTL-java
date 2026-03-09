package Player;

import AudioPlayer.AudioPlayer;
import Levels.LevelManager;
import utilz.Constants;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class DoubleJump extends PlayerState {
    public DoubleJump(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }

    @Override
    protected void update(float delta) {
        player.setLockDirection(true);
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY, state);
    }

    @Override
    protected void onEnter() {
        player.playing.getGameController().getAudioPlayer().playPoolEffect(AudioPlayer.DOUBLE_JUMP);
        playAnimation(Constants.PlayerConstants.JUMPING,Constants.PlayerAniConstants.JUMPING);
        aniSpeed = 10;
        player.setDouble_jump(true);
        player.setHitbox_active(false);
        float newVelY = -Physics.Force;
        player.setVelY(newVelY);
    }

    @Override
    protected void onExit() {

    }

    private void updateState(float delta) {
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
        }
        float newVelY = Maths.Lerp(player.getVelY(), Physics.Gravity * 2, delta * 5);
        player.setVelY(newVelY);
        if (player.getAttack()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Air_Attack);
        }

        if (player.getVelY() >= 0){
            playerStateMachine.Change_to_next_state(playerStateMachine.Fall);
            return;
        }

        if (player.checkCollisionCelling()){
            player.setDouble_jump(true);
        }

    }
}
