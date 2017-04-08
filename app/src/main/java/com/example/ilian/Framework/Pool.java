package com.example.ilian.Framework;

/**
 * Created by ilian on 4/8/17.
 */

/**
 * Java garbage collection is poor when firing events
 * reuse them wisely
 */

import java.util.List;
import java.util.ArrayList;

public class Pool<T>
{
    public interface PoolObjectFactory<T>
    {
        public T createObject();
    }

    private final List<T> freeObjects;
    private final PoolObjectFactory<T> factory;
    private final int maxSize;

    public Pool(PoolObjectFactory<T> factory, int maxSize)
    {
        this.factory = factory;
        this.maxSize = maxSize;
        this.freeObjects = new ArrayList<T>(maxSize);
    }

}
