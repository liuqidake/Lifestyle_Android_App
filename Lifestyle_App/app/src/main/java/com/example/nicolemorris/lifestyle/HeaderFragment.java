package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HeaderFragment extends Fragment {

    int user_choice;
    TextView tvHeaderTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_header, container, false);

        user_choice = getArguments().getInt("CHOICE");
        tvHeaderTitle = view.findViewById(R.id.tv_header_title);
        setHeaderTitle();

        return view;
    }

    private void setHeaderTitle(){
        if (user_choice == 1){

            tvHeaderTitle.setText("Profile Info");

        } else if (user_choice == 2){

            tvHeaderTitle.setText("Fitness Goals");


        } else if (user_choice == 3){

            tvHeaderTitle.setText("BMI Info");

        } else if (user_choice == 5){

            tvHeaderTitle.setText("Weather Info");

        } else if (user_choice == 6){

            tvHeaderTitle.setText("Help");

        } else if (user_choice == 7) {

            tvHeaderTitle.setText("Edit Profile");

        }
    }

}
