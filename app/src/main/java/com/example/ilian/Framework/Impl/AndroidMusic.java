package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/8/17.
 */

import com.example.ilian.Framework.Music;

import java.io.IOException;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class AndroidMusic implements Music, OnCompletionListener
{
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;

    public AndroidMusic(AssetFileDescriptor assetFd)
    {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(assetFd.getFileDescriptor(),
                    assetFd.getStartOffset(),
                    assetFd.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception ex)  {
            throw  new RuntimeException("Could not load music");
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp)
    {
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void play()
    {
        if (mediaPlayer.isPlaying())
            return;

        try {
            synchronized (this)
            {
                if(!isPrepared) {
                    mediaPlayer.prepare();
                }
                mediaPlayer.start();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void pause()
    {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void setVolume(float vol)
    {
        mediaPlayer.setVolume(vol, vol);
    }

    @Override
    public void stop()
    {
        mediaPlayer.stop();
        synchronized (this) {
            isPrepared = false;
        }
    }

    @Override
    public void setLoop(boolean ok)
    {
        mediaPlayer.setLooping(ok);
    }

    @Override
    public boolean isPlaying()
    {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped()
    {
        return !isPrepared;
    }

    @Override
    public boolean isLooping()
    {
        return mediaPlayer.isLooping();
    }

    @Override
    public void dispose()
    {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }
}
