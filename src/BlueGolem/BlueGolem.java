    package BlueGolem;

    import Entities.Entity;
    import Game.Game;
    import Levels.LevelManager;
    import Player.Player;
    import Ray.Ray;
    import ui.TextDame;
    import ui.TextDamePool;
    import utilz.LoadSave;
    import utilz.Maths;

    import java.awt.*;
    import java.awt.image.BufferedImage;
    import java.util.ArrayList;
    import java.util.Random;

    public class BlueGolem extends Entity {
        protected Boolean active = false;
        protected float maxHealth = 250; protected float currentHealth;
        protected float damage = 30;
        protected Random rand;
        protected BufferedImage sprite_sheet;
        protected BufferedImage[][] animations;
        private BlueGolemStateMachine blueGolemStateMachine;
        protected Player player;
        private Ray ray;
        protected int Direction = 1; protected Boolean lockDirection;
        private Rectangle Hitbox; public Color hitboxColor; protected Boolean hitbox_active = false;
        private Boolean hitplayer = false;
        protected float attackCooldown = 1.0f;
        protected float attackTimer = 0.0f;
        private TextDame tmp;
        enum State {
            IDLE,
            WANDER;
        }
        protected float stateTimeOut = 0.0f; protected float[] stateTime; protected float stateTimer; protected State states; protected Boolean stateOut = false;
        protected Boolean isAttack = false; protected Boolean Chase = false; protected Boolean intersectsInChase = false ;protected Boolean hurt = false; protected float hurtCooldown = 0.2f; protected float hurtTimer = 0;
        private Rectangle rayCastFloor; protected Boolean rayCastFloorActive = false ;public Color rayColor;
        protected Boolean death = false; protected Boolean finisedDeath = false;

        //Healthbar
        private int icon;
        private int maxHealthBar = 72;
        private int heathWidth = maxHealthBar;
        private float displayTime = 6.0f;
        private float displayTimer = 0.0f;
        private Boolean display = false;
        private TextDamePool textDamePool;
        public BlueGolem(float x, float y, LevelManager levelManager , Player player, TextDamePool textDamePool) {
            super(x, y, levelManager);
            this.player = player;
            loadSpriteSheet();
            loadAnimations();
            InitBound();
            InitHitBox();
            InitRayCastFloor();
            currentHealth = maxHealth;
            rand = new Random();
            stateTime = new float[] {3,4};
            states = State.WANDER;
            blueGolemStateMachine = new BlueGolemStateMachine(this, levelManager);
            ray = new Ray(levelManager);
            this.textDamePool = textDamePool;
        }
        private void loadSpriteSheet() {
            sprite_sheet = LoadSave.loadImage(LoadSave.BLUEGOLEM);
        }
        private void loadAnimations() {
            int rows = sprite_sheet.getHeight() / 64;
            int cols = sprite_sheet.getWidth() / 90;
            animations = new BufferedImage[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    animations[i][j] = sprite_sheet.getSubimage(j * 90, i * 64, 90, 64);
                }
            }
        }
        private void InitBound(){
            collisionBox = new Rectangle((int)(22 * Game.GAME_SCALE * 1.5) , (int) (32 * Game.GAME_SCALE * 1.5));
        }
        private void InitHitBox(){
            Hitbox = new Rectangle((int)(35 * Game.GAME_SCALE * 1.5), (int) (17 * Game.GAME_SCALE * 1.5));
        }

        private void InitRayCastFloor(){
            rayCastFloor = new Rectangle((int)(3 * Game.GAME_SCALE * 1.5), (int) (35 * Game.GAME_SCALE * 1.5));
        }
        private void updateRayCastFloor(){
            if (Direction == 1){
                rayCastFloor.setLocation((int)(x + collisionBox.width) , (int)(y + collisionBox.getHeight() / 2));
            } else {
                rayCastFloor.setLocation((int)(x - rayCastFloor.width) , (int)(y + collisionBox.getHeight() / 2));
            }
            rayColor = new Color(0, 0, 225, 130);
            rayCastFloorActive = false;
            for (Rectangle tile : levelManager.getCurrentLevel().getTileBounds()){
                if (rayCastFloor.intersects(tile)){
                    rayColor = new Color(225, 0, 0, 130);
                    rayCastFloorActive = true;
                    break;
                }
            }
        }

        private void updateHitbox(){
            if (Direction == 1) {
                Hitbox.setLocation((int) (x - 7 * Game.GAME_SCALE * 1.5), (int) (y + 15.5 * Game.GAME_SCALE * 1.5));
            } else {
                Hitbox.setLocation((int) (x - 10 * Game.GAME_SCALE * 1.5), (int) (y + 15.5 * Game.GAME_SCALE * 1.5));
            }
            if (hitbox_active) {
                hitboxColor = new Color(225, 0, 0, 130);
            } else {
                hitboxColor = new Color(0, 0, 225, 130);
            }
        }
        private void stateUpdate(float delta){
            int distance = Maths.Distance(player.getBound().x, player.getBound().y, collisionBox.x, collisionBox.y);
            if (distance <= 400 && !ray.rayInterrupt() && canSee()){
                Chase = true;
            }

            if (distance > 400 || ray.rayInterrupt()){
                Chase = false;
            }

            if (Chase && !lockDirection){
                if (player.getBound().x < collisionBox.x){
                    Direction = -1;
                } else {
                    Direction = 1;
                }
            }
            if (player.getHitbox().intersects(collisionBox)){
                if (player.getHitbox_active() && hurtTimer <= 0 && !active){
                    hurt = true;
                    hurtTimer = hurtCooldown;
                    currentHealth -= player.getDame();
                    display = true;
                    displayTimer = 0.0f;
                    if (player.getBound().x <= collisionBox.x){
                        Direction = -1;
                    } else {
                        Direction = 1;
                    }
                    textDamePool.spawnTextDame(collisionBox.x,collisionBox.y,"-"+(int)player.getDame(),Color.RED);
                } else {
                    hurt = false;
                }
            }

            if (Hitbox.intersects(player.getBound()) && canSee()) {
                isAttack = true;
                if (hitbox_active && attackTimer <= 0) {
                    player.getDamageFromEnemy(damage, true);
                    hitplayer = true;
                    attackTimer = attackCooldown; // Reset cooldown
                } else {
                    hitplayer = false;
                }
            } else {
                isAttack = false;
                hitplayer = false;
            }

            if (attackTimer > 0) {
                attackTimer -= delta * 2;
            }


            if (hurtTimer > 0){
                hurtTimer -= delta * 2;
            }

            if (currentHealth <= 0){
                death = true;
            } else {
                death = false;
            }

            if (display){
                displayTimer += delta * 2;
            }
            if (displayTimer >= displayTime){
                display = false;
                displayTimer = 0.0f;
            }
        }
        protected State getRandomState(){
            State[] states = State.values();
            return states[rand.nextInt(states.length)];
        }

        protected float getStateTime(){
            return stateTime[rand.nextInt(stateTime.length)];
        }

        private void HealthBar(Graphics g , int offsetX , int offsetY){
            if (display && !death) {
                g.setColor(new Color(0, 0, 0));
                g.fillRect(collisionBox.x - 11 - offsetX,
                        collisionBox.y - 19 - offsetY,
                        74, 11);

                g.setColor(new Color(225, 0, 30));
                g.fillRect(collisionBox.x - 10 - offsetX,
                        collisionBox.y - 18 - offsetY,
                        heathWidth, 9);
            }
        }
        private Boolean canSee(){

            if (Direction == 1){
                if (player.getBound().x >= collisionBox.x){
                    return true;
                }
            } else if (Direction == -1){
                if (player.getBound().x <= collisionBox.x){
                    return true;
                }
            }
            return false;
        }
        public void update(float delta) {
            super.update(delta);
            blueGolemStateMachine.update(delta);
            ray.update(delta,player.getBound().x , player.getBound().y, collisionBox.x , collisionBox.y);
            updateHitbox();
            updateRayCastFloor();
            stateUpdate(delta);
            updateHealthBar();
        }
        private void updateHealthBar(){
            heathWidth = (int)((currentHealth / (float)maxHealth) * maxHealthBar);
        }
        protected void stateTimer(float delta) {
            stateTimer += delta * 2;
            if (stateTimer >= stateTimeOut){
                states = getRandomState();
                stateTimer = 0;
                stateOut = true;
            }
        }
        public void render(Graphics g, int offsetX, int offsetY) {
            HealthBar(g , offsetX , offsetY);
            Debug(g,offsetX,offsetY);
            blueGolemStateMachine.render(g, offsetX, offsetY);
        }
        public void Debug(Graphics g, int offsetX, int offsetY) {
            g.setColor(hitboxColor);
            g.fillRect(Hitbox.x - offsetX, Hitbox.y - offsetY, Hitbox.width, Hitbox.height);
            g.setColor(new Color(0, 0, 225, 130));
            g.fillRect(collisionBox.x - offsetX , collisionBox.y - offsetY , collisionBox.width, collisionBox.height);

            g.setColor(rayColor);
            g.fillRect(rayCastFloor.x - offsetX, rayCastFloor.y - offsetY, rayCastFloor.width, rayCastFloor.height);
            ray.render(g, offsetX, offsetY);
        }
        public int getDirection() {
            return Direction;
        }
        public void setDirection(int direction) {
            Direction = direction;
        }
        public BufferedImage getSprite(int x , int y) {
            return animations[x][y];
        }
        public Rectangle getBounds() {
            return collisionBox;
        }
        public Rectangle getHitbox() {
            return Hitbox;
        }
        public Rectangle getRayCastFloor() {
            return rayCastFloor;
        }
        public Boolean getFinisedDeath(){
            return finisedDeath;
        }
    }
