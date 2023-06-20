package main;

public class PingGame {


    public static void main (String[] args) {
        GamePanel panel = new GamePanel();
        GameFrame frame = new GameFrame(panel);

        panel.startGameThread();
    }

}
