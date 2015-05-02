package com.tarikzunic.shootemfast;

import android.app.Activity;

import com.tarikzunic.framework.Screen;
import com.tarikzunic.framework.imp.AndroidGame;

/**
 * Created by Tarik on 1.5.2015.
 */
public class ShootemGame extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
