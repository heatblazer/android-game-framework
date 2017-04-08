package com.example.ilian.Framework.Impl;

/**
 * Created by ilian on 4/8/17.
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

public class AndroidAccelHandler implements SensorEventListener
{
    float accelX;
    float accelY;
    float accelZ;

    public  AndroidAccelHandler(Context ctx)
    {
        SensorManager manager = (SensorManager) ctx
                .getSystemService(Context.SENSOR_SERVICE);
        if (manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0) {
            Sensor accelometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelometer, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        accelX = event.values[0];
        accelY = event.values[1];
        accelZ = event.values[2];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
        // unused
    }

    public float getAccelX() { return  accelX; }

    public float getAccelY() { return  accelY; }

    public float getAccelZ() { return  accelZ; }

}
