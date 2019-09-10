package com.example.nicolemorris.lifestyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.nicolemorris.lifestyle.Database.DatabaseHelper;

import java.util.Calendar;

public class NewUserActivity extends AppCompatActivity
        implements NameAgeFragment.NameAgeOnDataPass, PhysDetailsFragment.PhysOnDataPass,
        LocationFragment.LocationOnDataPass, ProfilePicFragment.ProfilePicOnDataPass,
        ReviewFragment.ReviewOnDataPass {

    int creation_step = 1;
    String name, city, state, weight, sex;
    int feet, inches;
    int age;

    //Add database
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Get database
        db = new DatabaseHelper(this);

        if(savedInstanceState!=null){
            creation_step = savedInstanceState.getInt("STEP");
        } else {
            setView();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //Put them in the outgoing Bundle
        outState.putInt("STEP",creation_step);

    }

    @Override
    public void onNameAgeDataPass(Calendar date, String name) {
        // print to test
        this.name = name;
        Calendar today = Calendar.getInstance();
        this.age = today.get(Calendar.YEAR) - date.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < date.get(Calendar.DAY_OF_YEAR)){
            this.age--;
        }
        creation_step=1;
        setView();
    }

    @Override
    public void onPhysDataPass(String[] data) {

        feet = Integer.valueOf(data[0]);
        inches = Integer.valueOf(data[1]);
        weight = data[2];
        sex = data[3];

        creation_step=2;
        setView();
    }


    @Override
    public void onLocationDataPass(String[] data) {
        this.state = data[0];
        this.city = data[1];
        creation_step=3;
        setView();
    }

    @Override
    public void onProfilePicDataPass(String data) {
        // no data returned from profile fragment
        creation_step=4;
        setView();

    }

    @Override
    public void onReviewDataPass(int choice) {
        // no returned data
        creation_step=choice;
        setView();

    }

    private void setView(){
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
            //store current collected data into database
            db.insertData(name, age, city, state, feet, inches, weight, sex);
            creation_step++;

        } else if (creation_step==5) {

            //Edit details
            fTrans.replace(R.id.fl_frag_ph_2,new ChangeProfileFragment(), "Location");
            creation_step++;

        } else if (creation_step==6){

            //Change to home view
            Intent messageIntent = new Intent(this, HomeActivity.class);
            this.startActivity(messageIntent);

        }

        fTrans.commit();
    }

}
