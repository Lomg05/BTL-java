package Game;
import Inputs.KeyboardInput;
import Inputs.MouseInput;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameController gameController;
    private MouseInput mouse;
    public GamePanel(GameController gameController) {
        this.gameController = gameController;
        mouse = new MouseInput(this);
        setPanelSize(Game.GAME_WIDTH, Game.GAME_HEIGHT);
        addKeyListener(new KeyboardInput(this));
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
    }
    private void setPanelSize(int width, int height) {
        Dimension screenSize = new Dimension(width, height);
        setPreferredSize(screenSize);
        setMinimumSize(screenSize);
        setMaximumSize(screenSize);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameController.draw(g);
    }

    public GameController getGameController() {
        return gameController;
    }
}
