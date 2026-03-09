package Player;

import Game.Game;
import Levels.LevelManager;
import utilz.Maths;
import utilz.Physics;

import java.awt.*;
import StateMachine.State;

public class PlayerState extends State {
    protected Player player;
    protected PlayerStateMachine playerStateMachine;
    protected LevelManager levelManager;
    public PlayerState(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        this.player = player;
        this.playerStateMachine = playerStateMachine;
        this.levelManager = levelManager;
    }

    protected void drawAnimations(Graphics g, float offsetX, float offsetY, int state) {
        if (player.getDirection() == 1) {
            g.drawImage(player.getAnimation(state, aniFrame),
                    Math.round(player.getBound().x - 18 * Game.GAME_SCALE * 1.5f - offsetX),
                    Math.round(player.getBound().y - 8 * Game.GAME_SCALE * 1.5f - offsetY),
                    Math.round(player.getAnimation(0, 0).getWidth() * Game.GAME_SCALE * 1.5f),
                    Math.round(player.getAnimation(0, 0).getHeight() * Game.GAME_SCALE * 1.5f), null);
        } else {
            g.drawImage(player.getAnimation(state, aniFrame),
                    Math.round(player.getBound().x + 18 * Game.GAME_SCALE * 1.5f + player.getBound().width - offsetX),
                    Math.round(player.getBound().y - 8 * Game.GAME_SCALE * 1.5f - offsetY),
                    Math.round(player.getAnimation(0, 0).getWidth() * Game.GAME_SCALE * 1.5f) * player.getDirection(),
                    Math.round(player.getAnimation(0, 0).getHeight() * Game.GAME_SCALE * 1.5f), null);
        }
    }

    protected void updateAniFrames(int stateFrames) {
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

    protected void updateMovementInState(float delta) {
        float velocityX = Physics.Velocity;
        if (player.getLeft() && !player.getRight()) {
            float newVelX = Maths.Lerp(player.getVelX(), -velocityX, delta * 10);
            player.setVelX(newVelX);
        } else if (!player.getLeft() && player.getRight()) {
            float newVelX = Maths.Lerp(player.getVelX(), velocityX, delta * 10);
            player.setVelX(newVelX);
        } else {
            float newVelX = Maths.Lerp(player.getVelX(), 0, delta * 15);
            player.setVelX(newVelX);
        }
    }

    protected void playAnimation(int state , int stateTime){
        aniFrame = 0;
        aniTick = 0;
        this.state = state;
        this.stateTime = stateTime;
    }

    @Override
    protected void update(float delta) {}

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {}

    @Override
    protected void onEnter() {}

    @Override
    protected void onExit() {}
}
