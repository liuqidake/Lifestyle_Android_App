package com.example.nicolemorris.lifestyle;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewUserActivity extends AppCompatActivity {

    int creation_step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        if(savedInstanceState!=null){
            creation_step = savedInstanceState.getInt("STEP");
        }

        //Find each frame layout, replace with corresponding fragment
        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_ph_1,new TitleFragment(),"Title");

        if(creation_step==0){

            //Name & birthday
            fTrans.replace(R.id.fl_frag_ph_2,new NameAgeFragment(), "Location");
            creation_step++;

        } else if (creation_step==1){

            //Physical details
            fTrans.replace(R.id.fl_frag_ph_2,new PhysDetailsFragment(), "Location");
            creation_step++;

        } else if (creation_step==2){

            //Location
            fTrans.replace(R.id.fl_frag_ph_2,new LocationFragment(), "Location");
            creation_step++;

        } else if (creation_step==3){

            //Profile Picture
            fTrans.replace(R.id.fl_frag_ph_2,new ProfilePicFragment(), "Location");
            creation_step++;

        } else if (creation_step==4){

            //Review
            fTrans.replace(R.id.fl_frag_ph_2,new ReviewFragment(), "Location");
            creation_step++;

        } else if (creation_step==5) {

            //Edit details
            fTrans.replace(R.id.fl_frag_ph_2,new ReviewFragment(), "Location");
            creation_step++;

        } else {

            //Change to home view

        }

        fTrans.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Put them in the outgoing Bundle
        outState.putInt("STEP",creation_step);

    }

}
