package com.example.ilian.izframework.testgame;

/**
 * Created by ilian on 4/18/17.
 */

import android.provider.Settings;

import com.example.ilian.Framework.FileIO;
import com.example.ilian.Framework.Graphics;
import com.example.ilian.Framework.Graphics.PixmapFormat;
import com.example.ilian.Framework.GameFramework;
import com.example.ilian.Framework.Screen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class LoadingScreen extends Screen
{
    public static class Settings
    {
        public static boolean soundEnabled = true;

        public static void load(FileIO files)
        {
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(
                        files.readFile(".test")));
                soundEnabled = Boolean.parseBoolean(in.readLine());

            } catch (IOException ex) {
                // ok we have configs
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {/*eat it*/}
            }
        }

        public static void save(FileIO files)
        {
            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new OutputStreamWriter(
                        files.writeFile(".test")));
                out.write(Boolean.toString(soundEnabled));

            } catch (IOException ex) {

            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException ex) {/*eat it*/}
                }
            }
        }

    } // Settings



    public LoadingScreen(GameFramework game)

    {
        super(game);
    }
    @Override
    public void update(float dTime)
    {
        Graphics g = game.getGraphics();
        Assets.graphics[Assets.Images.BACKGROUND] = g.newPixmap("backgorund.png", PixmapFormat.RGB565);
        Assets.graphics[Assets.Images.LOGO] = g.newPixmap("logo.png", PixmapFormat.ARGB4444);
        Assets.graphics[Assets.Images.PLAYER] = g.newPixmap("player.png", PixmapFormat.ARGB4444);

        Assets.sounds[Assets.Sounds.PAUSE] = game.getAudio().newSound("pause.ogg");
        Assets.sounds[Assets.Sounds.CLICK] = game.getAudio().newSound("click.ogg");
        Assets.sounds[Assets.Sounds.PLAY] = game.getAudio().newSound("play.ogg");
        Assets.sounds[Assets.Sounds.STOP] = game.getAudio().newSound("stop.ogg");

        LoadingScreen.Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));


    }

    @Override
    public void present(float dTime)
    {
        Graphics g = game.getGraphics();
        g.drawPixmap(Assets.Instance().getGraphicsResource(Assets.Images.LOGO), 0, 0);


    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
