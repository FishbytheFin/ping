package main;

import main.hitboxes.PingRectangle;

import java.util.Random;

public class Ball {

    private PingRectangle hitbox;
    private double dy, dx;
    private double angle;
    private double speed;

    public Ball() {
        hitbox = new PingRectangle(0, 0, 50, 50);
        dy = dx = 0;
        angle = 0;
        speed = 14;
        reset();
    }


    public void update() {
        hitbox.x += dx;
        hitbox.y += dy;

        if (hitbox.y <= 0 || hitbox.y >= 720 - hitbox.width) {
            dy *= -1;
        }

        if (!PingGame.player1.getPlayerRule().equals(PlayerRules.SPINNING)) {
            if (CollisionManager.rectOnRect(PingGame.player1.getHitboxRect(), hitbox)) {
                dx *= -1;
                hitbox.x = PingGame.player1.getX() + PingGame.player1.getWidth();

            }
        } else {
            if (hitbox.intersects(PingGame.player1.getHitboxRect())) {
                angle = PingGame.player1.getHitboxRect().angle + Math.PI / 2;
                dx = speed * Math.cos(angle);
                dy = speed * Math.sin(angle);
            }
        }

        if (!PingGame.player2.getPlayerRule().equals(PlayerRules.SPINNING)) {
            if (CollisionManager.rectOnRect(PingGame.player2.getHitboxRect(), hitbox)) {
                dx *= -1;
                hitbox.x = PingGame.player2.getX() - getWidth();
            }
        } else {
            if (hitbox.intersects(PingGame.player2.getHitboxRect())) {
                angle = PingGame.player2.getHitboxRect().angle + Math.PI / 2;
                dx = speed * Math.cos(angle);
                dy = speed * Math.sin(angle);
            }
        }

    }

    public void reset() {
        hitbox.x = 540 - (hitbox.width / 2);
        hitbox.y = 360 - (hitbox.height / 2);
        speedUp();
        Random random = new Random();
        if (random.nextDouble() > 0.5) {
            angle = random.nextDouble() * 0.333 * Math.PI - (0.1666 * Math.PI);
        } else {
            angle = random.nextDouble() * 0.333 * Math.PI - (0.1666 * Math.PI) + Math.PI;
        }

        dx = speed * Math.cos(angle);
        dy = speed * Math.sin(angle);


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

    public void speedUp() {
        speed += 0.2;
    }

}
