package Player;

import AudioPlayer.AudioPlayer;
import Levels.LevelManager;
import utilz.Constants;

import java.awt.*;

public class Die extends PlayerState{
    public Die(Player player, PlayerStateMachine playerStateMachine, LevelManager levelManager) {
        super(player, playerStateMachine, levelManager);
    }
    @Override
    protected void update(float delta) {
        updateAniFrames(stateTime);
        updateState(delta);
    }

    @Override
    protected void render(Graphics g, int offsetX, int offsetY) {
        drawAnimations(g,offsetX,offsetY, state);
    }

    @Override
    protected void onEnter() {
        player.playing.getGameController().getAudioPlayer().playPoolEffect(AudioPlayer.GAME_OVER);
        player.playing.getGameController().getAudioPlayer().stopSong();
        playAnimation(Constants.PlayerConstants.DEATH, Constants.PlayerAniConstants.DEATH);
        aniSpeed = 10;
        player.setDouble_jump(false);
        player.setHitbox_active(false);
        player.setFinishedAttack(false);
        player.setAttack_stage(0);
    }

    @Override
    protected void onExit() {

    }

    private void updateState(float delta){
        player.setVelX(0);
        if (finished){
            player.gameOver = true;
        }
    }
}
