package main;

import javax.swing.*;

public class GameFrame extends JFrame {

    public GameFrame(GamePanel panel) {

        this.add(panel);
        this.setTitle("Ping");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }

}
