package com.tarikzunic.shootemfast;

/**
 * Created by Tarik on 8.5.2015.
 */
public class World {
    static final float TICK = 0.05f;

    TargetQue targetQue;
    public boolean gameOver = false;
    public boolean shot = false;
    public int score = 0;
    float tick = TICK;
    float tickTime = 0;

    public World() {
        targetQue = new TargetQue();
    }

    public void update(float deltaTime) {
        if (gameOver) {
            return;
        }

        tickTime += deltaTime;
        if (tickTime > tick) {
            tickTime -= tick;
            targetQue.move();
        }

        if (targetQue.targetEcsaped()) {
            gameOver = true;
        }

        if (shot) {
            int onTarget = targetQue.shot();
            if (onTarget == 0) {
                gameOver = true;
            } else {
                score += onTarget;
            }
            shot = false;
        }
    }

}
