package Player;

import Levels.LevelManager;
import StateMachine.StateMachine;
import utilz.Constants;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class Idle extends PlayerState {
    public Idle(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }

    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY, state);
    }

    @Override
    protected void onEnter() {
        playAnimation(Constants.PlayerConstants.IDLE, Constants.PlayerAniConstants.IDLE);
        aniSpeed = 10;
        player.die = false;
        player.setDouble_jump(false);
        player.setHitbox_active(false);
        player.setFinishedAttack(false);
        player.setAttack_stage(0);
    }

    @Override
    protected void onExit() {

    }

    private void updateState(float delta){
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
            return;
        }
        if (!player.getLeft() && !player.getRight()) {
            float newVelX = Maths.Lerp(player.getVelX(), 0.0f, delta * 100);
            player.setVelX(newVelX);
        }
        float newVelY = Maths.Lerp(player.getVelY(), Physics.Gravity * 2, delta * 10);
        player.setVelY(newVelY);

        if (player.getLeft() || player.getRight()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Running);
            return;
        }
        if (!player.checkCollisionFloor()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Fall);
            return;
        }
        if (player.getJump()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Jump);
            return;
        }
        if (player.getAttack() && player.getAttack_stage() == 1){
            playerStateMachine.Change_to_next_state(playerStateMachine.Attack_1);
        }
    }
}
