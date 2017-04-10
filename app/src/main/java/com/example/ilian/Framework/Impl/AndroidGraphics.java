package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/10/17.
 */
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.graphics.Bitmap.*;
import android.graphics.*;
import android.graphics.BitmapFactory;
import  android.graphics.BitmapFactory.*;

import com.example.ilian.Framework.Graphics;
import com.example.ilian.Framework.Pixmap;

public class AndroidGraphics implements Graphics
{
    AssetManager assetManager;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect sourceRect = new Rect();
    Rect destRect = new Rect();

    public AndroidGraphics(AssetManager res, Bitmap fb)
    {
        assetManager = res;
        frameBuffer = fb;
        canvas = new Canvas(frameBuffer);
        paint = new Paint();
    }

    @Override
    public Pixmap newPixmap(String fname, PixmapFormat fmt)
    {
        Config cfg = null;
        if (fmt == PixmapFormat.ARGB4444) {
            cfg = Config.ARGB_4444;
        } else if (fmt == PixmapFormat.ARGB8888) {
            cfg = Config.ARGB_8888;
        } else {
            cfg = Config.RGB_565;
        }

        Options opts = new Options();
        opts.inPreferredConfig = cfg;
        InputStream in = null;
        Bitmap bmp = null;
        try {
            in = assetManager.open(fname);
            bmp = BitmapFactory.decodeStream(in);
            if (bmp == null) {
                throw new RuntimeException("Could not load asset from file: "+fname);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not load asset from file: "+fname);
        } finally {
            if (in != null) {
                try { in.close(); } catch (IOException ex) {/*eat it*/}
            }
        }

        if (bmp.getConfig() == Config.ARGB_4444) {
            fmt = PixmapFormat.ARGB4444;
        } else if (bmp.getConfig() == Config.RGB_565) {
            fmt = PixmapFormat.RGB565;
        } else {
            fmt = PixmapFormat.ARGB8888;
        }

        return  new AndroidPixmap(bmp, fmt);
    }

    @Override
    public void clear(int color)
    {
        // the R, G and B masked and shifted to match the color,
        // no alpha tho...
        canvas.drawRGB((color & 0xff0000) >> 16,
                (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void putPixel(int x, int y, int color)
    {
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color)
    {
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int w, int h, int color)
    {
        paint.setColor(color);
        canvas.drawRect(x, y, w, h, paint);
    }

    @Override
    public void fillRect(int x, int y, int w, int h, int color)
    {
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(x, y, w, h, paint);
    }

    @Override
    public void drawPixmap(Pixmap pix, int x, int y, int srcX, int srcY, int srcW, int srcH)
    {
        sourceRect.left = srcX;
        sourceRect.right  = srcX + srcW -1;
        sourceRect.top = srcY;
        sourceRect.bottom = srcY + srcH - 1;

        destRect.left = x;
        destRect.right = x + srcX -1;
        destRect.top = y;
        destRect.bottom = y + srcY -1;

        canvas.drawBitmap(((AndroidPixmap) pix).bitmap, sourceRect, destRect, null);
    }

    @Override
    public void drawPixmap(Pixmap pix, int x, int y)
    {
        canvas.drawBitmap( ((AndroidPixmap)pix).bitmap, x, y, null);
    }

    @Override
    public int getWidth()
    {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight()
    {
        return frameBuffer.getHeight();
    }
}

