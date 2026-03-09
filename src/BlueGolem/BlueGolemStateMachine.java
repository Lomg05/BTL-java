package BlueGolem;

import Levels.LevelManager;
import StateMachine.StateMachine;

import java.awt.*;

public class BlueGolemStateMachine extends StateMachine {
    private BlueGolem blueGolem;
    private BlueGolemState currentState;
    protected Idle Idle; protected Wander Wander; protected Attack Attack; protected Chase Chase;
    protected Born Born; protected Death Death; protected Hurt Hurt;
    public BlueGolemStateMachine(BlueGolem blueGolem , LevelManager  levelManager) {
        super(levelManager);
        this.blueGolem = blueGolem;
        Idle = new Idle(blueGolem,this,levelManager);
        Wander = new Wander(blueGolem,this,levelManager);
        Attack = new Attack(blueGolem,this,levelManager);
        Chase = new Chase(blueGolem,this,levelManager);
        Born = new Born(blueGolem,this,levelManager);
        Death = new Death(blueGolem,this,levelManager);
        Hurt = new Hurt(blueGolem,this,levelManager);
        currentState = Born;
        currentState.onEnter();
    }

    @Override
    public void update(float delta) {
        currentState.update(delta);
    }

    @Override
    public void render(Graphics g, int offsetX, int offsetY) {
        currentState.render(g, offsetX, offsetY);
    }

    protected void Change_to_next_state(BlueGolemState next_state) {
        if (currentState != null) {
            currentState.onExit();
        }
        currentState = next_state;
        currentState.onEnter();
    }
}
