package main;

import main.hitboxes.PingRectangle;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static main.GamePanel.keyH;

public class Player {

    private ArrayList<PingRectangle> hitboxRect;

    private int pnumber;

    private int score;

    private double dx, dy;

    private ArrayList<PlayerRules> playerRules;

    public Player(double x, double y, double width, double height, int pnumber) {
        hitboxRect = new ArrayList<>();
        hitboxRect.add(new PingRectangle(x, y, width, height));
        dx = 0;
        dy = 0;
        this.pnumber = pnumber;
        score = 0;
    }

    public void reset() {
    }

    public void changeRules() {
        //Modifiers will not be exclusive
        //Game rules will be chosen first to determine interference with any other rules
        //Game rules will be chosen in Pinggame


        //Add player rules
        playerRules = new ArrayList<>();
        do {
            playerRules.add(PlayerRules.values()[ThreadLocalRandom.current().nextInt(0, PlayerRules.values().length)]);
        } while (ThreadLocalRandom.current().nextInt(0, 2) == 0);


        //Remove duplicates
        ArrayList<PlayerRules> tempPr = new ArrayList<>();

        for (PlayerRules pr:
             playerRules) {
            if(!tempPr.contains(pr)) {
                tempPr.add(pr);
            }
        }

        playerRules = tempPr;

        //Split player paddle
        if (playerRules.contains(PlayerRules.SPLIT_PADDLE)) {
            if (hitboxRect.size() == 1) {
                hitboxRect.get(0).setHeight(hitboxRect.get(0).getHeight() / 2);

                PingRectangle cloneRect = new PingRectangle(hitboxRect.get(0).x, hitboxRect.get(0).y, hitboxRect.get(0).width, hitboxRect.get(0).height);
                cloneRect.y = GamePanel.SCREEN_HEIGHT / 2.0 + (GamePanel.SCREEN_HEIGHT / 2.0 - hitboxRect.get(0).y - hitboxRect.get(0).height);
                hitboxRect.add(1, cloneRect);
            }
        } else if (hitboxRect.size() > 1) {
            hitboxRect.remove(1);
            hitboxRect.get(0).height = hitboxRect.get(0).height * 2;
        }

        //Reset player angle
        if (!playerRules.contains(PlayerRules.SPINNING)) {
            for (PingRectangle ph:
                 hitboxRect) {
                ph.angle = 0;
            }
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

        hitboxRect.get(0).x += dx;
        hitboxRect.get(0).y += dy;

        if (playerRules.contains(PlayerRules.SPLIT_PADDLE)) {
            //626
            hitboxRect.get(1).x += dx;
            hitboxRect.get(1).setY(hitboxRect.get(1).getY() - dy);
        }

        if (playerRules.contains(PlayerRules.SPINNING)) {

            for (PingRectangle pr:
                    hitboxRect) {
                pr.angle += Math.PI / 30;
            }

            //hitboxRect.angle += Math.PI / 30;
        }
    }

    public ArrayList<PingRectangle> getHitboxRect() {
        return hitboxRect;
    }

    public double getDx() {
        return dx;
    }

    public double getDy() {
        return dy;
    }

    public double getX() {
        return hitboxRect.get(0).x;
    }
    public double getY() {
        return hitboxRect.get(0).y;
    }
    public double getWidth() {
        return hitboxRect.get(0).width;
    }
    public double getHeight() {
        return hitboxRect.get(0).height;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<PlayerRules> getPlayerRules() {
        return playerRules;
    }

    public void setX(int i) {
        hitboxRect.get(0).x = i;
    }
    public void setY(int i) {
        hitboxRect.get(0).y = i;
    }
    public void setWidth(int i) {
        hitboxRect.get(0).width = i;
    }
    public void setHeight(int i) {
        hitboxRect.get(0).height = i;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

//    public void setHitboxRect(PingRectangle hitboxRect) {
//        this.hitboxRect = hitboxRect;
//    }

    public void increaseScore() {
        score++;
    }
}
