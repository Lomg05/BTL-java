package ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextDamePool {
    private List<TextDame> textDames;
    private int poolSize;
    private final float maxExtralLifeTime = 2.0f;
    private List<Float> extralLifeTimes;
    public TextDamePool(int size) {
        textDames = new ArrayList<TextDame>();
        extralLifeTimes = new ArrayList<>();
        poolSize = size;
        InitPool();
    }
    private void InitPool(){
        for ( int i = 0; i < poolSize; i++ ){
            textDames.add(new TextDame());
        }
    }

    public void spawnTextDame(int x , int y , String text, Color color){
        for (TextDame textDame : textDames){
            if (!textDame.active){
                textDame.activate(x,y,text,color);
                return;
            }
        }
        TextDame textDame = new TextDame();
        textDame.activate(x,y,text,color);
        textDames.add(textDame);
        extralLifeTimes.add(0.0f);
    }
    public void update(float delta){
        System.out.println(textDames.size());
        for (TextDame textDame : textDames){
            if (textDame.active){
                textDame.update(delta);
            }
        }
        for (int i = extralLifeTimes.size() - 1; i >= 0; i--) {
            int idx = i + poolSize;
            if (!textDames.get(idx).active) {
                float newTimer = extralLifeTimes.get(i) + delta * 2;
                if (newTimer > maxExtralLifeTime) {
                    textDames.remove(idx);
                    extralLifeTimes.remove(i);
                } else {
                    extralLifeTimes.set(i, newTimer);
                }
            }
        }
    }
    public void render(Graphics g, int offsetX, int offsetY){
        for (TextDame textDame : textDames){
            if (textDame.active){
                textDame.render(g, offsetX, offsetY);
            }
        }
    }
}
