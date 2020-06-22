package com.ztl9090909.tankwar;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Tank {

    private boolean enemy;

    private int x;
    private int y;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Direction direction;

    private boolean up, down, left, right;

    public Tank(int x, int y, Direction direction) {
        this( x, y, false,direction);
    }

    public Tank(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    void move() {
        if (this.stopped) return;

        switch (direction) {
            case UP:
                y -= 5;
                break;
            case UPLEFT:
                x -= 5;
                y -= 5;
                break;
            case UPRIGHT:
                x += 5;
                y -= 5;
                break;
            case DOWN:
                y += 5;
                break;
            case DOWNLEFT:
                x -= 5;
                y += 5;
                break;
            case DOWNRIGHT:
                x += 5;
                y += 5;
                break;
            case LEFT:
                x -= 5;
                break;
            case RIGHT:
                x += 5;
                break;

        }
    }

    Image getImage() {
        String prefix = enemy ? "e" : "";
        switch (direction) {
            case UP:
                return new ImageIcon("assets/images/" + prefix + "tankU.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/" + prefix + "tankLU.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankRU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/" + prefix + "tankD.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/" + prefix + "tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankRD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/" + prefix + "tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/" + prefix + "tankR.gif").getImage();

        }
        return null;
    }

    void draw(Graphics g) {
        int oldX = x;
        int oldY = y;
        this.determineDirection();
        this.move();

        if (x < 0 ) x = 0;
        else if ( x > 800 - getImage().getWidth(null)) x = 800 - getImage().getWidth(null);
        if ( y < 0 ) y = 0;
        else if (y > 600 - getImage().getHeight(null)) y = 600 - getImage().getHeight(null);

        Rectangle rec = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (rec.intersects(wall.getRectangle())) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        for (Tank tank : GameClient.getInstance().getEnemyTank()) {
            if (tank != this && rec.intersects(tank.getRectangle())) {
                x = oldX;
                y = oldY;
                break;
            }
        }

        g.drawImage(this.getImage(), this.x, this.y, null);
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;

        }
        this.determineDirection();
    }

    private void fire(){
        Missle  missle = new Missle(x + getImage().getWidth(null) / 2 - 6,
                y + getImage().getHeight(null) / 2 - 6, false, direction);
        GameClient.getInstance().getMissles().add(missle);
    }

    private boolean stopped;

    private void determineDirection() {
        if (!up && !right && !down && !left) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right) this.direction = Direction.UPLEFT;
            else if (up && right && !down && !left) this.direction = Direction.UPRIGHT;
            else if (down && left && !up && !right) this.direction = Direction.DOWNLEFT;
            else if (down && right && !up && !left) this.direction = Direction.DOWNRIGHT;
            else if (up && !right && !down && !left) this.direction = Direction.UP;
            else if (!up && !right && down && !left) this.direction = Direction.DOWN;
            else if (!up && !right && !down && left) this.direction = Direction.LEFT;
            else if (!up && right && !down && !left) this.direction = Direction.RIGHT;

            this.stopped = false;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
        this.determineDirection();
    }
}