package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean p1UpPressed, p1DownPressed;


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            p1UpPressed = true;
        }
        else if (code == KeyEvent.VK_S) {
            p1DownPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            p1UpPressed = false;
        }
        else if (code == KeyEvent.VK_S) {
            p1DownPressed = false;
        }
    }
}
