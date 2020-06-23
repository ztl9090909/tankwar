package com.ztl9090909.tankwar;

import java.awt.*;

public class Explosion {

    private int x, y;

    private int step = 0;

    private boolean live = true;

    boolean isLive() {
        return live;
    }

    void setLive(boolean live) {
        this.live = live;
    }

    Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void draw(Graphics g){
        if (step > 10){
            this.setLive(false);
            return;
        }
        g.drawImage(Tools.getImage(step++ + ".gif"), x, y, null);
    }

}
