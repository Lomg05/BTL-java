package ui;

import utilz.LoadSave;

import java.awt.*;

public class TextDame {
    public Boolean active = true;
    private Font font;
    private int x, y, size;
    private String text;
    public float opacity = 225;
    public Color color;
    public TextDame() {
        font = LoadSave.LoadFont(LoadSave.FONT);
        this.size = 30;
        color = Color.GREEN;
        text = new String("");
    }
    public void activate(int x , int y , String text , Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.active = true;
        this.opacity = 225;
        this.color = color;
    }
    public void update(float delta) {
        if (!active) {return;}
        y -= 300 * delta;
        opacity -= 600 * delta;
        if (opacity <= 0.1f) {
            opacity = 0;
            active = false;
        }
    }
    public void render(Graphics g, int offsetX, int offsetY) {
        if (!active) {return;}
        g.setFont(font.deriveFont((float) size));
        int alpha = (int) Math.min(225, Math.max(0, (int)opacity));
        g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),alpha));
        g.drawString(text,x - offsetX,y - offsetY);
    }
}
