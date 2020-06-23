package com.ztl9090909.tankwar;

import java.awt.*;

public class Missle {

    private int x;
    private int y;

    private static final int SPEED = 12;

    private final Direction direction;

    private final boolean enemy;

    private boolean live = true;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    public Missle(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    private Image getImage() {
        return direction.getImage("missile");
    }

    private void move() {
        x += direction.xFactor * SPEED;
        y += direction.yFactor * SPEED;
    }


    public void draw(Graphics g) {
        move();
        if (x < 0 || x > 800 || y < 0 || y > 600) {
            this.live = false;
            return;
        }

        Rectangle rectangle = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()) {
            if (rectangle.intersects(wall.getRectangle())) {
                this.setLive(false);
                return;
            }
        }

        if (enemy){
            Tank playerTank = GameClient.getInstance().getPlayerTank();
            if (rectangle.intersects(playerTank.getRectangle())){
                playerTank.setHp(playerTank.getHp() - 15);
                if (playerTank.getHp() <= 0){
                    playerTank.setLive(false);
                }
                this.setLive(false);
            }
        } else {
            for (Tank tank : GameClient.getInstance().getEnemyTank()){
                if (rectangle.intersects(tank.getRectangle())){
                    tank.setLive(false);
                    this.setLive(false);
                    break;
                }
            }

        }

        g.drawImage(getImage(), x, y, null);
    }

    Rectangle getRectangle(){
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }
}
