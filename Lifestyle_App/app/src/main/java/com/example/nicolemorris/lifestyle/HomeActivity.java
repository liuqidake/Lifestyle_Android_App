package com.example.nicolemorris.lifestyle;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class HomeActivity extends AppCompatActivity
        implements ChoicesListFragment.OnDataPass {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentTransaction fTrans = getSupportFragmentManager().beginTransaction();
        fTrans.replace(R.id.fl_frag_ph_1,new ChoicesListFragment(),"Choices");
        fTrans.commit();

    }

    @Override
    public void onDataPass(int data) {

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("CHOICE", data);
        startActivity(intent);

    }

}
