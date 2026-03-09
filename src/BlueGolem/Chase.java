package BlueGolem;

import Levels.LevelManager;
import utilz.Constants;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class Chase extends BlueGolemState{
    public Chase(BlueGolem blueGolem, BlueGolemStateMachine blueGolemStateMachine, LevelManager levelManager) {
        super(blueGolem, blueGolemStateMachine, levelManager);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY,state);
    }

    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void onEnter() {
        System.out.println("Chase");
        blueGolem.lockDirection = false;
        playAnimation(Constants.BlueGolemConstants.WALK, Constants.BlueGolemAniConstants.WALK);
        aniSpeed = 5;
    }

    private void updateState(float delta) {
        float newVelX = Maths.Lerp(blueGolem.getVelX(), Physics.WANDER_VELOCITY * blueGolem.Direction * 2, delta * 50);
        blueGolem.setVelX(newVelX);

        float newVelY = Maths.Lerp(blueGolem.getVelY(), Physics.Gravity * 2, delta * 10);
        blueGolem.setVelY(newVelY);

        if (blueGolem.death){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Death);
            return;
        }

        if (blueGolem.hurt){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Hurt);
        }

        if (!blueGolem.Chase){
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        }

        if (blueGolem.checkCollisionLeftWall() && !blueGolem.checkCollisionRightWall()) {
            blueGolem.Direction = 1;
            blueGolem.intersectsInChase = true;
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        } else if (blueGolem.checkCollisionRightWall() && !blueGolem.checkCollisionLeftWall()) {
            blueGolem.Direction = -1;
            blueGolem.intersectsInChase = true;
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        } else if (blueGolem.checkCollisionFloor() && !blueGolem.rayCastFloorActive){
            blueGolem.Direction *= -1;
            blueGolem.intersectsInChase = true;
            if (blueGolem.states == BlueGolem.State.IDLE){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Idle);
            } else if (blueGolem.states == BlueGolem.State.WANDER){
                blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Wander);
            }
        }

        if (blueGolem.isAttack){
            blueGolemStateMachine.Change_to_next_state(blueGolemStateMachine.Attack);
        }
    }
}
