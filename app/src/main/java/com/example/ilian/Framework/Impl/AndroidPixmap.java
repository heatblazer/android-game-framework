package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/10/17.
 */
import android.graphics.Bitmap;

import com.example.ilian.Framework.Graphics;
import com.example.ilian.Framework.Graphics.PixmapFormat;
import com.example.ilian.Framework.Pixmap;


public class AndroidPixmap implements Pixmap
{
    Bitmap bitmap;
    PixmapFormat format;

    public AndroidPixmap(Bitmap bmp, PixmapFormat fmt)
    {
        bitmap = bmp;
        format = fmt;
    }


    @Override
    public int getWidth()
    {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight()
    {
        return bitmap.getHeight();
    }

    @Override
    public PixmapFormat getFormat()
    {
        return format;
    }

    @Override
    public void dispose()
    {
        bitmap.recycle();
    }
}
