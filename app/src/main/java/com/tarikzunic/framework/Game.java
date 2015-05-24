package com.tarikzunic.framework;

import com.google.android.gms.ads.AdView;
import com.tarikzunic.framework.imp.Advertising;

/**
 * Created by Tarik on 27.3.2015.
 */
public interface Game {
    public Audio getAudio();

    public Input getInput();

    public FileIO getFileIO();

    public Graphics getGraphics();

    public void setScreen(Screen screen);

    public Screen getCurrentScreen();

    public Screen getStartScreen();

    public Advertising getAdvertising();

//    public AdView getAdView();

//    public void showInterstitial();

    public void showBanner();

    public void hideBunner();

}
