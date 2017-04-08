package com.example.ilian.Framework;

/**
 * Created by ilian on 4/7/17.
 */

/**
 * the game framework
 */
public interface GameFramework
{
    public Input getInput();

    public FileIO getFileIO();

    public Audio getAudio();

    public Graphics getGraphics();

    public  void setScreen(Screen s);

    public Screen getCurrentScreen();

    public Screen getMainScreen();
}

