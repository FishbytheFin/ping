package main;

import main.hitboxes.PingRectangle;

import static main.GamePanel.keyH;

public class Player {

    private PingRectangle hitboxRect;

    private int pnumber;

    private int score;

    private double dx, dy;

    public Player(double x, double y, double width, double height, int pnumber) {
        hitboxRect = new PingRectangle(x, y, width, height);
        dx = 0;
        dy = 0;
        this.pnumber = pnumber;
        score = 0;
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
