package com.example.sensor0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView show;
    SensorManager sensorManager;
    Sensor lightSensor;
    Sensor proxysensor;
    Sensor tempsensor;
    Sensor accsensor;
    Sensor magsensor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show=findViewById(R.id.show);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proxysensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempsensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        accsensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magsensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       int sensorType=event.sensor.getType();
       float magarr[]=new float[3];
       float acc[]=new float[3];
        switch (sensorType)
        {
            case Sensor.TYPE_LIGHT:
                break;
            case Sensor.TYPE_PROXIMITY:
                float[] value=event.values;
                show.setText("Distance is:"+value[0]);
                break;
            case Sensor.TYPE_ACCELEROMETER:
                acc=event.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magarr=event.values.clone();
                 break;
        }
        float rotationmatrix[]=new float[9];
        boolean rotation=SensorManager.getRotationMatrix(rotationmatrix,null,acc,magarr);
        if (rotation)
        {
            float orientation[]=new float[3];
            SensorManager.getOrientation(rotationmatrix,orientation);
            float azimuth=orientation[0];
            float pitch=orientation[1];
            float roll=orientation[2];

            show.setText("Azimuth "+azimuth+" Pitch:"+pitch+" roll:"+roll);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (lightSensor!=null)
         sensorManager.registerListener(this,lightSensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
                                         sensorManager.unregisterListener(this); //to deregisters every listeners

       // sensorManager.unregisterListener(this,lightSensor); //deregister only specific sensor
    }
}
