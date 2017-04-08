package com.example.ilian.Framework;

/**
 * Created by ilian on 4/6/17.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileIO
{
    public InputStream readAsset(String fname) throws IOException;
    public InputStream readFile(String fname) throws IOException;
    public OutputStream writeFile(String fname) throws IOException;
}

