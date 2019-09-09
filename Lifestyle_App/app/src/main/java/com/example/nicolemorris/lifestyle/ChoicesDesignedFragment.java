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

public class ChoicesDesignedFragment extends Fragment
        implements View.OnClickListener {

    OnChoiceDataPass mDataPasser;
    ImageButton profile_data, goals, bmi, hikes, weather, help;

    //Callback interface
    public interface OnChoiceDataPass{
        public void onChoiceDataPass(int data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (OnChoiceDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choices_designed, container, false);

        //Store buttons
        profile_data = view.findViewById(R.id.ib_profile);
        goals = view.findViewById(R.id.ib_goals);
        bmi = view.findViewById(R.id.ib_bmi);
        hikes = view.findViewById(R.id.ib_hike);
        weather = view.findViewById(R.id.ib_weather);
        help = view.findViewById(R.id.ib_help);

        //Set listeners
        profile_data.setOnClickListener(this);
        goals.setOnClickListener(this);
        bmi.setOnClickListener(this);
        hikes.setOnClickListener(this);
        weather.setOnClickListener(this);
        help.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_profile: {
                mDataPasser.onChoiceDataPass(1);
                break;
            }

            case R.id.ib_goals: {
                mDataPasser.onChoiceDataPass(2);
                break;
            }

            case R.id.ib_bmi: {
                mDataPasser.onChoiceDataPass(3);
                break;
            }

            case R.id.ib_hike: {
                mDataPasser.onChoiceDataPass(4);
                break;
            }

            case R.id.ib_weather: {
                mDataPasser.onChoiceDataPass(5);
                break;
            }

            case R.id.ib_help: {
                mDataPasser.onChoiceDataPass(6);
                break;
            }


        }
    }
}
