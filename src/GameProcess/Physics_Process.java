package GameProcess;

import Game.GameController;

public class Physics_Process extends GameLoop{
    private int UPS = 60;
    private double deltaTime = 1000000000.0 / UPS;
    private double deltaU = 0;
    private long lastFrame;
    private long collaspeTime = 0;
    private int frames = 0;
    public Physics_Process(GameController gameController) {
        super(gameController);
        start();
    }
    @Override
    protected void processGameLoop() {
        lastFrame = System.nanoTime();
        while(true) {
            long now = System.nanoTime();
            deltaU += (now - lastFrame) / deltaTime;
            collaspeTime += now - lastFrame;
            lastFrame = now;

            if (deltaU >= 1.0){
                update(dt);
                frames++;
                deltaU -= 1.0;
            }

        }
    }
}
