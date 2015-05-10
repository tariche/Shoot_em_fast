package com.tarikzunic.shootemfast;

/**
 * Created by Tarik on 8.5.2015.
 */
public class Target {
    public int x,y;
    public boolean isVisible;
    boolean inRange, isShot;

    public Target(int x, boolean isVisible) {
        this.x = x;
        this.y = 0;
        this.isVisible = isVisible;
        inRange = false;
        isShot = false;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
}
