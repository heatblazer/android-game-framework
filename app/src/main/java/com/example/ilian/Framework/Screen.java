package com.example.ilian.Framework;

/**
 * Created by ilian on 4/7/17.
 */


public abstract class Screen
{
    protected final GameFramework game;

    public Screen(GameFramework game)
    {
        this.game = game;
    }

    public abstract void update(float dTime);

    public abstract void present(float dTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
