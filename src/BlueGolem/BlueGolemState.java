package BlueGolem;

import Game.Game;
import Levels.LevelManager;
import StateMachine.State;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;

public class BlueGolemState extends State {
    protected BlueGolem blueGolem;
    protected BlueGolemStateMachine blueGolemStateMachine;
    protected LevelManager levelManager;

    public BlueGolemState(BlueGolem blueGolem, BlueGolemStateMachine blueGolemStateMachine, LevelManager levelManager) {
        this.blueGolem = blueGolem;
        this.blueGolemStateMachine = blueGolemStateMachine;
        this.levelManager = levelManager;
    }

    protected void drawAnimations(Graphics g, float offsetX, float offsetY, int state) {
        if (blueGolem.getDirection() == 1){
            g.drawImage(blueGolem.getSprite(state,aniFrame),
                    (int)(blueGolem.getBounds().x - 33 * Game.GAME_SCALE * 1.5 - offsetX), (int)(blueGolem.getBounds().y - 32 * Game.GAME_SCALE * 1.5 - offsetY),
                    (int)(blueGolem.getSprite(0,0).getWidth() * Game.GAME_SCALE * 1.5),
                    (int)(blueGolem.getSprite(0,0).getHeight() * Game.GAME_SCALE * 1.5), null);
        } else {
            g.drawImage(blueGolem.getSprite(state,aniFrame),
                    (int)(blueGolem.getBounds().x + 33 * Game.GAME_SCALE * 1.5 + blueGolem.getBounds().width - offsetX), (int)(blueGolem.getBounds().y - 32 * Game.GAME_SCALE * 1.5 - offsetY),
                    (int)(blueGolem.getSprite(0,0).getWidth() * Game.GAME_SCALE * 1.5 * blueGolem.Direction),
                    (int)(blueGolem.getSprite(0,0).getHeight() * Game.GAME_SCALE * 1.5), null);
        }
    }
    

    protected void updateMovementInState(float delta){
        float newVelX = Maths.Lerp(blueGolem.getVelX(), Physics.WANDER_VELOCITY * blueGolem.Direction, delta * 50);
        blueGolem.setVelX(newVelX);

        float newVelY = Maths.Lerp(blueGolem.getVelY(), Physics.Gravity * 2, delta * 10);
        blueGolem.setVelY(newVelY);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {}

    @Override
    protected void update(float delta) {}

    @Override
    protected void onEnter() {}

    @Override
    protected void onExit() {}
}
