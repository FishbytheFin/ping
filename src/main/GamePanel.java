package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    static final int SCREEN_WIDTH = 1080;
    static final int SCREEN_HEIGHT = 720;

    static final double FPS = 60;
    Thread gameThread;
    KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void init() {

    }

    public void update() {
        double playerdy = 0.0;
        if (keyH.p1UpPressed) {
            playerdy -= 6.0;
        }
        if (keyH.p1DownPressed) {
            playerdy += 6.0;
        }
        PingGame.player1.setDy(playerdy);

        PingGame.player1.update();
    }

    public void moveBall() {

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect((int)PingGame.player1.getX(), (int)PingGame.player1.getY(), (int)PingGame.player1.getWidth(), (int)PingGame.player1.getHeight());
        g2.dispose();
    }




    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();

                repaint();
                delta--;
            }


        }
    }
}
