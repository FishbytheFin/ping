package main;

import main.hitboxes.PingRectangle;

import java.util.concurrent.ThreadLocalRandom;

import static main.GamePanel.keyH;

public class Player {

    private PingRectangle hitboxRect;

    private int pnumber;

    private int score;

    private double dx, dy;

    private PlayerRules playerRule;

    public Player(double x, double y, double width, double height, int pnumber) {
        hitboxRect = new PingRectangle(x, y, width, height);
        dx = 0;
        dy = 0;
        this.pnumber = pnumber;
        score = 0;
    }

    public void reset() {
    }

    public void changeRules() {
        //Player can only have one player rule as they are all exclusive and change how the controls work
        //Modifiers will not be exclusive
        //Game rules will be chosen first to determine interference with any other rules
        //Game rules will be chosen in Pinggame
        //int numberOfPlayerRules = ThreadLocalRandom.current().nextInt(0, 4 + 1);

        playerRule = PlayerRules.values()[ThreadLocalRandom.current().nextInt(0, PlayerRules.values().length)];

        if (!playerRule.equals(PlayerRules.SPINNING)) {
            hitboxRect.angle = 0;
        }
    }


    public void update() {

        double playerdy = 0.0;
        if (pnumber == 1) {
            if (keyH.p1UpPressed) {
                playerdy -= 6.0;
            }
            if (keyH.p1DownPressed) {
                playerdy += 6.0;
            }
        } else {
            if (keyH.p2UpPressed) {
                playerdy -= 6.0;
            }
            if (keyH.p2DownPressed) {
                playerdy += 6.0;
            }
        }

        dy = playerdy;

        hitboxRect.x += dx;
        hitboxRect.y += dy;

        if (playerRule.equals(PlayerRules.SPINNING)) {
            hitboxRect.angle += Math.PI / 30;
        }
    }

    public PingRectangle getHitboxRect() {
        return hitboxRect;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getX() {
        return hitboxRect.x;
    }
    public double getY() {
        return hitboxRect.y;
    }
    public double getWidth() {
        return hitboxRect.width;
    }
    public double getHeight() {
        return hitboxRect.height;
    }

    public int getScore() {
        return score;
    }

    public PlayerRules getPlayerRule() {
        return playerRule;
    }

    public void setX(int i) {
        hitboxRect.x = i;
    }
    public void setY(int i) {
        hitboxRect.y = i;
    }
    public void setWidth(int i) {
        hitboxRect.width = i;
    }
    public void setHeight(int i) {
        hitboxRect.height = i;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public void setHitboxRect(PingRectangle hitboxRect) {
        this.hitboxRect = hitboxRect;
    }

    public void increaseScore() {
        score++;
    }
}
