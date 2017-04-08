package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/7/17.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.example.ilian.Framework.FileIO;

public class AndroidFileIO implements FileIO
{
    Context ctx;
    AssetManager assets;
    String externalStoragePath;

    AndroidFileIO(Context ctx)
    {
        this.ctx = ctx;
        this.assets = ctx.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
    }

    @Override
    public InputStream readAsset(String fname) throws IOException
    {
        return assets.open(fname);
    }

    @Override
    public InputStream readFile(String fname) throws IOException
    {
        return new FileInputStream(externalStoragePath+fname);
    }

    @Override
    public OutputStream writeFile(String fname) throws IOException
    {
        return new FileOutputStream(externalStoragePath+fname);
    }

    public SharedPreferences getPreferences()
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

}
