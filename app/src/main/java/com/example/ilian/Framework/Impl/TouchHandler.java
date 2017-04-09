package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/9/17.
 */

import com.example.ilian.Framework.Input.TouchEvent;

import java.util.List;
import android.view.View.OnTouchListener;


public interface TouchHandler extends OnTouchListener
{
    public boolean isTouchDown(int pointer);

    public int getTouchX(int pointer);

    public int getTouchY(int pointer);

    public List<TouchEvent> getTouchEvents();
}
