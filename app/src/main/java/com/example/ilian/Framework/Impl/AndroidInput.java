package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/10/17.
 */
import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;

import com.example.ilian.Framework.Input;


public class AndroidInput implements Input
{
    TouchHandler touchHandler;
    KeyboardHandler keysHandler;
    AndroidAccelHandler accelHandler;

    public AndroidInput(Context ctx, View view, float scaleX, float scaleY)
    {
        accelHandler = new AndroidAccelHandler(ctx);
        keysHandler = new KeyboardHandler(view);
        if (Integer.parseInt(VERSION.SDK) < 5) {
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        } else {
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
        }
    }
    @Override
    public boolean isKeyPressed(int keyCode)
    {
        return keysHandler.isKeyPressed(keyCode);
    }

    @Override
    public boolean isTouchDown(int pointer)
    {
        return touchHandler.isTouchDown(pointer);
    }

    @Override
    public int getTouchX(int pointer)
    {
        return  touchHandler.getTouchX(pointer);
    }

    @Override
    public int getTouchY(int pointer)
    {
        return touchHandler.getTouchY(pointer);
    }

    @Override
    public float getAccelX()
    {
        return accelHandler.getAccelX();
    }

    @Override
    public float getAccelY()
    {
        return accelHandler.getAccelY();
    }

    @Override
    public float getAccelZ()
    {
        return accelHandler.getAccelZ();
    }

    @Override
    public List<KeysEvent> getKeyEvents()
    {
        return keysHandler.getKeyEvents();
    }

    @Override
    public List<TouchEvent> getTouchEvents()
    {
        return touchHandler.getTouchEvents();
    }
}

