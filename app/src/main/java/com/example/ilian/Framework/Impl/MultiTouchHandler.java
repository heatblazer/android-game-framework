package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/9/17.
 */

import java.util.List;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.view.View;
import android.view.MotionEvent;

import com.example.ilian.Framework.Input;
import com.example.ilian.Framework.Pool;
import com.example.ilian.Framework.Pool.PoolObjectFactory;
import com.example.ilian.Framework.Input.TouchEvent;

@TargetApi(5)
public class MultiTouchHandler implements TouchHandler
{
    private static final int MAX_TOUCHPOINTS = 10;

    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];

    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEventsList = new ArrayList<TouchEvent>();
    List<TouchEvent> touchBuffer = new ArrayList<TouchEvent>();

    float scaleX;
    float scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY)
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
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return  false;
            } else {
                return isTouched[index];
            }
        }
    }

    @Override
    public int getTouchX(int pointer)
    {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchX[index];
            }
        }
    }

    @Override
    public int getTouchY(int pointer)
    {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchY[index];
            }
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents()
    {
        synchronized (this) {
            int len = touchEventsList.size();
            for(int i=0; i < len; ++i) {
                touchEventPool.free(touchEventsList.get(i));
            }
            touchEventsList.clear();
            touchEventsList.addAll(touchBuffer);
            touchBuffer.clear();
            return  touchEventsList;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK)
                    >> MotionEvent.ACTION_POINTER_ID_SHIFT;

            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for(int i=0; i < MAX_TOUCHPOINTS; ++i) {
                if (i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    /* if it's an up/down/cancel/out event, mask the id to see if we should
                        process it for this touch point
                    */
                    continue;
                }
                /* states go trough here, carefull now... */
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_DOWN;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_POINTER_UP:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.TOUCH_UP;
                        touchEvent.pointer = pointerId;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = false;
                        id[i] = -1;
                        touchBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.type = TouchEvent.DRAGGED;
                        touchEvent.type = pointerId;
                        touchEvent.x = touchX[i] = (int) (event.getX() * scaleX);
                        touchEvent.y = touchY[i] = (int) (event.getY() * scaleY);
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchBuffer.add(touchEvent);
                        break;
                }
            } // for(...);
            return  true;
        }
    }

    private int getIndex(int pointerID)
    {
        for(int i=0; i < MAX_TOUCHPOINTS; ++i) {
            if (pointerID == id[i]) {
                return  id[i];
            }
        }
        return -1;
    }
}
