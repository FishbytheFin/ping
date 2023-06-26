package main;

public class PingGame {
    public static Player player1, player2;
    public static Ball ball;

    public static void main (String[] args) {
        player1 = new Player(20, 260, 40, 200, 1);
        player2 = new Player(1020, 260, 40, 200, 2);

        ball = new Ball();

        GamePanel panel = new GamePanel();
        GameFrame frame = new GameFrame(panel);

        panel.startGameThread();
    }

}
