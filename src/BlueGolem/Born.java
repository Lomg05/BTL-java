package BlueGolem;

import Levels.LevelManager;
import utilz.Constants;

import java.awt.*;

public class Born extends BlueGolemState{

    public Born(BlueGolem blueGolem, BlueGolemStateMachine blueGolemStateMachine, LevelManager levelManager) {
        super(blueGolem, blueGolemStateMachine, levelManager);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        blueGolem.stateTimer(delta);
        updateState(delta);
    }

    @Override
    protected void onEnter() {
        System.out.println("Born");
        blueGolem.lockDirection = false;
        blueGolem.stateTimeOut = blueGolem.getStateTime();
        playAnimation(Constants.BlueGolemConstants.BORN, Constants.BlueGolemAniConstants.BORN);
        blueGolem.Chase = false;
        aniSpeed = 5;
    }

    private void updateState(float delta) {
        blueGolem.setVelX(0);


        if (finished) {
            blueGolem.stateOut = false;
            blueGolem.stateTimer = 0;
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER) {
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        }
    }
}
