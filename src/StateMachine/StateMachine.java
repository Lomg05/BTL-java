package StateMachine;

import Levels.LevelManager;

import java.awt.*;

public abstract class StateMachine {
    protected LevelManager levelManager;
    protected Boolean set_active;

    public StateMachine(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    public abstract void update(float delta);
    public abstract void render(Graphics g, int offsetX, int offsetY);
}
