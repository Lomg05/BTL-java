package utilz;

import Levels.MapInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.List;

public class LoadSave {
    public static final String BACKGROUND_COLOR = "Background.png";
    public static final String TREE_BACK = "Tree_back.png";
    public static final String TREE_FRONT = "Tree_front.png";
    public static final String FONT = "m6x11plus.ttf";
    public static final String LEVEL_1 = "level1.tmx";
    public static final String LEVEL_2 = "level2.tmx";
    public static final String LEVEL_3 = "level3.tmx";
    public static final String TILE_MAP_DATA = "Assets.png";
    public static final String PLAYER = "Fbk.png";
    public static final String BLUEGOLEM = "BlueGolem.png";
    public static final String GOLDGOLEM = "GoldGolem.png";
    public static final String BRINGER_OF_DEATH = "BringerOfDeath.png";
    public static final String ITEMS = "items.png";
    public static final String FBK_ICON = "FbkIcon.png";
    public static final String MENU_BUTTON = "MenuButtons.png";
    public static final String MENU_BUTTON_BACKGROUND = "menu_background.png";
    public static final String MENU_BACKGROUND = "MenuBackGround.png";
    public static final String PAUSE_BACKGROUND = "pause_background.png";
    public static final String SOUND_BUTTON = "sound_button.png";
    public static final String URM_BUTTOM = "urm_button.png";
    public static final String VOLUME = "volumn_num.png";
    public static final String SLIDER = "slider.png";
    public static final String COMPLETE_IMG = "levelcompletebackgorund.png";
    public static final String GAME_COMPLETE = "gameCompleted.png";
    public static final String GAME_OVER = "gameOver.png";
    public static final String PROCESS_BAR = "process.png";
    public static final String CONTROL = "Control.png";
    public static BufferedImage loadImage(String filename) {
        BufferedImage image = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
        try {
            image = ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static MapInfo getLevelData(String filename) {
        MapInfo info = new MapInfo();
        info.data = new LinkedHashMap<>();
        info.bounds = new ArrayList<>();
        info.enemySpawnPoints = new ArrayList<>();
        info.PlayerSpawnPoint = new Point();
        try {
            InputStream file = LoadSave.class.getResourceAsStream("/" + filename);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(file);
            doc.getDocumentElement().normalize();
            Element mapElement = doc.getDocumentElement();
            //map size
            info.tileInWidth = Integer.parseInt(mapElement.getAttribute("width"));
            info.tileInHeight = Integer.parseInt(mapElement.getAttribute("height"));

            //map layer
            NodeList layers = doc.getElementsByTagName("layer");
            for (int i = 0; i < layers.getLength(); i++) {
                Element layer = (Element) layers.item(i);
                String name = layer.getAttribute("name");
                int width = Integer.parseInt(layer.getAttribute("width").trim());
                int height = Integer.parseInt(layer.getAttribute("height").trim());
                Element data = (Element) layer.getElementsByTagName("data").item(0);
                String encoding = data.getAttribute("encoding");
                String rawData = data.getTextContent().trim();

                String token[] = rawData.split(",");
                int[][] tileMap = new int[height][width];

                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        int index = row * width + col;
                        int value = Integer.parseInt(token[index].trim());
                        if (value > 0) {
                            value = value - 1;
                        }
                        tileMap[row][col] = value;
                    }
                }
                info.data.put(name, tileMap);
            }
            //tile bounds
            NodeList objectgroup = doc.getElementsByTagName("objectgroup");
            for (int i = 0; i < objectgroup.getLength(); i++) {
                Element grp = (Element) objectgroup.item(i);
                String name = grp.getAttribute("name");
                if (name.equals("TileBounds")) {
                    NodeList objects = grp.getElementsByTagName("object");

                    for (int j = 0; j < objects.getLength(); j++) {
                        Element obj = (Element) objects.item(j);
                        if (obj.hasAttribute("gid")) {
                            int x = (int) Float.parseFloat(obj.getAttribute("x").trim());
                            int y = (int) Float.parseFloat(obj.getAttribute("y").trim());
                            int width = (int) Float.parseFloat(obj.getAttribute("width").trim());
                            int height = (int) Float.parseFloat(obj.getAttribute("height").trim());
                            info.bounds.add(new Rectangle(x, y - height, width, height));
                        } else {
                            int x = (int) Float.parseFloat(obj.getAttribute("x").trim());
                            int y = (int) Float.parseFloat(obj.getAttribute("y").trim());
                            int width = (int) Float.parseFloat(obj.getAttribute("width").trim());
                            int height = (int) Float.parseFloat(obj.getAttribute("height").trim());
                            info.bounds.add(new Rectangle(x, y, width, height));
                        }
                    }
                }
            }
            for (int i = 0; i < objectgroup.getLength(); i++) {
                Element grp = (Element) objectgroup.item(i);
                String name = grp.getAttribute("name");
                if (name.equals("EnemySpawnPoint")) {
                    NodeList objects = grp.getElementsByTagName("object");
                    for (int j = 0; j < objects.getLength(); j++) {
                        Element obj = (Element) objects.item(j);
                        float x = Float.parseFloat(obj.getAttribute("x").trim());
                        float y = Float.parseFloat(obj.getAttribute("y").trim());
                        info.enemySpawnPoints.add(new Point((int)x, (int)y));
                    }
                } else if (name.equals("PlayerSpawnPoint")) {
                    NodeList objects = grp.getElementsByTagName("object");
                    for (int j = 0; j < objects.getLength(); j++) {
                        Element obj = (Element) objects.item(j);
                        float x = Float.parseFloat(obj.getAttribute("x").trim());
                        float y = Float.parseFloat(obj.getAttribute("y").trim());
                        info.PlayerSpawnPoint = new Point((int)x, (int)y);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }

    public static Font LoadFont(String filename) {
        Font font;
        try{
            InputStream is = LoadSave.class.getResourceAsStream("/" + filename);
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (FontFormatException e) {
            throw new RuntimeException(e);
        }
        return font;
    }
}
