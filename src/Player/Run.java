package Player;

import Levels.LevelManager;
import utilz.Constants;
import java.awt.*;

public class Run extends PlayerState {
    public Run(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }

    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        updateMovementInState(delta);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void onEnter() {
        playAnimation(Constants.PlayerConstants.RUNNING, Constants.PlayerAniConstants.RUNNING);
        player.setDouble_jump(false);
        player.setHitbox_active(false);
        aniSpeed = 5;
    }

    public void updateState(float delta) {
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
        }
        if (!player.getLeft() && !player.getRight()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Idle);
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
            return;
        }
    }
}
