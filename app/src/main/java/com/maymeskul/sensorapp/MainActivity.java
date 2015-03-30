package com.maymeskul.sensorapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Arrays;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView tvData;
    SensorManager sensorManager;
    Sensor sensorCompass;
    private SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvData = (TextView) findViewById(R.id.tv_data);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorCompass = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                Log.d(TAG, "onSensorChanged." + Arrays.toString(event.values));
                tvData.setText(
                        values[0]+ "\n"
                + values[1]+"\n"
                + values[2]+"\n");
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                Log.d(TAG, "onAccuracyChanged." + accuracy);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(listener, sensorCompass, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listener, sensorCompass);
    }
}
