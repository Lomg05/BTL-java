package GameProcess;

import Game.GameController;

import java.awt.*;

public abstract class GameLoop {
    private Thread gameThread;
    private GameController gameController;
    protected float dt = (float) (1.0 / 120);
    public GameLoop(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        gameThread = new Thread(this::processGameLoop);
        gameThread.start();
    }
    protected void update(float deltaTime) {
        gameController.update(deltaTime);
    }

    protected void render(){
        gameController.render();
    }
    protected abstract void processGameLoop();
}
