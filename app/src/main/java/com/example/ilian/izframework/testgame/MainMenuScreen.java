package com.example.ilian.izframework.testgame;

/**
 * Created by ilian on 4/19/17.
 */

import java.util.List;

import com.example.ilian.Framework.*;
import com.example.ilian.Framework.Input;


public class MainMenuScreen extends Screen
{

    public MainMenuScreen(GameFramework game)
    {
        super(game);
    }

    /**
     * check if we are in the bounds
     * @param ev - event x/y
     * @param x - check against x
     * @param y - check against y
     * @param w - width of rect
     * @param h - height of rect
     * @return - true if we are in bounds, else false
     */
    private boolean inRect(Input.TouchEvent ev, int x, int y, int w, int h)
    {
        if ( (ev.x > x ) && (ev.x < x + w - 1) &&
                (ev.y > y) && (ev.y < y + h - 1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void update(float dTime)
    {
        Graphics g = game.getGraphics();
        List<Input.TouchEvent> touchEvents = game.getInput().getTouchEvents();
        game.getInput().getKeyEvents();
        int len = touchEvents.size();
        for(int i=0; i < len; ++i) {
            Input.TouchEvent event = touchEvents.get(i);
            if (event.type == Input.TouchEvent.TOUCH_UP) {
                //todo make boud checks later
            }
        }
    }

    @Override
    public void present(float dTime) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
