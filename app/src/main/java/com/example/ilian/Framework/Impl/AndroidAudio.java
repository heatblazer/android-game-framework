package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/7/17.
 */

import java.io.IOException;

import android.app.Activity;
import android.content.res.*;
import android.media.*;


import com.example.ilian.Framework.Audio;
import com.example.ilian.Framework.Music;
import com.example.ilian.Framework.Voice;

public class AndroidAudio implements Audio
{
    AssetManager assets;
    SoundPool soundPool;

    public AndroidAudio(Activity activity)
    {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        assets = activity.getAssets();
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Music newMusic(String fname)
    {
        try {
            AssetFileDescriptor afd = assets.openFd(fname);
            return new AndroidMusic(afd);
        } catch (IOException ex) {
            throw  new RuntimeException("Could not load music: " + fname);
        }
    }

    @Override
    public Voice newSound(String fname)
    {
        try {
            AssetFileDescriptor afd = assets.openFd(fname);
            int soundID = soundPool.load(afd, 0);
            return  new AndroidVoice(soundPool, soundID);
        } catch (IOException ex) {
            throw new RuntimeException("Could not load voice: "+fname);
        }
    }
}
