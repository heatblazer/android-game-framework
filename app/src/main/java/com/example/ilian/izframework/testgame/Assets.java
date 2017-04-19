package com.example.ilian.izframework.testgame;

/**
 * Created by ilian on 4/18/17.
 */

import android.sax.StartElementListener;

import com.example.ilian.Framework.Graphics;
import com.example.ilian.Framework.Impl.AndroidAudio;
import com.example.ilian.Framework.Impl.AndroidGraphics;
import com.example.ilian.Framework.Pixmap;
import com.example.ilian.Framework.Audio;
import com.example.ilian.Framework.Voice;

/**
 * Singleton class for the assets ...
 */
public class Assets
{
    public static class Images
    {
        public static final int BACKGROUND = 0;
        public static final int LOGO = 1;
        public static final int PLAYER = 2;
    }

    public static class Sounds
    {
        public static final int CLICK = 0;
        public static final int PLAY = 1;
        public static final int STOP = 2;
        public static final int PAUSE = 3;
    }
    static Pixmap[] graphics = new Pixmap[Images.PLAYER];
    static Voice[] sounds = new Voice[Sounds.PAUSE];

    private Assets()
    {
    }

    static private Assets s_instance = new Assets();

    public static Assets Instance()
    {
        return s_instance;
    }

    public Pixmap getGraphicsResource(int res)
    {
        if (res < 0 || res >= Images.PLAYER) {
            return  null;
        }
        return  graphics[res];
    }

    public Voice getAudioResource(int res)
    {
        if (res < 0 || res >= Images.PLAYER) {
            return  null;
        }
        return  sounds[res];
    }

}
