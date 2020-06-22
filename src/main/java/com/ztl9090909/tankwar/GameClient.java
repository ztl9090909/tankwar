package com.ztl9090909.tankwar;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient extends JComponent {

    private Tank playerTank;

    private List<Tank> enemyTank;

    private List<Wall> walls;

    private GameClient() {

        this.playerTank = new Tank(400, 100, Direction.DOWN);
        this.enemyTank = new ArrayList<>(12);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                this.enemyTank.add(new Tank(200 + j * 80, 400 + 40 * i, true, Direction.UP));
            }
        }
        this.walls = Arrays.asList(
                new Wall(200, 140, true, 15),
                new Wall(200, 540, true, 15),
                new Wall(100, 80, false, 15),
                new Wall(700, 80, false, 15)
        );

        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        playerTank.draw(g);
        for (Tank tank : enemyTank) {
            tank.draw(g);
        }
        for (Wall wall : walls){
            wall.draw(g);
        }
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("坦克大战！");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        final GameClient client = new GameClient();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                client.playerTank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                client.playerTank.keyReleased(e);
            }
        });
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        while (true) {
            client.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
