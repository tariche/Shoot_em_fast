package com.tarikzunic.shootemfast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tarik on 8.5.2015.
 */
public class World {
    static final float TICK = 0.02f;

    TargetQue targetQue;
    List<Bullet> bullets = new ArrayList<>();
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

        if (shot) {
            shot = false;
            bullets.add(new Bullet());
        }

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet.y < -20) {
                gameOver = true;
            }
            if (targetQue.checkCollision(bullet)) {
                score += targetQue.success;
                bullet.visible = false;
            }
            if (bullet.visible) {
                bullet.update();
            } else
                bullets.remove(i);
        }

        if (targetQue.targetEcsaped()) {
            gameOver = true;
        }

        /*if (shot) {
            bullet.visible = true;
            int onTarget = targetQue.shot();
            if (onTarget == 0) {
                gameOver = true;
            } else {
                score += onTarget;
            }
            shot = false;
        }

        if (bullet.visible) {
            bullet.y -= 20;
            if (bullet.y < 70) {
                bullet.visible = false;
                bullet.y = 596;
            }
        }*/
    }

}
