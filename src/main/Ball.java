package main;

import main.hitboxes.PingRectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Ball {

    private PingRectangle hitbox;
    private ArrayList<BallRules> ballRules;
    private double dy, dx;
    private double angle;
    private double speed;
    private int colorFrames;
    private Color ballColor;

    public Ball() {
        hitbox = new PingRectangle(0, 0, 50, 50);
        dy = dx = 0;
        angle = 0;
        speed = 14;
        ballColor = Color.white;
        reset();
    }

    public void update() {
        hitbox.x += dx;
        hitbox.y += dy;

        if (hitbox.y <= 0) {
            hitbox.y = 0;
            dy *= -1;
        } else if (hitbox.y >= 720 - hitbox.width) {
            hitbox.y = 720 - hitbox.width;
            dy *= -1;
        }

        //Rainbow Ball
        if (ballRules.contains(BallRules.RAINBOW)) {
            colorFrames--;
            if (colorFrames == 0) {
                ballColor = PingGame.rainbowColors[ThreadLocalRandom.current().nextInt(0, PingGame.rainbowColors.length)];
                colorFrames = 15;
            }
        }


        //PLayer 1 collisions
        for (PingRectangle pr:
             PingGame.player1.getHitboxRect()) {
            if (!PingGame.player1.getPlayerRules().contains(PlayerRules.SPINNING)) {
                if (CollisionManager.rectOnRect(pr, hitbox)) {
                    dx *= -1;
                    hitbox.x = pr.x + pr.width;
                    if (ballRules.contains(BallRules.RAINBOW)) {
                        PingGame.player1.setColor(ballColor);
                    }
                }
            } else {
                if (hitbox.intersects(pr)) {
                    angle = pr.angle + Math.PI / 2;
                    dx = speed * Math.cos(angle);
                    dy = speed * Math.sin(angle);
                    if (ballRules.contains(BallRules.RAINBOW)) {
                        PingGame.player1.setColor(ballColor);
                    }
                }
            }
        }

        //PLayer 2 collisions
        for (PingRectangle pr:
                PingGame.player2.getHitboxRect()) {
            if (!PingGame.player2.getPlayerRules().contains(PlayerRules.SPINNING)) {
                if (CollisionManager.rectOnRect(pr, hitbox)) {
                    dx *= -1;
                    hitbox.x = pr.x - getWidth();
                    if (ballRules.contains(BallRules.RAINBOW)) {
                        PingGame.player2.setColor(ballColor);
                    }
                }
            } else {
                if (hitbox.intersects(pr)) {
                    angle = pr.angle + Math.PI / 2;
                    dx = speed * Math.cos(angle);
                    dy = speed * Math.sin(angle);
                    if (ballRules.contains(BallRules.RAINBOW)) {
                        PingGame.player2.setColor(ballColor);
                    }
                }
            }
        }
    }

    public void changeRules() {
        //Modifiers will not be exclusive
        //Game rules will be chosen first to determine interference with any other rules
        //Game rules will be chosen in Pinggame


        //Add player rules
        ballRules = new ArrayList<>();
        do {
            ballRules.add(BallRules.values()[ThreadLocalRandom.current().nextInt(0, BallRules.values().length)]);
        } while (ThreadLocalRandom.current().nextInt(0, 2) == 0);


        //Remove duplicates
        ArrayList<BallRules> tempPr = new ArrayList<>();

        for (BallRules pr:
                ballRules) {
            if(!tempPr.contains(pr)) {
                tempPr.add(pr);
            }
        }

        ballRules = tempPr;

        if (ThreadLocalRandom.current().nextInt(0, 5) == 0) {
            ballColor = Color.white;
        }

        if (ballRules.contains(BallRules.RAINBOW)) {
            ballColor = PingGame.rainbowColors[ThreadLocalRandom.current().nextInt(0, PingGame.rainbowColors.length)];
            colorFrames = 15;
        }

        hitbox.width = 50;
        hitbox.height = 50;

        if (ballRules.contains(BallRules.BIG)) {
            hitbox.width *= 1.5;
            hitbox.height *= 1.5;
        }
        if (ballRules.contains(BallRules.SMALL)) {
            hitbox.width *= 0.75;
            hitbox.width *= 0.75;
        }
        if (ballRules.contains(BallRules.OVAL)) {
            if (ThreadLocalRandom.current().nextInt(0, 2) == 1) {
                hitbox.width *= 1.5;
            } else {
                hitbox.height *= 1.5;
            }
        }

        //System.out.println(ballRules);

    }

    public void reset() {
        hitbox.x = GamePanel.SCREEN_WIDTH / 2.0 - (hitbox.width / 2);
        hitbox.y = GamePanel.SCREEN_HEIGHT / 2.0 - (hitbox.height / 2);
        speedUp();
        Random random = new Random();
        if (random.nextDouble() > 0.5) {
            angle = random.nextDouble() * 0.333 * Math.PI - (0.1666 * Math.PI);
        } else {
            angle = random.nextDouble() * 0.333 * Math.PI - (0.1666 * Math.PI) + Math.PI;
        }

        dx = speed * Math.cos(angle);
        dy = speed * Math.sin(angle);

        if (dx > 0) {
            hitbox.x -= GamePanel.SCREEN_WIDTH / 4.0;
        } else {
            hitbox.x += GamePanel.SCREEN_WIDTH / 4.0;
        }


    }

    public double getX() {
        return hitbox.x;
    }

    public double getY() {
        return hitbox.y;
    }

    public double getWidth() {
        return hitbox.width;
    }

    public double getHeight() {
        return hitbox.height;
    }

    public PingRectangle getHitbox() {
        return hitbox;
    }

    public Color getColor() {
        return ballColor;
    }

    public void speedUp() {
        speed += 0.2;
    }

}
