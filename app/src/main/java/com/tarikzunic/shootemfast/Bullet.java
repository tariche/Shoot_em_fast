package com.tarikzunic.shootemfast;

import android.graphics.Rect;

/**
 * Created by Tarik on 10.5.2015.
 */
public class Bullet {
    boolean visible;
    int x, y;
    Rect r;

    public Bullet() {
        this.visible = true;
        this.x = 240;
        this.y = 586;
        r = new Rect(0, 0, 0, 0);
    }

    public void update() {
        y -= 14;
        r.set(238, y, 242, y + 8);
    }
}
