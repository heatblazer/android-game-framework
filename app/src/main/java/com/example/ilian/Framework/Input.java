package com.example.ilian.Framework;

/**
 * Created by ilian on 4/6/17.
 */

import java.util.List;

public interface Input
{
    public static class KeysEvent
    {
        public static final int KEY_DOWN = 0;
        public static final int KEY_UP = 1;

        public int type;
        public int keyType;
        public char keyChar;
    }

    public static class TouchEvent
    {
        public static final int TOUCH_DOWN = 0;
        public static final int TOUCH_UP = 1;
        public static final int DRAGGED = 2;

        int type;
        int x, y;
        int pointer;
    }

    public boolean isKeyPressed(int keyCode);
    public boolean isTouchDown(int pointer);
    public int getTouchX();
    public int getTouchY();
    public float getAccelX();
    public float getAccelY();
    public float getAccelZ();

    public List<KeysEvent> getKeyEvents();
    public List<TouchEvent> getTouchEvents();

}
