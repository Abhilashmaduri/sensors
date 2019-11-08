package com.example.sensor0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView show,proxy;
    SensorManager sensorManager;
    Sensor lightSensor;
    Sensor proxysensor;
    Sensor tempsensor;
    Sensor accsensor;
    Sensor magsensor;

    float[]accarr=new float[3];
    float[]magarr=new float[3];

    TextView listshow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show=findViewById(R.id.show);
        proxy=findViewById(R.id.proxy);
        listshow=findViewById(R.id.listshow);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);

        lightSensor=sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proxysensor=sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        tempsensor=sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        accsensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magsensor=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener( this,accsensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener( this,magsensor,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener( this,proxysensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        switch (sensorType)
        {
            case Sensor.TYPE_LIGHT:
                break;
            case Sensor.TYPE_PROXIMITY:
                float[] value  = sensorEvent.values;

                proxy.setText("Distance is: "+ +value[0]);
                //tvsensor.setBackgroundColor(Color.CYAN);
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                break;
            case Sensor.TYPE_ACCELEROMETER:
                accarr     =  sensorEvent.values.clone();
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                magarr = sensorEvent.values.clone();
                break;
        }
        float[] rotationMatrix = new float[9];
        boolean rotationOK = SensorManager.getRotationMatrix(rotationMatrix, null,accarr, magarr);

        if(rotationOK)
        {
            float orientation[] = new float[3];
            SensorManager.getOrientation(rotationMatrix, orientation);
            float azimut = orientation[0];
            float pitch = orientation[1];
            float   roll = orientation[2];

            show.setText("azimut is : " + azimut +"\n pitch is : "+ pitch +"\n roll is : "+roll);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void dothis(View view)
    {
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        StringBuilder sb = new StringBuilder();
        for(Sensor s : sensorList)
        {
            String s1 = "Name: "+s.getName()+ " Vendor: "+ s.getVendor()+ " Version:" + s.getVersion();
            sb.append(s1+"\n");
        }
        listshow.setText(sb);
    }

}
