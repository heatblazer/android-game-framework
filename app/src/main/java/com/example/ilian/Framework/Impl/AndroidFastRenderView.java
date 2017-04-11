package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/11/17.
 */

import android.graphics.*;
import android.view.*;



/**
 * the fast render view where we actually will draw everythin from canvas to fb
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable
{
    AndroidGame game = null;
    Bitmap frameBuffer;
    Thread renderThread = null;
    SurfaceHolder surfaceHolder = null;
    volatile boolean isRunning = false;

    public AndroidFastRenderView(AndroidGame game, Bitmap fb)
    {
        super(game);
        this.game = game;
        this.frameBuffer = fb;
        this.surfaceHolder = getHolder();
    }

    public void resume()
    {
        isRunning = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void pause()
    {
        isRunning = false;
        while (true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException ex) {
                // retry
            }
        }
    }

    @Override
    public void run()
    {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while (isRunning) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            float dtTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();
            game.getCurrentScreen().update(dtTime);
            game.getCurrentScreen().present(dtTime);

            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
}
