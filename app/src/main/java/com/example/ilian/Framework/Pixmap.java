package com.example.ilian.Framework;

/**
 * Created by ilian on 4/7/17.
 */

import com.example.ilian.Framework.Graphics;

public interface Pixmap
{
    public int getWidth();

    public int getHeight();

    public Graphics.PixmapFormat getFormat();

    public void dispose();
}
