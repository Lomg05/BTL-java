package Player;

import Levels.LevelManager;
import StateMachine.StateMachine;

import java.awt.*;

public class PlayerStateMachine extends StateMachine {
    private Player player;
    private PlayerState currentPlayerState;
    protected Run Running;protected Idle Idle;protected Fall Fall;protected Jump Jump;
    protected DoubleJump DoubleJump;protected Attack_1 Attack_1;protected Attack_2 Attack_2;
    protected Air_Attack Air_Attack; protected Die Die;
    public PlayerStateMachine(Player player, LevelManager levelManager) {
        super(levelManager);
        this.player = player;
        Running = new Run(player,this,levelManager);
        Idle = new Idle(player,this,levelManager);
        Fall = new Fall(player,this,levelManager);
        Jump = new Jump(player,this,levelManager);
        DoubleJump = new DoubleJump(player,this,levelManager);
        Attack_1 = new Attack_1(player,this,levelManager);
        Attack_2 = new Attack_2(player,this,levelManager);
        Air_Attack = new Air_Attack(player,this,levelManager);
        Die = new Die(player,this,levelManager);
        currentPlayerState = Fall;
        currentPlayerState.onEnter();
    }

    public void update(float delta) {
        currentPlayerState.update(delta);
    }


    public void render(Graphics g, int offsetX, int offsetY) {
        currentPlayerState.render(g,offsetX, offsetY);
    }

    protected void Change_to_next_state(PlayerState nextPlayerState) {
        if (this.currentPlayerState != null) {
            this.currentPlayerState.onExit();
        }
        this.currentPlayerState = nextPlayerState;
        this.currentPlayerState.onEnter();
    }
    public void resetState() {
        currentPlayerState = Fall;
    }
}
