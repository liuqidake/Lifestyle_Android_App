package com.example.nicolemorris.lifestyle.Sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;

import com.example.nicolemorris.lifestyle.Activities.MainActivity;
import com.example.nicolemorris.lifestyle.Model.User;
import com.example.nicolemorris.lifestyle.Model.UserRepo;
import com.example.nicolemorris.lifestyle.Model.UserViewModel;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class StepCounter {
    private SensorManager mSensorManager;
    private Sensor mStepCounter;
    private Context mContext;

    int total_steps;
    int last_steps;
    User mUser;
    UserViewModel mUserViewModel;

boolean mFirstStep;
    public StepCounter(Context context, UserViewModel userViewModel){
//        System.out.println("IN STEP COUNTER CONSTRUCTOR");
        mContext = context;
        mUserViewModel = userViewModel;
        total_steps = 0;
        mFirstStep = true;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }
        mUserViewModel = userViewModel;


    }

    final Observer<User> userObserver = new Observer<User>() {
        @Override
        public void onChanged(@Nullable final User user) {
            // Update the UI if this data variable changes
            if (user != null) {
                mUser = user;
            }
        }
    };

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //Get current timestamp & steps
            int newSteps = Math.round(sensorEvent.values[0]);

            //If there is a timestamp (-1 is placeholder for none saved)
            if(mUser.getStepTimeStamp() == -1 ||
                    ///If change in timestamps is greater than a day
                (TimeUnit.NANOSECONDS.toDays(sensorEvent.timestamp - mUser.getStepTimeStamp()) > 1)) {

                //Save new daily steps
                mUser.setDailySteps(newSteps);

            } else {

                //If same day, update steps to steps+newsteps
                mUser.setDailySteps(mUser.getDailySteps() + newSteps);
            }

            //Save timestamp
            mUser.setStepTimeStamp((sensorEvent.timestamp));

            UserRepo.updateUserProfile(mContext,mUser);


            //UPDATE DATA IN DATABASE

////            String.valueOf(sensorEvent.values[0])
//            if(!mFirstStep) {
//                for (int i = 0; i < sensorEvent.values.length; i++) {
//                    System.out.println("sensorEvent.values[" + i + "] = " + sensorEvent.values[i]);
//                }
//                total_steps = last_steps - Math.round(sensorEvent.values[0]) + total_steps;
//                tv_z.setText(String.valueOf(total_steps));
////            System.out.println("Steps = " + sensorEvent.values[0]);
//            } else {
//                tv_z.setText(String.valueOf(total_steps));
//            }
//            last_steps = Math.round(sensorEvent.values[0]);
//            mFirstStep = false;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };

    public SensorEventListener getListener(){
        return mListener;
    }


}
