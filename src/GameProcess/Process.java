package GameProcess;

import Game.GameController;

public class Process extends GameLoop{
    private int FPS = 120;
    private double deltaTime = 1000000000.0 / FPS;
    private double deltaF = 0;
    private long lastFrame;
    private long collaspeTime = 0;
    private int frames = 0;
    public Process(GameController gameController) {
        super(gameController);
        start();
    }
    @Override
    protected void processGameLoop() {
        lastFrame = System.nanoTime();
        while(true) {
            long now = System.nanoTime();
            deltaF += (now - lastFrame) / deltaTime;
            collaspeTime += now - lastFrame;
            lastFrame = now;

            if (deltaF >= 1.0) {
                frames++;
                render();
                deltaF -= 1.0;
            }

        }
    }

}
