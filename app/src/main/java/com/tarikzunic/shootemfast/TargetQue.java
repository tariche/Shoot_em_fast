package com.tarikzunic.shootemfast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Tarik on 8.5.2015.
 */
public class TargetQue {
    List<Target> targets = new ArrayList<>(10);
    Random r = new Random();
    int success = 0;

    public TargetQue() {
        for (int i = 0; i < 10; i++) {
            targets.add(new Target(480 + i * 48, r.nextBoolean()));
        }
    }

    public void move() {
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            target.x -= 2;
            target.r.set(target.x, 65, target.x + 46, 79);
            if (target.x == 0) {
                target.x = 480;
                target.current = 0;
                target.isShot = false;
                target.isVisible = r.nextBoolean();
            }

            if (target.isVisible && target.x < 300 && !target.isShot) {
                target.inRange = true;
            }

            if (target.isShot) {
                ++target.current;
                if (target.current == 4) {
                    target.current = 0;
                    target.isVisible = false;
                    target.isShot = true;
                }
            }
        }
    }

    /*public int shot() {
        int success = 0;
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            if (target.inRange && target.isVisible) {
                int targetX = target.x;
                if (targetX < 240 && (targetX + 46) > 240) {
                    success += 1;
                    if ((targetX + 8) < 240 && (targetX + 38) > 240) {
                        success += 1;
                        if ((targetX + 30) < 240 && (targetX + 30) > 240) {
                            success += 1;
                        }
                    }
                    target.isShot = true;
                    target.isVisible = false;
                    return success;
                }
            }
        }
        return success;
    }*/

    public boolean targetEcsaped() {
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            if ((target.x + 48) < 200 && target.isVisible) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(Bullet bullet) {
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            if (target.inRange) {
                if (target.r.intersect(bullet.r)) {
                    target.inRange = false;
                    target.isShot = true;
                    shot(target, bullet);
                    return true;
                }
            }
        }
        return false;
    }

    private void shot(Target target, Bullet bullet) {
        success = 0;
        int targetX = target.x;
        if (bullet.x > targetX - 1 && bullet.x < targetX + 46 + 1) { // testiraj
            success += 1;
            if (bullet.x > targetX + 8 && bullet.x < targetX + 38) {
                success += 1;
                if (bullet.x > targetX + 16 && bullet.x < targetX + 30) {
                    success += 1;
                }
            }
//            target.isShot = true;
//            target.isVisible = false;
        }
    }
}
