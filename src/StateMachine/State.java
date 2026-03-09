package StateMachine;

import java.awt.*;

public abstract class State {
    protected int aniFrame = 0;
    protected int aniSpeed;
    protected int aniTick = 0;
    protected int state = 0;
    protected int stateTime = 0;
    protected Boolean finished = false;

    protected void drawAnimations(Graphics g, float offsetX, float offsetY, int state) {};

    protected void updateAniFrames(int stateFrames){
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniFrame = (aniFrame + 1) % stateFrames;
            aniTick = 0;
        }
        if (aniFrame >= stateFrames - 1) {
            finished = true;
        } else {
            finished = false;
        }
    }
    protected void playAnimation(int state , int stateTime){
        aniFrame = 0;
        aniTick = 0;
        this.state = state;
        this.stateTime = stateTime;
    }

    protected abstract void update(float delta);
    protected abstract void render(Graphics g, int offsetX, int offsetY);
    protected abstract void onEnter();
    protected abstract void onExit();
}
