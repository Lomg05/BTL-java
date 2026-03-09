package GameState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface StateMethods {
    public void update(float delta);
    public void render(Graphics g);
    public void MouseDragged(MouseEvent e);
    public void MousePressed(MouseEvent e);
    public void MouseReleased(MouseEvent e);
    public void MouseMoved(MouseEvent e);
    public void KeyPressed(KeyEvent e);
    public void KeyReleased(KeyEvent e);

}
