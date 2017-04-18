package com.example.ilian.izframework.testgame;

/**
 * Created by ilian on 4/18/17.
 */

import com.example.ilian.Framework.Screen;
import com.example.ilian.Framework.Impl.AndroidGame;


public class TestGame extends AndroidGame
{
    @Override
    public Screen getStartScreen() {
        return new LoadingScreen(this);
    }
}
