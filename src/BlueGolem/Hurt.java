package BlueGolem;

import Levels.LevelManager;
import utilz.Constants;

import java.awt.*;

public class Hurt extends BlueGolemState{
    public Hurt(BlueGolem blueGolem, BlueGolemStateMachine blueGolemStateMachine, LevelManager levelManager) {
        super(blueGolem, blueGolemStateMachine, levelManager);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void update(float delta) {
        blueGolem.lockDirection = true;
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void onEnter() {
        System.out.println("Hurt");
        playAnimation(Constants.BlueGolemConstants.HURT, Constants.BlueGolemAniConstants.HURT);
        aniSpeed = 8;
    }


    private void updateState(float delta) {
        blueGolem.setVelX(0);
        if (blueGolem.death){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Death);
            return;
        }
        if (finished){
            if (blueGolem.isAttack){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Attack);
            } else if (blueGolem.Chase){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Chase);
            } else if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER) {
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        }
    }
}
