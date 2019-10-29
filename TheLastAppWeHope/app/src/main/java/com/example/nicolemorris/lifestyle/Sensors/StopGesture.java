package com.example.nicolemorris.lifestyle.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nicolemorris.lifestyle.Model.User;
import com.example.nicolemorris.lifestyle.Model.UserViewModel;

public class StopGesture {

    private SensorManager mSensorManager;
    private Sensor mStop;
    private Context mContext;

    private double last_z;
    private double now_z;
    StepCounter mStepCounter;
    StartGesture mStartGesture;
    UserViewModel mUserViewModel;
    User mUser;

    public StopGesture(Context context, StepCounter stepCounter, UserViewModel userViewModel, User user){
        mContext = context;
        mStepCounter = stepCounter;
        mUserViewModel = userViewModel;
        mUser = user;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStop = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        if(mStop!=null){
            mSensorManager.registerListener(mListener,mStop,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    private SensorEventListener mListener = new SensorEventListener() {


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //Get the acceleration rates along the y and z axes

            now_z = sensorEvent.values[2];

            //If not motion sense
//                double dx = Math.abs(last_x - now_x);
//                double dy = Math.abs(last_y - now_y);
                double dz = Math.abs(last_z - now_z);

                if (now_z == last_z) {

                    /*
                    SOUND NOTIFICATION
                    */
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(mContext, notification);
                    r.play();

                    /*
                    DISPLAY TOAST
                     */
                    Toast.makeText(mContext, "Step Counter Deactivated", Toast.LENGTH_SHORT).show();

                    /*
                    STOP STEP COUNTER
                    */

                    mStepCounter.stop();

                    /*
                    START START GESTURE
                    */
                    mStartGesture = new StartGesture(mContext, mUserViewModel, mUser);

                    /*
                    STOP LISTENER
                    */
                    mSensorManager.unregisterListener(mListener);

                    /*
                    FOR TESTING ONLY (REMOVE LATER)
                    */
//                    tv_z.setText("Stopped");
                }

            last_z = now_z;
//            mNotFirstTime = true;
        }


        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };

}
