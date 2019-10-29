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

    public StepCounter(Context context, UserViewModel userViewModel, User user){

        mContext = context;
        mUserViewModel = userViewModel;
        mUser = user;
        total_steps = 0;
        mFirstStep = true;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mStepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(mStepCounter!=null){
            mSensorManager.registerListener(mListener,mStepCounter,SensorManager.SENSOR_DELAY_FASTEST);
        }

        mUserViewModel = userViewModel;

    }

    private SensorEventListener mListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {

            //Update data in database
            mUser.setDailySteps(mUser.getDailySteps() + Math.round(sensorEvent.values[0]));
//            mUser.setDailySteps(2019);//DELETE THIS LINE ONCE TESTING ON ANDROID PHONE

            UserRepo.updateUserProfile(mContext,mUser);

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }

    };

    public void stop(){ mSensorManager.unregisterListener(mListener);}


}
