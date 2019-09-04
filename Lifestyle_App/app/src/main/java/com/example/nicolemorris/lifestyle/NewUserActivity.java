package com.example.nicolemorris.lifestyle;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewUserActivity extends AppCompatActivity
        implements NameAgeFragment.NameAgeOnDataPass, PhysDetailsFragment.PhysOnDataPass,
        LocationFragment.LocationOnDataPass, ProfilePicFragment.ProfilePicOnDataPass,
        ReviewFragment.ReviewOnDataPass {

    int creation_step = 0;
    String name, city, state, height, weight, sex;
    int age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

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
    public void onNameAgeDataPass(java.util.Date data) {

        creation_step=1;
        setView();

    }

    @Override
    public void onPhysDataPass(String[] data) {

//        height = data[0];
//        weight = data[1];
//        sex = data[2];

        creation_step=2;
        setView();
    }


    @Override
    public void onLocationDataPass(String[] data) {

        creation_step=3;
        setView();

    }

    @Override
    public void onProfilePicDataPass(String data) {

        creation_step=4;
        setView();

    }

    @Override
    public void onReviewDataPass(int choice) {

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
