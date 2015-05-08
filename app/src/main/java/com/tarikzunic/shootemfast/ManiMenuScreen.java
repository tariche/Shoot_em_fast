package com.tarikzunic.shootemfast;

import com.tarikzunic.framework.Game;
import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Input.TouchEvent;
import com.tarikzunic.framework.Screen;

import java.util.List;

/**
 * Created by tarik on 5/7/15.
 */
public class ManiMenuScreen extends Screen {
    public ManiMenuScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x > 100 && event.x < 380) {
                    if (event.y > 400 && event.y < 463) {
                        game.setScreen(new GameScreen(game));
                    }
                    if (event.y > 464 && event.y < 528) {
                        game.setScreen(new HighScoreScreen(game));
                    }
                    if (event.y > 529 && event.y < 592) {
                        game.setScreen(new HelpScreen(game));
                    }
                }

                if (event.x < 80 && event.y < 80) {
                    Settings.soundEabled = !Settings.soundEabled;
                    if (Settings.soundEabled) {
                        Assets.shotSnd.play(1);
                    }
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.background, 0, 0);
        g.drawPixmap(Assets.name, 130, 150);
        g.drawPixmap(Assets.mainmenu,100,400);
        if (Settings.soundEabled) {
            g.drawPixmap(Assets.buttons, 0, 0, 80, 0, 81, 81);
        } else {
            g.drawPixmap(Assets.buttons, 0, 0, 0, 0, 81, 81);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
