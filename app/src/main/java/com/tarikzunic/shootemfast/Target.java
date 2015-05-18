package com.tarikzunic.shootemfast;

import android.graphics.Rect;

/**
 * Created by Tarik on 8.5.2015.
 */
public class Target {
    public static final int TARGET_COLUMNS = 4;
    public int x,y;
    public boolean isVisible;
    boolean inRange, isShot;
    Rect r;
    int current;

    public Target(int x, boolean isVisible) {
        this.x = x;
        this.y = 0;
        this.isVisible = isVisible;
        inRange = false;
        isShot = false;
        r = new Rect(0, 0, 0, 0);
        current = 0;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
