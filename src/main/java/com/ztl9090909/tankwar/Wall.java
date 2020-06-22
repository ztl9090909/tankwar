package com.ztl9090909.tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Wall {

    private int x;
    private int y;

    private boolean horizontal;

    private int bricks;

    private final Image brickImage;

    public Wall(int x, int y, boolean horizontal, int bricks) {
        this.brickImage = Tools.getImage("brick.png");
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.bricks = bricks;
    }

    Rectangle getRectangle() {
        return horizontal ? new Rectangle(x, y,
                bricks * brickImage.getWidth(null), brickImage.getHeight(null)) :
                new Rectangle(x, y, brickImage.getWidth(null), brickImage.getHeight(null) * bricks);
    }

    public void draw(Graphics g) {
        if (horizontal) {
            for (int i = 0; i < bricks; i++) {
                g.drawImage(brickImage, x + i * brickImage.getWidth(null), y, null);
            }
        } else {
            for (int i = 0; i < bricks; i++) {
                g.drawImage(brickImage, x, y + i * brickImage.getHeight(null), null);
            }
        }
    }
}
