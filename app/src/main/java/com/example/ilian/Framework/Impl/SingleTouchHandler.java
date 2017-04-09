package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/9/17.
 */

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.MotionEvent;

import com.example.ilian.Framework.Input;
import com.example.ilian.Framework.Pool;
import com.example.ilian.Framework.Pool.PoolObjectFactory;
import com.example.ilian.Framework.Input.TouchEvent;

public class SingleTouchHandler implements TouchHandler
{
    boolean isTouched;
    int touchX;
    int touchY;

    Pool <TouchEvent> touchEventPool;
    List <TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List <TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();

    float scaleX;
    float scaleY;

    public  SingleTouchHandler(View view, float scaleX, float scaleY)
    {
        PoolObjectFactory<TouchEvent> factory =
                new PoolObjectFactory<TouchEvent>() {
                    @Override
                    public TouchEvent createObject() {
                        return new TouchEvent();
                    }
                };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean isTouchDown(int pointer)
    {
        synchronized (this) {
            if (pointer == 0) {
                return isTouched;
            } else {
                return false;
            }
        }
    }

    @Override
    public int getTouchX(int pointer)
    {
        synchronized (this) {
            return touchX;
        }
    }

    @Override
    public int getTouchY(int pointer)
    {
        synchronized (this) {
            return touchY;
        }
    }

    /**
     * the same as the key event handler buffering and pooling,
     * see the big explaination there.
     * @return touch events list filled with items from the touchBuffer
     */
    @Override
    public List<TouchEvent> getTouchEvents()
    {
        synchronized (this) {
            int len = touchEvents.size();
            for(int i=0; i < len; ++i) {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

    // this comes from OnTouchListener interface
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        synchronized (this) {
            TouchEvent touchEvent = touchEventPool.newObject();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_MOVE:
                    touchEvent.type = TouchEvent.DRAGGED;
                    isTouched = true;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    isTouched = false;
                    break;
            }
            touchEvent.x = touchX = (int)(event.getX() * scaleX);
            touchEvent.y = touchY = (int)(event.getY() * scaleY);
            touchEventsBuffer.add(touchEvent);
            return true;
        } // unlock
    }
}

