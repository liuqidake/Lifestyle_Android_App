package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class BottomButtons extends Fragment
        implements View.OnClickListener {

    OnBottomDataPass mDataPasser;
    ImageButton profile_data, goals, bmi, hikes, weather, help;

    //Callback interface
    public interface OnBottomDataPass{
        public void onBottomDataPass(int data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (OnBottomDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_buttons, container, false);

        //Store buttons
        profile_data = view.findViewById(R.id.b_profile);
        goals = view.findViewById(R.id.b_goals);
        bmi = view.findViewById(R.id.b_bmi);
        hikes = view.findViewById(R.id.b_hikes);
        weather = view.findViewById(R.id.b_weather);
        help = view.findViewById(R.id.b_help);

        //Set listeners
        profile_data.setOnClickListener(this);
        goals.setOnClickListener(this);
        bmi.setOnClickListener(this);
        hikes.setOnClickListener(this);
        weather.setOnClickListener(this);
        help.setOnClickListener(this);

        //Return view
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_profile: {
                mDataPasser.onBottomDataPass(1);
                break;
            }

            case R.id.b_goals: {
                mDataPasser.onBottomDataPass(2);
                break;
            }

            case R.id.b_bmi: {
                mDataPasser.onBottomDataPass(3);
                break;
            }

            case R.id.b_hikes: {
                mDataPasser.onBottomDataPass(4);
                break;
            }

            case R.id.b_weather: {
                mDataPasser.onBottomDataPass(5);
                break;
            }

            case R.id.b_help: {
                mDataPasser.onBottomDataPass(6);
                break;
            }


        }
    }
}
