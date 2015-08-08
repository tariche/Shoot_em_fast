package com.tarikzunic.framework.imp;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager.WakeLock;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
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
    Advertising advertising;
    Screen screen;
    WakeLock wakeLock;
    View decor;
    int uiOptions;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        decor = getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decor.setSystemUiVisibility(uiOptions);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 480 : 800;
        int frameBufferHeight = isPortrait ? 800 : 480;
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
        advertising = new Advertising(this);
        screen = getStartScreen();

        adView = new AdView(this);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.setAdSize(AdSize.SMART_BANNER);

        RelativeLayout mainLayout = new RelativeLayout(this);
        mainLayout.addView(renderView);

        RelativeLayout.LayoutParams addParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        addParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mainLayout.addView(adView, addParams);

        setContentView(mainLayout);//(renderView);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        /*PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GLGame");*/
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            decor.setSystemUiVisibility(uiOptions);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        decor.setSystemUiVisibility(uiOptions);
//        showBanner();
        screen.resume();
        renderView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        wakeLock.release();
//        advertising.showAd();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            adView.destroy();
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
    public Advertising getAdvertising() {
        return advertising;
    }

    /*@Override
    public AdView getAdView() {
        return adView;
    }*/

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

    /*public void showInterstitial() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    advertising.showAd();
                }
            });
        } else {
            advertising.showAd();
        }
    }*/

    @Override
    public void showBanner() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    adView.setVisibility(AdView.VISIBLE);
                    adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
                }
            });
        } else {
            adView.setVisibility(AdView.VISIBLE);
            adView.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        }
    }

    @Override
    public void hideBunner() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    adView.setVisibility(AdView.GONE);
                }
            });
        } else {
            adView.setVisibility(AdView.GONE);
        }
    }
}
