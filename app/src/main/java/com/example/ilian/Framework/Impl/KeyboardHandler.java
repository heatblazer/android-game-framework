package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/8/17.
 */

import com.example.ilian.Framework.Input.KeysEvent;
import com.example.ilian.Framework.Pool;
import com.example.ilian.Framework.Pool.PoolObjectFactory;

import java.util.List;
import java.util.ArrayList;

import android.view.View;
import android.view.View.OnKeyListener;

public class KeyboardHandler implements OnKeyListener
{
    boolean[] pressedKeys = new boolean[128]; // map from KeyEvent.KEY_CODE_XXX
    Pool<KeysEvent> keyEventPool;
    List<KeysEvent> keyEventBuffer = new ArrayList<KeysEvent>();
    List<KeysEvent> keyEvents = new ArrayList<KeysEvent>();

    KeyboardHandler(View view)
    {
        PoolObjectFactory<KeysEvent> factory = new PoolObjectFactory<KeysEvent>() {
            @Override
            public KeysEvent createObject() {
                return new KeysEvent();
            }
        };
        keyEventPool = new Pool<KeysEvent>(factory, 100);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    public boolean onKey(View v, int keyCode, android.view.KeyEvent event)
    {
        if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) {
            return false;
        }

        synchronized (this) {
            KeysEvent keyEvent = keyEventPool.newObject();
            keyEvent.keyType = keyCode;
            keyEvent.keyChar = (char) event.getUnicodeChar();

            if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
                keyEvent.type = KeysEvent.KEY_DOWN;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys[keyCode] = true;
                }
            }
            if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
                keyEvent.type = KeysEvent.KEY_UP;
                if (keyCode > 0 && keyCode < 127) {
                    pressedKeys[keyCode] = false;
                }
            }
            // see getKeyEvents() explained
            // buffer the key event here
            keyEventBuffer.add(keyEvent);
        }
        return false;
    }

    /**
     * return a key code being pressed or not
     * @param keycode
     * @return key satys - no need to synchro sicne we work with primitives here
     */
    boolean isKeyPressed(int keycode)
    {
        if (keycode < 0 || keycode > 127) {
            return  false;
        }
        return pressedKeys[keycode];
    }

    /**
     * Called from UI thread so be careful here- lock is needed
     * Careful now on the explaination, since it's a bit tricky to understand the dbl buffering :
     * UI Thread -> onKey(): key event arrives, nothing to reuse
     *              keyEvents = { } keyEventBuffer = {KeyEvent1} pool={ }
     * Main Thread -> getKeyEvents(): process keys, nothing to reuse
     *              keyEvents = {keyEvent1} keyEventBuffer = { } pool = { }
     * UI Thread -> onKey(): another event arrives
     *              keyEvents = {keyEvent1} keyEventBuffer = {keyEvent2} pool = { }
     * MainThread -> getKeyEvents(): process keys, still nothing to reuse
     *              keyEvents = {KeyEvent2} keyEventBuffer = { } pool = {KeyEvent1}
     * UI Thread -> onKey(): another key arrives, pool holds KeyEvent1 here
     *              keyEvents = {KeyEvent2} keyEventBuffer = {KeyEvent1} pool = { }
     *
         1.	 We get a new event in the UI thread. There’s nothing in the Pool yet, so
         a new KeyEvent instance (KeyEvent1) is created and inserted into the
         keyEventsBuffer list.
         2.	 We call getKeyEvents() on the main thread. getKeyEvents() takes
         KeyEvent1 from the keyEventsBuffer list and puts it into the keyEvents
         list that is returns to the caller.
         3.	 We get another event on the UI thread. We still have nothing in the Pool,
         so a new KeyEvent instance (KeyEvent2) is created and inserted into the
         keyEventsBuffer list.
         4.	 The main thread calls getKeyEvents() again. Now, something
         interesting happens. Upon entry into the method, the keyEvents list
         still holds KeyEvent1. The insertion loop will place that event into our
         Pool. It then clears the keyEvents list and inserts any KeyEvent into the
         keyEventsBuffer, in this case, KeyEvent2. We just recycled a key event.
         5.	 Another key event arrives on the UI thread. This time, we have a free
         KeyEvent in our Pool, which we happily reuse. Incredibly, there’s no
         garbage collection!
     * @return the newly filled key buffer
     */
    public List<KeysEvent> getKeyEvents()
    {
        synchronized (this) {
            int len = keyEvents.size();
            for(int i=0; i < len; ++i) {
                keyEventPool.free(keyEvents.get(i)); // pool them for reusing
            }
            keyEvents.clear();
            keyEvents.addAll(keyEventBuffer);
            keyEventBuffer.clear();
            return keyEvents;
        }
    }

}

