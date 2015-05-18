package com.tarikzunic.shootemfast;

import android.graphics.Color;
import android.graphics.Paint;

import com.tarikzunic.framework.Game;
import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Input.TouchEvent;
import com.tarikzunic.framework.Screen;

import java.util.List;

/**
 * Created by tarik on 5/7/15.
 */
public class GameScreen extends Screen {
    enum GameStatus {
        Running,
        GameOver
    }

    GameStatus status = GameStatus.Running;
    World world;
    int oldScore = 0;
    String printScore = "0";
    String textScore = "SCORE:";
    int textScoreHeight;
    Paint paint;


    public GameScreen(Game game) {
        super(game);
        world = new World();
        paint = game.getGraphics().setPaintText(Color.BLUE, 24);
        textScoreHeight = game.getGraphics().textBounds(textScore, paint).height();
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

        if (status == GameStatus.Running) {
            updateRunning(touchEvents, deltaTime);
        }
        /*if (status == GameStatus.Paused) {
            updatePaused(touchEvents);
        }*/
        if (status == GameStatus.GameOver) {
            updateGameOver(touchEvents);
        }
    }

    private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touchEvent = touchEvents.get(i);

            /*if (touchEvent.type == TouchEvent.TOUCH_UP) {
                if (touchEvent.x < 81 && touchEvent.y < 81) {
                    if (Settings.soundEabled) {
                        Assets.shotSnd.play(1);
                    }

                    status = GameStatus.Paused;
                }
            }*/

            if (touchEvent.type == TouchEvent.TOUCH_DOWN) {
                if (touchEvent.x > 152 && touchEvent.x < 330 && touchEvent.y > 596) {
                    if (Settings.soundEabled) {
                        Assets.shotSnd.play(1);
                    }
                    world.shot = true;
                }
                /*if (touchEvent.x > 81 && touchEvent.y > 81) {
                    world.shot = true;
                }*/
            }
        }

        world.update(deltaTime);

        if (world.gameOver) {
            status = GameStatus.GameOver;
        }

        if (oldScore != world.score) {
            oldScore = world.score;
            if (Settings.soundEabled) {
                Assets.targetSnd.play(1);
            }
        }
    }

    private void nullify() {
        paint = null;
        System.gc();
    }

    /*private void updatePaused(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == TouchEvent.TOUCH_UP) {

            }
        }
    }*/

    private void updateGameOver(List<TouchEvent> touchEvents) {
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent touchEvent = touchEvents.get(i);
            if (touchEvent.type == TouchEvent.TOUCH_UP) {
                if (touchEvent.x > 81 && touchEvent.y < 81) {
                    Settings.soundEabled = !Settings.soundEabled;
                    if (Settings.soundEabled) {
                        Assets.shotSnd.play(1);
                    }
                }
                if (touchEvent.x > 165 && touchEvent.x < 315) {
                    if (touchEvent.y > 400 && touchEvent.y < 458) {
                        game.setScreen(new GameScreen(game));
                    }
                    if (touchEvent.y > 458 && touchEvent.y < 516) {
                        game.setScreen(new HelpScreen(game));
                    }
                    if (touchEvent.y > 516 && touchEvent.y < 574) {
                        nullify();
                        game.setScreen(new ManiMenuScreen(game));
                    }
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
//        drawWorld(world);

        drawRunning();
        /*if (status == GameStatus.Running) {
            drawRunning();
        }
        if (status == GameStatus.Paused) {
            drawPaused();
        }*/
        if (status == GameStatus.GameOver) {
            drawGameOver();
        }
    }

    /*private void drawWorld(World world) {

    }*/

    private void drawRunning() {
        Graphics g = game.getGraphics();
        int len = world.targetQue.targets.size();
        for (int i = 0; i < len; i++) {
            Target target = world.targetQue.targets.get(i);
            if (target.isVisible) {
                g.drawPixmap(Assets.targets, target.x, target.y, 46 * target.current, 0, 47, 96);
            }
        }

        int bulletsLen = world.bullets.size();
        if (bulletsLen != 0) {
            for (int i = 0; i < bulletsLen; i++) {
                Bullet bullet = world.bullets.get(i);
                g.drawRect(bullet.x - 2, bullet.y - 2, 5, 8, Color.BLACK);
            }
        }
       /* if (world.bullet.visible) {
            Bullet bullet = world.bullet;
            g.drawRect(bullet.x - 2, bullet.y - 2, 5, 8, Color.BLACK);
        }*/

        drawCurrentScore();
    }

    private void drawCurrentScore() {
        Graphics g = game.getGraphics();
        g.drawText(textScore, 20, 5 + textScoreHeight, paint);
        if (Integer.parseInt(printScore) != oldScore) {
            printScore = "" + oldScore;
        }
        g.drawText(printScore, 20, 10 + 2 * textScoreHeight, paint);
    }

    /*private void drawPaused() {

    }*/

    private void drawGameOver() {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.missed, 145, 200);
        g.drawPixmap(Assets.gameover, 165, 400);
    }

    @Override
    public void pause() {
        /*if (state == GameState.Running) {
            state = GameState.Paused;
        }*/
        if (status == GameStatus.GameOver) {
            Settings.addScore(world.score);
            Settings.save(game.getFileIO());
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void backButton() {

    }
}
