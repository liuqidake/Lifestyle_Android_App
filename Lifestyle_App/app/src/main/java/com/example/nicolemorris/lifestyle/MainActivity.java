package com.example.nicolemorris.lifestyle;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    String username = "Nicole";
    String username = "Nicole";
    int user_choice = 2;
    double height_inches = 72;
    double weight_pounds = 305;
    BmiFragment bf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find each frame layout, replace with corresponding fragment
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();

        if (username == null) {
            Intent messageIntent = new Intent(this, NewUserActivity.class);
            this.startActivity(messageIntent);

        } else if (user_choice == 0){

            //Launch profile information

        } else if (user_choice == 1){

            //Launch fitness goals

        } else if (user_choice == 2){

            //Add data to fragment

            bf = new BmiFragment();

            //Send data to it
            Bundle sentData = new Bundle();
            sentData.putDouble("HEIGHT",height_inches);
            sentData.putDouble("WEIGHT",weight_pounds);
            bf.setArguments(sentData);

            //Launch bmi
            fTrans.replace(R.id.fl_frag_ph_2,bf,"BMI");


        } else if (user_choice == 3){

            //Launch find-a-hike

        } else if (user_choice == 4){

            //Launch weather information

        } else if (user_choice == 5){

            //Launch help

        } else {

            //Launch main activity
        }

        fTrans.replace(R.id.fl_frag_ph_1,new HeaderFragment(),"Header");
        fTrans.replace(R.id.fl_frag_ph_3,new BottomButtons(),"Choices");
        fTrans.commit();

    }
}

