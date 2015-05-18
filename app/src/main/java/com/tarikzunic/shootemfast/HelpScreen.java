package com.tarikzunic.shootemfast;

import com.tarikzunic.framework.Game;
import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Input.TouchEvent;
import com.tarikzunic.framework.Screen;

import java.util.List;

/**
 * Created by tarik on 5/7/15.
 */
public class HelpScreen extends Screen {
    public HelpScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
        int len = touchEvents.size();
        for (int i = 0; i < len; i++) {
            TouchEvent event = touchEvents.get(i);
            if (event.type == TouchEvent.TOUCH_UP) {
                if (event.x < 80 && event.y < 80) {
                    game.setScreen(new ManiMenuScreen(game));
                }
            }
        }
    }

    @Override
    public void present(float deltaTime) {
        Graphics g = game.getGraphics();

        g.drawPixmap(Assets.hlpbackground, 0, 0);
        g.drawPixmap(Assets.buttons, 0, 0, 80, 80, 81, 81);
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

    @Override
    public void backButton() {

    }
}
