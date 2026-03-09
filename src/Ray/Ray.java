package Ray;

import Game.Game;
import Levels.LevelManager;
import utilz.Maths;

import java.awt.*;

public class Ray {
    private float start_x, start_y, end_x, end_y, angle;
    private Boolean bottom, top, left, right;
    private int wall_hit_x , wall_hit_y;
    private LevelManager manager;
    private Boolean found_horizontal_wall = false; private Boolean found_vertical_wall = false;
    public Ray(LevelManager manager) {
        this.manager = manager;
    }

    public void update(float delta, float player_x, float player_y, float enemy_x, float enemy_y) {
        start_x = enemy_x;
        start_y = enemy_y;
        end_x = player_x;
        end_y = player_y;
        this.angle = angle;
        wall_hit_x = 0;
        wall_hit_y = 0;
        float raw_angle = (float) Math.atan2(player_y - enemy_y, player_x - enemy_x);
        this.angle = normalize_angle(raw_angle);
        top = angle > 0 && angle < Math.PI;
        bottom = !top;
        left = angle > Math.PI / 2 && angle < 3 * Math.PI / 2;
        right = !left;

        cast();
    }
    private float normalize_angle(float angle) {
        angle = (float) (angle % (2 * Math.PI));
        if (angle < 0) angle += 2 * Math.PI;
        return angle;
    }
    private void cast(){
        wall_hit_x = (int)end_x;
        wall_hit_y = (int)end_y;
        found_horizontal_wall = false;
        float horizontal_wall_x = 0;
        float horizontal_wall_y = 0;

        float first_intersection_x = 0;
        float first_intersection_y = 0;

        if (top){
            first_intersection_y = ((start_y / Game.TILE_SIZE) * Game.TILE_SIZE) + Game.TILE_SIZE;
        } else if (bottom){
            first_intersection_y = ((start_y / Game.TILE_SIZE) * Game.TILE_SIZE) - 1;
        }

        first_intersection_x = (float) ( start_x + (first_intersection_y - start_y) / Math.tan((double)this.angle));
        float next_horizontal_x = first_intersection_x;
        float next_horizontal_y = first_intersection_y;
        float xa = 0; float ya = 0;

        if (top){
            ya = Game.TILE_SIZE;
        } else if (bottom){
            ya = -Game.TILE_SIZE;
        }

        xa = (float) (ya / Math.tan(this.angle));
        float dx_total = end_x - start_x;
        float dy_total = end_y - start_y;
        float max_distance = (float) Math.sqrt(dx_total * dx_total + dy_total * dy_total);

        float dx_current, dy_current, distance;
        while (true) {
            dx_current = next_horizontal_x - start_x;
            dy_current = next_horizontal_y - start_y;
            distance = (float) Math.sqrt(dx_current * dx_current + dy_current * dy_current);
            if (distance > max_distance) break;
            if (isInMap(next_horizontal_x, next_horizontal_y)){
                found_horizontal_wall = true;
                horizontal_wall_x = next_horizontal_x;
                horizontal_wall_y = next_horizontal_y;
                break;
            } else {
                next_horizontal_x += xa;
                next_horizontal_y += ya;
            }
        }

        found_vertical_wall = false;
        float vertical_wall_x = 0;
        float vertical_wall_y = 0;

        if (left){
            first_intersection_x = (start_x / Game.TILE_SIZE) * Game.TILE_SIZE - 1;
        } else if (right){
            first_intersection_x = ((start_x / Game.TILE_SIZE) * Game.TILE_SIZE)  + Game.TILE_SIZE;
        }

        first_intersection_y = (float) (start_y + (first_intersection_x - start_x) * Math.tan(angle));

        float next_vertical_x = first_intersection_x;
        float next_vertical_y = first_intersection_y;

        if (left){
            xa = -Game.TILE_SIZE;
        } else if (right){
            xa = Game.TILE_SIZE;
        }

        ya = (float) (xa * Math.tan(angle));

        while (true){
            dx_current = next_vertical_x - start_x;
            dy_current = next_vertical_y - start_y;
            distance = (float) Math.sqrt(dx_current * dx_current + dy_current * dy_current);
            if (distance > max_distance) break;
            if (isInMap(next_vertical_x, next_vertical_y)){
                found_vertical_wall = true;
                vertical_wall_x = next_vertical_x;
                vertical_wall_y = next_vertical_y;
                break;
            } else {
                next_vertical_x += xa;
                next_vertical_y += ya;
            }
        }

        float distance_horizontal = 0;
        float distance_vertical = 0;

        if (found_horizontal_wall){
            distance_horizontal = Maths.Distance(start_x, start_y, horizontal_wall_x, horizontal_wall_y);
        } else {
            distance_horizontal = Float.MAX_VALUE;
        }

        if (found_vertical_wall){
            distance_vertical = Maths.Distance(start_x, start_y, vertical_wall_x, vertical_wall_y);
        }else {
            distance_vertical = Float.MAX_VALUE;
        }

        if (!found_horizontal_wall && !found_vertical_wall) {
            wall_hit_x = (int)end_x;
            wall_hit_y = (int)end_y;
        } else if (distance_horizontal < distance_vertical) {
            wall_hit_x = (int)horizontal_wall_x;
            wall_hit_y = (int)horizontal_wall_y;
        } else {
            wall_hit_x = (int)vertical_wall_x;
            wall_hit_y = (int)vertical_wall_y;
        }
        
    }
    public Boolean rayInterrupt(){
        return found_horizontal_wall || found_vertical_wall;
    }
    private Boolean isInMap(float x, float y){
        for (Rectangle bounds : manager.getCurrentLevel().getTileBounds()){
            if (bounds.contains((int)x,(int)y)){
                return true;
            }
        }
        return false;
    }
    public void render(Graphics g, int offsetX, int offsetY){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawLine((int) start_x - offsetX, (int) start_y - offsetY,  wall_hit_x - offsetX,  wall_hit_y - offsetY);
    }
}
