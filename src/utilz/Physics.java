package utilz;

import java.awt.*;

public class Physics {
    public static final int Gravity = 1000;
    public static final int Velocity = 1000;
    public static final int Force = 2450;

    public static final int WANDER_VELOCITY = 150;
    public static final int CHASING_VELOCITY = 300;
    public static String HorizontalCollision(Rectangle bound, Rectangle other){
        Rectangle intersection = bound.intersection(other);
        if (intersection.width < intersection.height){
            if (bound.x < other.x){
                return "right";
            } else {
                return "left";
            }
        }
        return "none";
    }

    public static String VerticalCollision(Rectangle bound, Rectangle other){
        Rectangle intersection = bound.intersection(other);
        if (intersection.height < intersection.width){
            if(bound.y + 1 < other.y){
                return "bottom";
            } else {
                return "top";
            }
        }
        return "none";
    }

}
