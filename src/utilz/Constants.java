package utilz;

import Game.Game;

import java.awt.*;

public class Constants {
    public static class Background{
        public static final int BACKGROUND_COLOR_DEFAULT_WIDTH = 320;
        public static final int BACKGROUND_COLOR_DEFAULT_HEIGHT = 180;
        public static final int BACKGROUND_COLOR_WIDTH = (int) (320 * Game.GAME_SCALE * 1.5f);
        public static final int BACKGROUND_COLOR_HEIGHT = (int) (180 * Game.GAME_SCALE * 1.5f);
    }
    public static class MenuButton{
        public static final int MENU_BUTTON_DEFAULT_WIDTH = 69;
        public static final int MENU_BUTTON_DEFAULT_HEIGHT = 21;
        public static final int MENU_BUTTON_WIDTH = (int) (MENU_BUTTON_DEFAULT_WIDTH * Game.GAME_SCALE * 3);
        public static final int MENU_BUTTON_HEIGHT  = (int) (MENU_BUTTON_DEFAULT_HEIGHT * Game.GAME_SCALE * 3);
        public static final int BACKGROUND_BUTTON_DEFAULT_WIDTH = 100;
        public static final int BACKGROUND_BUTTON_DEFAULT_HEIGHT = 117;
        public static final int BACKGROUND_BUTTON_WIDTH = (int) (BACKGROUND_BUTTON_DEFAULT_WIDTH * Game.GAME_SCALE * 2);
        public static final int BACKGROUND_BUTTON_HEIGHT = (int) (BACKGROUND_BUTTON_DEFAULT_HEIGHT * Game.GAME_SCALE * 2);
    }
    public static class PauseButton{
        public static final int SOUND_SIZE_DEFAULT_WIDTH = 23;
        public static final int SOUND_SIZE_DEFAULT_HEIGHT = 21;
        public static final int SOUND_SIZE_WIDTH = (int) (SOUND_SIZE_DEFAULT_WIDTH * Game.GAME_SCALE * 2.5);
        public static final int SOUND_SIZE_HEIGHT = (int) (SOUND_SIZE_DEFAULT_HEIGHT * Game.GAME_SCALE * 2.5);
        public static final int BACKGROUND_WIDTH =(int) (105 * Game.GAME_SCALE * 2.5);
        public static final int BACKGROUND_HEIGHT = (int) (141 * Game.GAME_SCALE * 2.5);
    }
    public static class URMButton{
        public static final int URM_SIZE_DEFAULT_WIDTH = 23;
        public static final int URM_SIZE_DEFAULT_HEIGHT = 21;
        public static final int URM_SIZE_WIDTH = (int) (URM_SIZE_DEFAULT_WIDTH * Game.GAME_SCALE * 2.5);
        public static final int URM_SIZE_HEIGHT = (int) (URM_SIZE_DEFAULT_HEIGHT * Game.GAME_SCALE * 2.5);
    }
    public static class VolumeButton{
        public static final int VOLUME_SIZE_DEFAULT_WIDTH = 10;
        public static final int VOLUME_SIZE_DEFAULT_HEIGHT = 21;
        public static final int VOLUME_SIZE_WIDTH = (int) (VOLUME_SIZE_DEFAULT_WIDTH * Game.GAME_SCALE * 2.5);
        public static final int VOLUME_SIZE_HEIGHT = (int) (VOLUME_SIZE_DEFAULT_HEIGHT * Game.GAME_SCALE * 2.5);
        public static final int SLIDER_DEFAULT_WIDTH = 69;
        public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.GAME_SCALE * 2.5);
    }
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int FALLING = 3;
        public static final int ATTACK_1 = 4;
        public static final int ATTACK_2 = 5;
        public static final int AIR_ATTACK = 6;
        public static final int DEATH = 7;
    }

    public static class PlayerAniConstants {
        public static final int IDLE = 4;
        public static final int RUNNING = 6;
        public static final int JUMPING = 3;
        public static final int FALLING = 3;
        public static final int ATTACK_1 = 5;
        public static final int ATTACK_2 = 6;
        public static final int AIR_ATTACK = 6;
        public static final int DEATH = 9;
    }

    public static class BlueGolemConstants {
        public static final int IDLE = 0;
        public static final int WALK = 1;
        public static final int HURT = 2;
        public static final int ATTACK = 3;
        public static final int BORN = 4;
        public static final int DIE = 5;
    }

    public static class BlueGolemAniConstants {
        public static final int IDLE = 8;
        public static final int WALK = 8;
        public static final int HURT = 4;
        public static final int ATTACK = 11;
        public static final int BORN = 13;
        public static final int DIE = 13;
    }

    public static class TheDeathConstants {
        public static final int IDLE = 0;
        public static final int WANDER = 1;
        public static final int ATTACK = 2;
        public static final int HURT = 3;
        public static final int DIE = 4;
        public static final int BORN = 8;
    }
    public static class TheDeathAniConstants {
        public static final int IDLE = 8;
        public static final int WANDER = 8;
        public static final int ATTACK = 9;
        public static final int HURT = 5;
        public static final int DIE = 10;
        public static final int BORN = 10;
    }
    public static class ItemConstants {
        public static final int BLUE_POTION = 0;
        public static final int RED_POTION = 1;
        public static final int HAMBURGER = 2;
    }
    public static class ItemAniConstants {
        public static final int BLUE_POTION = 7;
        public static final int RED_POTION = 7;
        public static final int HAMBURGER = 7;
    }
}
