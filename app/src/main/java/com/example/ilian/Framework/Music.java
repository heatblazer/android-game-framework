package com.example.ilian.Framework;

/**
 * Created by ilian on 4/8/17.
 */

public interface Music
{
    public void play();

    public void setVolume(float vol);

    public void stop();

    public void pause();

    public void setLoop(boolean ok);

    public boolean isPlaying();

    public boolean isStopped();

    public boolean isLooping();

    public void dispose();

}
