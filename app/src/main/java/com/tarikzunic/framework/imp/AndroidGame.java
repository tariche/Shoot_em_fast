package com.tarikzunic.framework.imp;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.Window;

import com.tarikzunic.framework.Audio;
import com.tarikzunic.framework.FileIO;
import com.tarikzunic.framework.Game;
import com.tarikzunic.framework.Graphics;
import com.tarikzunic.framework.Input;
import com.tarikzunic.framework.Screen;

/**
 * Created by Tarik on 21.4.2015.
 */
public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;
    View decor;
    int uiOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        decor = getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;


        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 320 : 480;
        int frameBufferHeight = isPortrait ? 480 : 320;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        float scaleX, scaleY;
        if (VERSION.SDK_INT < 17) {
            scaleX = (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
            scaleY = (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();
        } else {
            Point screenSize = new Point();
            getWindowManager().getDefaultDisplay().getRealSize(screenSize); //getSize(screenSize)
            scaleX = (float) frameBufferWidth / screenSize.x; //width();
            scaleY = (float) frameBufferHeight / screenSize.y; //height();
        }
//        Log.d("Dodir", "scaleX: " + scaleX + " scaleY: " + scaleY);

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);

//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GLGame");
    }

    @Override
    protected void onResume() {
        super.onResume();
        decor.setSystemUiVisibility(uiOptions);
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

}
