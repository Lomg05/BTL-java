package Player;

import Entities.Entity;
import Game.Game;
import GameState.Playing;
import Levels.LevelManager;
import utilz.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    protected Playing playing;
    private BufferedImage player_sheet;
    private BufferedImage[][] animations;
    public Color bound_color;
    public Color hitbox_color;
    private PlayerStateMachine stateMachine;
    private int Direction = 1; private Boolean lockDirection = false;
    private Boolean left = false, right = false; //Movement
    private Boolean isPressedJump = false , isConsumedJump = false , jump = false , double_jump = false; //Jump
    private Boolean isPressedDash = false , isConsumedDash = false , isDash = false , canDash = false; //Dash but maybe i make it later :)
    private Boolean isPressedAttack = false, isConsumedAttack = false, isAttack = false; protected int default_dame_1 = 30 ; protected int default_dame_2 = 50 ; protected int damage = default_dame_1;//Attack Input
    private final long COMBO_TIMEOUT_RS = 500000000L; private int attack_stage = 0; private int max_combo = 2; private long lastAttackTime; private Boolean finishedAttack = false;//For attack
    private Rectangle Hitbox; private Boolean hitbox_active = false;
    //UI Healthbar, bla bla...
    private BufferedImage icon;
    protected float maxHealth = 150;
    protected float currentHealth = maxHealth;
    protected int maxHealthBarWidth = (int) (200 * Game.GAME_SCALE * 1.5);
    protected int currentHealthBarWidth = maxHealthBarWidth;
    protected Boolean die = false; protected Boolean gameOver = false;
    private Boolean hurt = false;
    //Effect from items
    protected float damageScale = 0; protected float timeDamageScale; protected Boolean damageApplied = false;
    protected float healScale = 0; protected float timeHealScale; protected Boolean healingApplied = false; protected int healInDameDeal;
    protected float heal; protected Boolean healApplied = false; protected Boolean getHeal = false; protected Boolean healOnce = false;
    protected Boolean getIntersect = false;
    //Kill enemy
    private int enemy_kill_num = 0;
    public Player(float x, float y, LevelManager levelManager, Playing playing) {
        super(x, y, levelManager);
        this.stateMachine = new PlayerStateMachine(this, levelManager);
        InitPlayer();
        loadAnimations();
        InitBounds();
        InitHitbox();
        this.playing = playing;
        hitbox_color = new Color(0, 0, 255, 130);
    }
    private void InitPlayer() {
        player_sheet = LoadSave.loadImage(LoadSave.PLAYER);
        icon = LoadSave.loadImage(LoadSave.FBK_ICON);
    }
    private void InitBounds() {
        collisionBox = new Rectangle((int)(18 * Game.GAME_SCALE * 1.5f),(int)(27 * Game.GAME_SCALE * 1.5f));
    }
    private void InitHitbox() {
       Hitbox = new Rectangle((int)(21 * Game.GAME_SCALE * 1.5f), (int)(18 * Game.GAME_SCALE * 1.5f));
    }
    private void loadAnimations(){
        int rows = player_sheet.getHeight() / 35;
        int cols = player_sheet.getWidth() / 53;
        animations = new BufferedImage[rows][cols];
        for ( int i = 0 ; i < rows ; i ++){
            for ( int j = 0 ; j < cols ; j ++){
                animations[i][j] = player_sheet.getSubimage(j * 53, i * 35, 53, 35);
            }
        }
    }
    private void setMoving(float delta){
        if (x >= levelManager.getCurrentLevel().getMap_width()- collisionBox.width){
            velX = 0;
            x = levelManager.getCurrentLevel().getMap_width() - collisionBox.width;
        } else if (x < 0){
            velX = 0;
            x = 0;
        }

    }
    private void drawHealthBar(Graphics g){
        g.drawImage(icon,3,3, (int) (icon.getWidth() * 0.4f), (int) (icon.getHeight() * 0.4f),null);
        g.setColor(new Color(0,225,0));
        g.fillRect((int)(icon.getWidth() * 0.4f) + 3, 3, currentHealthBarWidth , 13);
    }
    private void updateHitbox(){
        if (Direction == -1){
            int hitbox_x = (int)(collisionBox.x + collisionBox.width - 15 * Game.GAME_SCALE * 1.5 - Hitbox.width);
            int hitbox_y = (int)(collisionBox.y + 5 * Game.GAME_SCALE * 1.5);
            Hitbox.setLocation(hitbox_x, hitbox_y);
        } else {
            int hitbox_x = (int)(collisionBox.x + 15 * Game.GAME_SCALE * 1.5);
            int hitbox_y = (int)(collisionBox.y + 5 * Game.GAME_SCALE * 1.5);
            Hitbox.setLocation(hitbox_x, hitbox_y);
        }
        if (hitbox_active == false){
            hitbox_color = new Color(0, 0, 255, 130);
        } else {
            hitbox_color = new Color(225, 0, 0, 130);
        }
    }
    private void updateEffect(float delta){
        if (damageApplied && timeDamageScale > 0){
            timeDamageScale -= delta * 2;
        } else {
            damageScale = 0;
            damageApplied = false;
        }

        if (healingApplied && timeHealScale > 0){
            timeHealScale -= delta * 2;
        } else {
            healScale = 0;
            healingApplied = false;
        }

        if (healApplied && !getHeal){
            if (currentHealth < maxHealth){
                currentHealth += heal;
                getHeal = true;
                healOnce = false;
            }
        } else {
            healApplied = false;
            getHeal = false;
            heal = 0;
        }
    }
    private void attackProcess(){
        long now = System.nanoTime();
        if (isPressedAttack && !isConsumedAttack){
            isAttack = true;
            isConsumedAttack = true;
            lastAttackTime = now;
            if (finishedAttack && attack_stage > 0) {
                attack_stage++;
            } else {
                attack_stage++;
            }
        } else {
            isAttack = false;
        }

        if (finishedAttack) {
            if (attack_stage > 0 && now - lastAttackTime > COMBO_TIMEOUT_RS) {
                attack_stage = 0;
            }
        }

        if (attack_stage > max_combo){
            if (finishedAttack){
                attack_stage = 1;
            } else {
                attack_stage = max_combo;
            }
        }
    }
    private void updateDirection(){
        if (!lockDirection){
            if (left && !right){
                Direction = -1;
            } else if (right && !left){
                Direction = 1;
            }
        }
    }
    public void update(float delta_time) {
        lockDirection = false;
        attackProcess();
        stateMachine.update(delta_time);
        updateDirection();
        super.update(delta_time);
        setMoving(delta_time);
        updateHitbox();
        updateHealthBar();
        updateEffect(delta_time);
        if (isPressedJump && !isConsumedJump){
            jump = true;
            isConsumedJump = true;
        } else {
            jump = false;
        }

        if (isPressedDash && !isConsumedDash){
            isDash = true;
            isConsumedDash = true;
        } else {
            isDash = false;
        }
        if (currentHealth > maxHealth){
            currentHealth = maxHealth;
        } else if (currentHealth < 0){
            currentHealth = 0;
        }

        if (currentHealth <= 0 || collisionBox.y >= playing.getLevelManager().getCurrentLevel().getMap_height()){
            die = true;
        }
        System.out.println(currentHealth);
    }
    private void updateHealthBar(){
        currentHealthBarWidth = (int)((float)currentHealth/maxHealth * maxHealthBarWidth);
        if (healInDameDeal != 0 && currentHealth < maxHealth){
            playing.getPool().spawnTextDame(collisionBox.x, collisionBox.y,(int)(damage * healScale) + "",Color.GREEN);
        } else if (heal != 0 && !healOnce){
            playing.getPool().spawnTextDame(collisionBox.x, collisionBox.y,(int)(heal) + "",Color.GREEN);
            healOnce = true;
        }
    }

    public void getDamageFromEnemy(float damage, Boolean hurt){
        currentHealth -= (damage - healScale * damage);
        this.hurt = hurt;
    }
    public void render(Graphics g , int offsetX , int offsetY) {
        stateMachine.render(g, offsetX, offsetY);
        drawHealthBar(g);
    }

    public Boolean getHitbox_active() {
        return hitbox_active;
    }

    public void setHitbox_active(Boolean hitbox_active) {
        this.hitbox_active = hitbox_active;
    }

    public int getDirection() {
        return Direction;
    }
    public void setDirection(int direction) {
        Direction = direction;
    }
    public Rectangle getBound(){
        return collisionBox;
    }
    public BufferedImage getAnimation(int row, int col){
        return animations[row][col];
    }

    public Boolean getRight() {
        return right;
    }

    public void setRight(Boolean right) {
        this.right = right;
    }

    public Boolean getLeft() {
        return left;
    }

    public void setLeft(Boolean left) {
        this.left = left;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public Rectangle getHitbox() {
        return Hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        Hitbox = hitbox;
    }

    public Boolean getJump() {
        return jump;
    }

    public Boolean getConsumed() {
        return isConsumedJump;
    }

    public void setConsumed(Boolean consumed) {
        isConsumedJump = consumed;
    }

    public Boolean getPressedJump() {
        return isPressedJump;
    }

    public void setPressedJump(Boolean pressedJump) {
        isPressedJump = pressedJump;
    }

    public void setJump(Boolean jump) {
        this.jump = jump;
    }

    public Boolean getDouble_jump() {
        return double_jump;
    }

    public void setDouble_jump(Boolean double_jump) {
        this.double_jump = double_jump;
    }

    public Boolean getDash() {
        return isDash;
    }

    public void setDash(Boolean dash) {
        isDash = dash;
    }

    public Boolean getPressedDash() {
        return isPressedDash;
    }

    public void setPressedDash(Boolean pressedDash) {
        isPressedDash = pressedDash;
    }

    public Boolean getConsumedDash() {
        return isConsumedDash;
    }

    public void setConsumedDash(Boolean consumedDash) {
        isConsumedDash = consumedDash;
    }

    public Boolean getCanDash() {
        return canDash;
    }

    public void setCanDash(Boolean canDash) {
        this.canDash = canDash;
    }

    public Boolean getAttack() {
        return isAttack;
    }

    public void setAttack(Boolean attack) {
        isAttack = attack;
    }

    public Boolean getConsumedAttack() {
        return isConsumedAttack;
    }

    public void setConsumedAttack(Boolean consumedAttack) {
        isConsumedAttack = consumedAttack;
    }

    public Boolean getPressedAttack() {
        return isPressedAttack;
    }

    public void setPressedAttack(Boolean pressedAttack) {
        isPressedAttack = pressedAttack;
    }

    public Boolean getFinishedAttack() {
        return finishedAttack;
    }

    public void setFinishedAttack(Boolean finishedAttack) {
        this.finishedAttack = finishedAttack;
    }

    public int getAttack_stage() {
        return attack_stage;
    }

    public void setAttack_stage(int attack_stage){
        this.attack_stage = attack_stage;
    }

    public Boolean getLockDirection() {
        return lockDirection;
    }

    public void setLockDirection(Boolean lockDirection) {
        this.lockDirection = lockDirection;
    }
    public float getDame(){
        return damage;
    }
    public void applyEffectFromPotion(String item, float scale , float time) {
        if (item == "BLUE"){
            healScale = scale;
            timeHealScale = time;
        } else if (item == "RED"){
            damageScale = scale;
            timeDamageScale = time;
        }
    }
    public void applyEffectFromHamburger(float heal) {
        this.heal = heal;
    }
    public void setDamageApplied(Boolean damageApplied) {
        this.damageApplied = damageApplied;
    }
    public void setHealingApplied(Boolean healingApplied) {
        this.healingApplied = healingApplied;
    }
    public void setHealApplied(Boolean healApplied) {
        this.healApplied = healApplied;
    }
    public void setEnemy_kill_num(int enemy_kill_num) {
        this.enemy_kill_num = enemy_kill_num;
    }
    public int getEnemy_kill_num(){
        return enemy_kill_num;
    }
    public void resetPlayer(int x, int y){
        this.x = x;
        this.y = y;
        currentHealth = maxHealth;
        enemy_kill_num = 0;
        damageScale = 0;
        healScale = 0;
        gameOver = false;
        die = false;
        stateMachine.resetState();
        Direction = 1;
    }
    public Boolean getGameOver() {
        return gameOver;
    }
    public void setGetIntersect(Boolean getIntersect) {
        this.getIntersect = getIntersect;
    }
}
