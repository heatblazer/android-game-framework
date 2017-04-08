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

/**
 * Instance pooling, prevent the continous garbage collection that clogs our
 * realtime application.
 * @param <T>
 */
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

    /**
     *
     * @return fetch us a new Object or give us an already pooled instance
     */
    public T newObject()
    {
        T obj = null;
        if (freeObjects.isEmpty()) {
            obj = factory.createObject();
        } else {
            obj = freeObjects.remove(freeObjects.size()-1);
        }
        return  obj;
    }

    /**
     * @param obj - reinsert the object that we no longer use
     */
    public void free(T obj)
    {
        if (freeObjects.size() < maxSize) {
            freeObjects.add(obj);
        }
    }

}
