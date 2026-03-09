package BlueGolem;

import Levels.LevelManager;
import utilz.Constants;

import java.awt.*;

public class Wander extends BlueGolemState{
    public Wander(BlueGolem blueGolem, BlueGolemStateMachine blueGolemStateMachine, LevelManager levelManager) {
        super(blueGolem, blueGolemStateMachine, levelManager);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        updateMovementInState(delta);
        blueGolem.stateTimer(delta);
        updateState(delta);
    }

    @Override
    protected void onEnter() {
        System.out.println("wander");
        blueGolem.lockDirection = false;
        if (blueGolem.intersectsInChase){
            blueGolem.Chase = false;
            blueGolem.intersectsInChase = false;
        }
        blueGolem.stateTimeOut = blueGolem.getStateTime();
        playAnimation(Constants.BlueGolemConstants.WALK, Constants.BlueGolemAniConstants.WALK);
        aniSpeed = 10;
    }

    private void updateState(float delta) {

        if (blueGolem.death){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Death);
            return;
        }

        if (blueGolem.checkCollisionLeftWall() && !blueGolem.checkCollisionRightWall()) {
            blueGolem.Direction = 1;
            blueGolem.stateTimeOut = blueGolem.getStateTime();
        } else if (blueGolem.checkCollisionRightWall() && !blueGolem.checkCollisionLeftWall()) {
            blueGolem.Direction = -1;
            blueGolem.stateTimeOut = blueGolem.getStateTime();
        }

        if (blueGolem.checkCollisionFloor() && !blueGolem.rayCastFloorActive){
            blueGolem.Direction *= -1;
            blueGolem.stateTimeOut = blueGolem.getStateTime();
        }

        if (blueGolem.stateOut == true && finished) {
            blueGolem.stateOut = false;
            blueGolem.stateTimer = 0;
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER) {
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        }

        if (blueGolem.isAttack){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Attack);
        } else if (blueGolem.Chase){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Chase);
        } else if (blueGolem.hurt) {
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Hurt);
        }
    }
}
