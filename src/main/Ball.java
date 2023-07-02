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

        //PLayer 1 collisions
        for (PingRectangle pr:
             PingGame.player1.getHitboxRect()) {
            if (!PingGame.player1.getPlayerRules().contains(PlayerRules.SPINNING)) {
                if (CollisionManager.rectOnRect(pr, hitbox)) {
                    dx *= -1;
                    hitbox.x = pr.x + pr.width;

                }
            } else {
                if (hitbox.intersects(pr)) {
                    angle = pr.angle + Math.PI / 2;
                    dx = speed * Math.cos(angle);
                    dy = speed * Math.sin(angle);
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

                }
            } else {
                if (hitbox.intersects(pr)) {
                    angle = pr.angle + Math.PI / 2;
                    dx = speed * Math.cos(angle);
                    dy = speed * Math.sin(angle);
                }
            }
        }



//
//        if (!PingGame.player2.getPlayerRules().contains(PlayerRules.SPINNING)) {
//            if (CollisionManager.rectOnRect(PingGame.player2.getHitboxRect(), hitbox)) {
//                dx *= -1;
//                hitbox.x = PingGame.player2.getX() - getWidth();
//            }
//        } else {
//            if (hitbox.intersects(PingGame.player2.getHitboxRect())) {
//                angle = PingGame.player2.getHitboxRect().angle + Math.PI / 2;
//                dx = speed * Math.cos(angle);
//                dy = speed * Math.sin(angle);
//            }
//        }

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
