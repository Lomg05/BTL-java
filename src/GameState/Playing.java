package GameState;

import Camera.Camera2D;
import EnemyManager.EnemyManager;
import Game.*;
import Levels.LevelManager;
import ObjectManager.ObjecManager;
import Player.Player;
import ui.*;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Playing extends State implements StateMethods {
    public Boolean gameComplete = false;
    private LevelManager levelManager;
    private ObjecManager objecManager;
    private Player player;
    private Camera2D camera;
    private EnemyManager enemyManager;
    private Boolean paused = false;
    private PauseOverlay pauseOverlay;
    private LevelCompleteOverlay levelCompleteOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private GameOverOverlay gameOverOverlay;
    private Boolean levelComplete = false;
    private Boolean gameOver = false;
    private Font font;
    private BufferedImage process;
    private TextDamePool pool;

    // INTRO
    private boolean introActive = true;
    private float introTimer = 0;
    private final float introDuration = 3.0f;

    public Playing(GameController gameController) {
        super(gameController);
        font = LoadSave.LoadFont(LoadSave.FONT);
        process = LoadSave.loadImage(LoadSave.PROCESS_BAR);
        this.pool = new TextDamePool(3);
        levelManager = new LevelManager(this);
        player = new Player(200, 0, levelManager, this);
        objecManager = new ObjecManager(this);
        enemyManager = new EnemyManager(this);
        camera = new Camera2D(player, levelManager, enemyManager, pool);
        pauseOverlay = new PauseOverlay(this);
        levelCompleteOverlay = new LevelCompleteOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
    }

    @Override
    public void update(float delta) {
        if (introActive) {
            introTimer += delta;
            if (introTimer >= introDuration) {
                introActive = false;
            }
            return;
        }

        gameOver = player.getGameOver();
        if (gameOver) {
            gameOverOverlay.update();
        } else {
            if (paused) {
                pauseOverlay.update();
            } else if (gameComplete) {
                gameCompletedOverlay.update();
            } else if (levelComplete) {
                levelCompleteOverlay.update();
            } else {
                levelManager.update();
                enemyManager.update(delta);
                camera.update();
                player.update(delta);
                pool.update(delta);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (introActive) {
            renderIntro(g);
            return;
        }

        camera.render(g);
        processLevel(g);

        if (gameOver) {
            gameOverOverlay.render(g);
        } else {
            if (paused) {
                pauseOverlay.render(g);
            } else if (gameComplete) {
                gameCompletedOverlay.render(g);
            } else if (levelComplete) {
                levelCompleteOverlay.render(g);
            }
        }
    }

    private void renderIntro(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        int alpha = (int) (255 * (introTimer / introDuration));
        if (alpha > 255) alpha = 255;

        g.setFont(font.deriveFont(60f));
        g.setColor(new Color(255, 255, 255, alpha));
        String title = "The Adventure of Fubuki";
        int titleWidth = g.getFontMetrics().stringWidth(title);
        int titleX = (Game.GAME_WIDTH - titleWidth) / 2;
        int titleY = Game.GAME_HEIGHT / 2 - 40;
        g.drawString(title, titleX, titleY);

        g.setFont(font.deriveFont(24f));
        g.setColor(new Color(200, 200, 200, alpha));
        String[] controls = {
                "<- : left     -> : right",
                "X : jump      C : attack"
        };
        for (int i = 0; i < controls.length; i++) {
            String line = controls[i];
            int lineWidth = g.getFontMetrics().stringWidth(line);
            int lineX = (Game.GAME_WIDTH - lineWidth) / 2;
            int lineY = titleY + 50 + i * 30;
            g.drawString(line, lineX, lineY);
        }
    }

    private void processLevel(Graphics g) {
        int x = 20;
        int y = Game.GAME_HEIGHT - 20;
        String currentEnemyKilled = player.getEnemy_kill_num() + "";
        String levelEnemyKillRequired = levelManager.getCurrentLevel().getMisson() + "";
        g.setFont(font.deriveFont(90f));
        g.setColor(new Color(216, 220, 225, 225));
        g.drawString(currentEnemyKilled + " / " + levelEnemyKillRequired, x + 23, y - 5);
    }

    public void unpause() {
        paused = false;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public void setLevelComplete(Boolean levelComplete) {
        this.levelComplete = levelComplete;
    }

    public Player getPlayer() {
        return player;
    }

    public void resetAll() {
        enemyManager.removeAll();
        player.resetPlayer(levelManager.getCurrentLevel().getPlayerSpawnPointX(), levelManager.getCurrentLevel().getPlayerSpawnPointY());
        gameOver = false;
    }

    public void replay() {
        enemyManager.removeAll();
        player.resetPlayer(levelManager.getCurrentLevel().getPlayerSpawnPointX(), levelManager.getCurrentLevel().getPlayerSpawnPointY());
        enemyManager.setSpawnPoint(levelManager.getCurrentLevel().getEnemySpawnPoints());
        enemyManager.addEnemy();
    }

    public ObjecManager getObjecManager() {
        return objecManager;
    }

    public TextDamePool getPool() {
        return pool;
    }

    @Override
    public void MouseDragged(MouseEvent e) {
        if (paused && !gameOver) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void MousePressed(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.MousePressed(e);
        } else {
            if (paused) {
                pauseOverlay.mousePressed(e);
            } else if (levelComplete) {
                levelCompleteOverlay.MousePressed(e);
            } else if (gameComplete) {
                gameCompletedOverlay.MousePressed(e);
            }
        }
    }

    @Override
    public void MouseReleased(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.MouseReleased(e);
        } else {
            if (paused) {
                pauseOverlay.mouseReleased(e);
            } else if (levelComplete) {
                levelCompleteOverlay.MouseReleased(e);
            } else if (gameComplete) {
                gameCompletedOverlay.MouseReleased(e);
            }
        }
    }

    @Override
    public void MouseMoved(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.MouseMove(e);
        } else {
            if (paused) {
                pauseOverlay.mouseMoved(e);
            } else if (levelComplete) {
                levelCompleteOverlay.MouseMove(e);
            } else if (gameComplete) {
                gameCompletedOverlay.MouseMove(e);
            }
        }
    }

    @Override
    public void KeyPressed(KeyEvent e) {
        if (introActive) return; // không điều khiển trong lúc intro

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setRight(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            if (!player.getPressedJump()) {
                player.setPressedJump(true);
                player.setConsumed(false);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            if (!player.getPressedDash()) {
                player.setPressedDash(true);
                player.setConsumedDash(false);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            if (!player.getPressedAttack()) {
                player.setPressedAttack(true);
                player.setConsumedAttack(false);
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            paused = !paused;
        }
    }

    @Override
    public void KeyReleased(KeyEvent e) {
        if (introActive) return;

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_X) {
            player.setPressedJump(false);
            player.setConsumed(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_Z) {
            player.setPressedDash(false);
            player.setConsumedDash(false);
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            player.setPressedAttack(false);
            player.setConsumedAttack(false);
        }
    }
}
