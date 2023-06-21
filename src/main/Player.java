package main;

import main.hitboxes.PingRectangle;

public class Player {

    private PingRectangle hitboxRect;

    private double dx, dy;

    public Player(double x, double y, double width, double height) {
        hitboxRect = new PingRectangle(x, y, width, height);
        dx = 0;
        dy = 0;
    }

    public void update() {
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
}
