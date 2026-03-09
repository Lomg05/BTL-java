package Levels;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapInfo {
    public Map<String, int[][]> data;
    public List<Rectangle> bounds;
    public ArrayList<Point> enemySpawnPoints;
    public Point PlayerSpawnPoint;
    public int tileInWidth;
    public int tileInHeight;
    public int Map_Width;
    public int Map_Height;
}
