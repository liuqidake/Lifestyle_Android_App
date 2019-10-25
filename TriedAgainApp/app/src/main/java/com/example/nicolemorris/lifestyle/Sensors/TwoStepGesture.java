package com.example.nicolemorris.lifestyle.Sensors;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class TwoStepGesture {

    private SensorManager mSensorManager;
    private Sensor mTwoSteps;
    private Context mContext;

    private final double mMaxThreshold = 2.0;
    private final double mMinThreshold = 1.0;

//    private final double mMaxThreshold = 0.10;
//    private final double mMinThreshold = 0.05;

    private double last_x, last_y, last_z;
    private double now_x, now_y,now_z;
    private boolean mNotFirstTime;
    int steps;
    TextView tv_x, tv_y, tv_z;

    StepCounter stepCounter;
    StopGesture stopGesture;
    TwoStepGesture thisGesture;

    public TwoStepGesture(Context context, TextView z){
        mContext = context;
        tv_z = z;
        steps = 0;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mTwoSteps = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(mTwoSteps!=null){
            mSensorManager.registerListener(mListener,mTwoSteps,SensorManager.SENSOR_DELAY_NORMAL);
        }
        thisGesture = this;
    }

    private SensorEventListener mListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //Get the acceleration rates along the y and z axes
            now_y = sensorEvent.values[1];
            now_z = sensorEvent.values[2];

//            System.out.print("X: " + sensorEvent.values[0] + ", Y: " + sensorEvent.values[1] + ", Z: " + sensorEvent.values[2]);

            //Check if step (forward fast but not too fast & far but not too far) was taken (no = break)

            //If not motion sense
//            if(mNotFirstTime){
//                double dx = Math.abs(last_x - now_x);
//                double dy = Math.abs(last_y - now_y);
                double dz = last_z - now_z;

                //If step was taken
//                if (dz > mMaxThreshold && dz > mMinThreshold) {

                        /*
                        SOUND NOTIFICATION
                         */
//                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                        Ringtone r = RingtoneManager.getRingtone(mContext, notification);
//                        r.play();

                        /*
                        START STEP COUNTER
                         */
                        stepCounter = new StepCounter(mContext, tv_z);

                        /*
                        START STOP GESTURE
                         */
                        stopGesture = new StopGesture(mContext, stepCounter, tv_z); //REMOVE Z TV LATER

                        /*
                        PAUSE LISTENER
                         */
                        mSensorManager.unregisterListener(mListener);

                        /*
                        FOR TESTING ONLY (REMOVE LATER)
                         */
//                        tv_z.setText("Last: " + String.valueOf(last_z) + ", Now: " + String.valueOf(now_z));

//                }

//            }

            last_x = now_x;
            last_y = now_y;
            last_z = now_z;
            mNotFirstTime = true;

        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };
}
