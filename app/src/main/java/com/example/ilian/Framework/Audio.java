package com.example.ilian.Framework;

/**
 * Created by ilian on 4/6/17.
 */

import com.example.ilian.Framework.Voice;
import com.example.ilian.Framework.Music;

public interface Audio
{
    public Music newMusic(String fname);
    public Voice newSound(String fname);
}


