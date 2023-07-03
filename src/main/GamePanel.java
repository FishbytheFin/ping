package main;

import main.hitboxes.PingRectangle;

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
        PingGame.ball.changeRules();
    }

    public void update() {
        PingGame.player1.update();
        PingGame.player2.update();

        PingGame.ball.update();

        //Player scores
        if (PingGame.ball.getHitbox().x < -PingGame.ball.getHitbox().width) {
            PingGame.player2.increaseScore();
            PingGame.ball.reset();
            PingGame.player1.changeRules();
            PingGame.player2.changeRules();
            PingGame.ball.changeRules();
        } else if (PingGame.ball.getHitbox().x > 1080) {
            PingGame.player1.increaseScore();
            PingGame.ball.reset();
            PingGame.player1.changeRules();
            PingGame.player2.changeRules();
            PingGame.ball.changeRules();
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        //g2.setColor(Color.WHITE);


        g2.setColor(PingGame.player1.getColor());
        //Draw p1
        for (PingRectangle pr:
             PingGame.player1.getHitboxRect()) {
            g2.rotate(pr.angle, (int)(pr.x + pr.width / 2), (int)pr.y + pr.height / 2);
            g2.fillRect((int)pr.x, (int)pr.y, (int)pr.width, (int)pr.height);
            g2.dispose();

            g2 = (Graphics2D) g.create();
            g2.setColor(PingGame.player1.getColor());
        }


        g2.setColor(PingGame.player2.getColor());
        //Draw p2
        for (PingRectangle pr:
                PingGame.player2.getHitboxRect()) {
            g2.rotate(pr.angle, (int)(pr.x + pr.width / 2), (int)pr.y + pr.height / 2);
            g2.fillRect((int)pr.x, (int)pr.y, (int)pr.width, (int)pr.height);
            g2.dispose();

            g2 = (Graphics2D) g.create();
            g2.setColor(PingGame.player2.getColor());
        }

        g2.setColor(PingGame.ball.getColor());
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
