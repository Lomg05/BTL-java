package Player;

import Levels.LevelManager;
import StateMachine.StateMachine;
import utilz.Constants;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class Fall extends PlayerState {
    public Fall(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
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
        playAnimation(Constants.PlayerConstants.FALLING,Constants.PlayerAniConstants.FALLING);
        aniSpeed = 5;
        player.setHitbox_active(false);
    }

    private void updateState(float delta){
        if (player.die){
            playerStateMachine.Change_to_next_state(playerStateMachine.Die);
        }
       float newVelY = Maths.Lerp(player.getVelY(), Physics.Gravity * 2, delta * 10);
       player.setVelY(newVelY);
        if (player.getAttack()){
            playerStateMachine.Change_to_next_state(playerStateMachine.Air_Attack);
        }

       if (player.getJump() && !player.getDouble_jump()){
           playerStateMachine.Change_to_next_state(playerStateMachine.DoubleJump);
           return;
       }
       if (player.checkCollisionFloor()){
           playerStateMachine.Change_to_next_state(playerStateMachine.Idle);
       }


    }
}
