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

    public TargetQue() {
        for (int i = 0; i < 10; i++) {
            targets.add(new Target(480 + i * 48, r.nextBoolean()));
        }
    }

    public void move() {
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            target.x -= 1;
            if (target.x == 0) {
                target.x = 480;
                target.isShot = false;
                target.isVisible = r.nextBoolean();
            }

            if (target.x < 240 && (target.x + 48) > 240) {
                target.inRange = true;
            }
        }
    }

    public int shot() {
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
    }

    public boolean targetEcsaped() {
        for (int i = 0; i < 10; i++) {
            Target target = targets.get(i);
            if ((target.x + 48) < 240 && target.isVisible) {
                return true;
            }
        }
        return false;
    }
}
