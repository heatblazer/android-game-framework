package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/8/17.
 */

import com.example.ilian.Framework.Voice;
import android.media.SoundPool;

public class AndroidVoice implements Voice
{
    int soundId;
    SoundPool soundPool;

    public AndroidVoice(SoundPool pool, int id)
    {
        this.soundPool = pool;
        this.soundId = id;
    }

    @Override
    public void play(float vol)
    {
        soundPool.play(soundId, vol, vol, 0, 0, 1);
    }

    @Override
    public void dispose()
    {
        soundPool.unload(soundId);
    }
}

