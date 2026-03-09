package Game;
import GameProcess.Physics_Process;
import GameProcess.Process;

public class Game {
    public final static int TILE_DEFAULT = 16;
    public final static int TILE_IN_WIDTH = 48;
    public final static int TILE_IN_HEIGHT = 27;
    public final static float GAME_SCALE = 1.5f;
    public final static int TILE_SIZE = (int)(TILE_DEFAULT * GAME_SCALE);
    public final static int GAME_WIDTH = TILE_IN_WIDTH * TILE_SIZE;
    public final static int GAME_HEIGHT = TILE_IN_HEIGHT * TILE_SIZE;
    public final static int MAP_TILE_SIZE = TILE_SIZE * 2;
    public Game(){
        GameController gameController = new GameController();
        Physics_Process p = new Physics_Process(gameController);
        Process process = new Process(gameController);
    }
}
