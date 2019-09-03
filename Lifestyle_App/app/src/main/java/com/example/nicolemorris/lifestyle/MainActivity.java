package com.example.nicolemorris.lifestyle;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

//    String username = "Nicole";
    String username = "Nicole";
    int user_choice = 2;

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

            //Launch bmi
            fTrans.replace(R.id.fl_frag_ph_1,new HeaderFragment(),"Header");
            fTrans.replace(R.id.fl_frag_ph_2,new BmiFragment(),"BMI");
            fTrans.replace(R.id.fl_frag_ph_3,new BottomButtons(),"Choices");

        } else if (user_choice == 3){

            //Launch find-a-hike

        } else if (user_choice == 4){

            //Launch weather information

        } else if (user_choice == 5){

            //Launch help

        } else {

            //Launch main activity
        }

//        fTrans.replace(R.id.fl_frag_ph_1,new ChoicesListFragment(),"Choices");
        fTrans.commit();

    }
}

