package main;

import main.hitboxes.PingRectangle;

import java.util.HashMap;

public class CollisionManager {

    //Given a rectangle and a point, return whether they overlap
    public static boolean pointOnRect(HashMap<String, Integer> point, HashMap<String, Integer> rect) {
        return point.get("x") >= rect.get("x") && point.get("x") <= rect.get("x") + rect.get("width") && point.get("y") >= rect.get("y") && point.get("y") <= rect.get("y") + rect.get("height");
    }

    //Given 2 rectangles, return whether they overlap
    public static boolean rectOnRect(PingRectangle rect1, PingRectangle rect2) {
        return rect1.x < rect2.x + rect2.width &&
                rect1.x + rect1.width > rect2.x &&
                rect1.y < rect2.y + rect2.height &&
                rect1.height + rect1.y > rect2.y;
    }

    //Given a circle and a point, return whether they overlap
    public static boolean pointOnCircle(HashMap<String, Integer> point, HashMap<String, Integer> circ) {
        int cx = circ.get("x") - point.get("x");
        int cy = circ.get("y") - point.get("y");

        return circ.get("radius") >= Math.sqrt((cx * cx) + (cy * cy));
    }

    //Given 2 circles, return whether they overlap
    public static boolean circleOnCircle(HashMap<String, Integer> circ1, HashMap<String, Integer> circ2) {
        int cr = circ1.get("radius") + circ2.get("radius");
        int cx = circ1.get("x") - circ2.get("x");
        int cy = circ1.get("y") - circ2.get("y");

        return cr >= Math.sqrt((cx * cx) + (cy * cy));
    }


    //Given a rectangle and a circle, return whether they overlap
    //INCOMPLETE
    public static boolean rectOnCircle() {
        return false;
    }
}