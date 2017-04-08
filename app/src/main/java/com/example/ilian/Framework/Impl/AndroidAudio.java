package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/7/17.
 */

import com.example.ilian.Framework.Audio;
import com.example.ilian.Framework.Music;
import com.example.ilian.Framework.Voice;

public class AndroidAudio implements Audio
{

    @Override
    public Music newMusic(String fname)
    {
        return null;
    }

    @Override
    public Voice newSound(String fname)
    {
        return null;
    }
}
