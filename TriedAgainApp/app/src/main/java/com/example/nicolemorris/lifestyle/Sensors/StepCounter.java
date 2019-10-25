package com.example.nicolemorris.lifestyle.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

public class StepCounter {
    private SensorManager mSensorManager;
    private Sensor mStepCounter;
    private Context mContext;
    TextView tv_z;
    int total_steps;
    int last_steps;
boolean mFirstStep;
    public StepCounter(Context context, TextView z){
//        System.out.println("IN STEP COUNTER CONSTRUCTOR");
        mContext = context;
        tv_z = z;
        total_steps = 0;
        mFirstStep = true;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //UPDATE DATA IN DATABASE
//            String.valueOf(sensorEvent.values[0])
            if(!mFirstStep) {
                for (int i = 0; i < sensorEvent.values.length; i++) {
                    System.out.println("sensorEvent.values[" + i + "] = " + sensorEvent.values[i]);
                }
                total_steps = last_steps - Math.round(sensorEvent.values[0]) + total_steps;
                tv_z.setText(String.valueOf(total_steps));
//            System.out.println("Steps = " + sensorEvent.values[0]);
            } else {
                tv_z.setText(String.valueOf(total_steps));
            }
            last_steps = Math.round(sensorEvent.values[0]);
            mFirstStep = false;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };

    public SensorEventListener getListener(){
        return mListener;
    }


}
