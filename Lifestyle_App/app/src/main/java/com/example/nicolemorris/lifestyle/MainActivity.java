package com.example.nicolemorris.lifestyle;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements BottomButtons.OnDataPass {

    String username = "Nicole";
//    String username;
    int user_choice = 1;
    double height_inches = 72;
    double weight_pounds = 105;

    ReviewFragment pf;
    GoalsFragment gf;
    BmiFragment bf;
    WeatherFragment wf;
    HelpFragment hf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_choice = getIntent().getIntExtra("CHOICE",0);

        if (username == null) {
            Intent messageIntent = new Intent(this, NewUserActivity.class);
            this.startActivity(messageIntent);

        } else if (user_choice != 0){

            changeFragments();

        } else {

            Intent messageIntent = new Intent(this, HomeActivity.class);
            this.startActivity(messageIntent);
        }

    }

    @Override
    public void onDataPass(int data) {

        user_choice = data;
        changeFragments();

    }

    private void changeFragments(){

        //Find each frame layout, replace with corresponding fragment
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (user_choice == 1){

            //Launch profile information
            pf = new ReviewFragment();
            fTrans.replace(R.id.fl_frag_ph_2,pf,"Profile");

        } else if (user_choice == 2){

            //Launch fitness goals
            gf = new GoalsFragment();
            fTrans.replace(R.id.fl_frag_ph_2,gf,"Goals");

        } else if (user_choice == 3){

            bf = new BmiFragment();

            //Send data to it
            Bundle sentData = new Bundle();
            sentData.putDouble("HEIGHT",height_inches);
            sentData.putDouble("WEIGHT",weight_pounds);
            bf.setArguments(sentData);

            //Launch bmi
            fTrans.replace(R.id.fl_frag_ph_2,bf,"BMI");


        } else if (user_choice == 4){

            //Launch find-a-hike

        } else if (user_choice == 5){

            //Launch weather information
            wf = new WeatherFragment();
            fTrans.replace(R.id.fl_frag_ph_2,wf,"Weather");

        } else if (user_choice == 6){

            //Launch help
            hf = new HelpFragment();
            fTrans.replace(R.id.fl_frag_ph_2,hf,"Help");

        } else {

            //Launch main activity
        }

        fTrans.replace(R.id.fl_frag_ph_1,new HeaderFragment(),"Header");
        fTrans.replace(R.id.fl_frag_ph_3,new BottomButtons(),"Choices");
        fTrans.commit();
    }
}

