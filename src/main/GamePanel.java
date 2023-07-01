package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    static final int SCREEN_WIDTH = 1080;
    static final int SCREEN_HEIGHT = 720;

    static final double FPS = 60;
    Thread gameThread;
    public static KeyHandler keyH = new KeyHandler();

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void init() {
        PingGame.player1.changeRules();
        PingGame.player2.changeRules();
    }

    public void update() {
        PingGame.player1.update();
        PingGame.player2.update();

        PingGame.ball.update();
        if (PingGame.ball.getHitbox().x < -PingGame.ball.getHitbox().width) {
            PingGame.player2.increaseScore();
            PingGame.ball.reset();
            PingGame.player1.changeRules();
            PingGame.player2.changeRules();
        } else if (PingGame.ball.getHitbox().x > 1080) {
            PingGame.player1.increaseScore();
            PingGame.ball.reset();
            PingGame.player1.changeRules();
            PingGame.player2.changeRules();
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);

        //UPDATE FILL RECT CUZ IT WILL DRAW ROTATED RECTS FROM CENTER
        //Draw p1
        g2.rotate(PingGame.player1.getHitboxRect().angle, (int)(PingGame.player1.getX() + PingGame.player1.getHitboxRect().width / 2), (int)PingGame.player1.getY() + PingGame.player1.getHitboxRect().height / 2);
        g2.fillRect((int)PingGame.player1.getX(), (int)PingGame.player1.getY(), (int)PingGame.player1.getWidth(), (int)PingGame.player1.getHeight());
        g2.dispose();

        g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);

        //Draw p2
        g2.rotate(PingGame.player2.getHitboxRect().angle, (int)(PingGame.player2.getX() + PingGame.player2.getHitboxRect().width / 2), (int)PingGame.player2.getY() + PingGame.player2.getHitboxRect().height / 2);
        g2.fillRect((int)PingGame.player2.getX(), (int)PingGame.player2.getY(), (int)PingGame.player2.getWidth(), (int)PingGame.player2.getHeight());
        g2.dispose();

        g2 = (Graphics2D) g.create();
        g2.setColor(Color.WHITE);

        //Draw ball
        g2.fillRect((int)PingGame.ball.getX(), (int)PingGame.ball.getY(), (int)PingGame.ball.getWidth(), (int)PingGame.ball.getHeight());

        g2.drawString(String.valueOf(PingGame.player1.getScore()), (SCREEN_WIDTH / 2) - 100, 40);
        g2.drawString(String.valueOf(PingGame.player2.getScore()), (SCREEN_WIDTH / 2) + 100, 40);
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

        init();

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
