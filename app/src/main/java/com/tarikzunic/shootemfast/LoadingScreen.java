package com.tarikzunic.shootemfast;

import android.content.Context;
import android.graphics.Typeface;

import com.tarikzunic.framework.Audio;
import com.tarikzunic.framework.FileIO;
import com.tarikzunic.framework.Game;
import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Screen;

/**
 * Created by Tarik on 1.5.2015.
 */
public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    @Override
    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", Graphics.PixmapFormat.RGB565);
        Assets.hlpbackground = g.newPixmap("hlpbackground.png", Graphics.PixmapFormat.RGB565);
        Assets.buttons = g.newPixmap("buttons.png", Graphics.PixmapFormat.ARGB4444);
        Assets.mainmenu = g.newPixmap("mainmenu.png", Graphics.PixmapFormat.ARGB4444);
        Assets.gameover = g.newPixmap("gameover.png", Graphics.PixmapFormat.ARGB4444);
        Assets.name = g.newPixmap("name.png", Graphics.PixmapFormat.ARGB4444);
        Assets.missed = g.newPixmap("missed.png", Graphics.PixmapFormat.ARGB4444);
        Assets.rifle = g.newPixmap("rifle.png", Graphics.PixmapFormat.ARGB4444);
        Assets.target = g.newPixmap("target.png", Graphics.PixmapFormat.ARGB4444);
        Assets.targets = g.newPixmap("targets.png", Graphics.PixmapFormat.ARGB4444);
        Assets.shotSnd = game.getAudio().newSound("shotSnd.ogg");
        Assets.targetSnd = game.getAudio().newSound("targetSnd.ogg");
        Settings.load(game.getFileIO());
        game.setScreen(new ManiMenuScreen(game));
    }

    @Override
    public void present(float deltaTime) {

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
