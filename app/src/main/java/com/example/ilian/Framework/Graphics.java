package com.example.ilian.Framework;

/**
 * Created by ilian on 4/7/17.
 */

public interface Graphics
{
    public static  enum PixmapFormat
    {
        ARGB8888,
        ARGB4444, // 12 bits for alpha blending
        RGB565
    }

    public  Pixmap newPixmap(String fname, PixmapFormat fmt);

    public void clear(int color);

    public void putPixel(int x, int y, int color);

    public void drawLine(int x, int y, int x2, int y2, int color);

    public void drawRect(int x, int y, int w, int h, int color);

    public void fillRect(int x, int y, int w, int h, int color);

    public void drawPixmap(Pixmap pix, int x, int y,
                           int srcX, int srcY, int srcW, int srcH);

    public void drawPixmap(Pixmap pix, int x, int y);

    public int getWidth();

    public int getHeight();

}
