package main;

public class PingGame {
    public static Player player1;

    public static void main (String[] args) {
        player1 = new Player(20, 260, 40, 200);
        GamePanel panel = new GamePanel();
        GameFrame frame = new GameFrame(panel);

        panel.startGameThread();
    }

}
