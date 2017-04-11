package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/11/17.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.*;
import android.os.PowerManager.WakeLock; // prevent screen locks
import android.view.*;


import com.example.ilian.Framework.*; // import our interfaces

public abstract class AndroidGame extends Activity implements GameFramework
{
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

    @Override
    public void onCreate(Bundle instance)
    {
        super.onCreate(instance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        int fbW, fbH;
        if (isLandscape) {
            fbW = 480;
            fbH = 320;
        } else {
            fbW = 320;
            fbH = 480;
        }

        Bitmap framebuffer = Bitmap.createBitmap(fbW, fbH, Config.RGB_565);

        float scaleX = (float) fbW / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) fbH / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, framebuffer);
        graphics = new AndroidGraphics(getAssets(), framebuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");

    }

    @Override
    public void onResume()
    {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }


    @Override
    public Input getInput()
    {
        return input;
    }

    @Override
    public FileIO getFileIO()
    {
        return fileIO;
    }

    @Override
    public Audio getAudio()
    {
        return audio;
    }

    @Override
    public Graphics getGraphics()
    {
        return graphics;
    }

    @Override
    public void setScreen(Screen s)
    {
        if (s == null) {
            throw new IllegalArgumentException("Screen must not be null!");
        }

        screen.pause();
        screen.dispose();
        screen.resume();
        screen.update(0);
        screen = s;
    }

    @Override
    public Screen getCurrentScreen()
    {
        return screen;
    }

    //@Override
    //public Screen getStartScreen() {
    //    return null;
    //}
}
