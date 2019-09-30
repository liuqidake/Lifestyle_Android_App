package com.example.nicolemorris.lifestyle;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BmiFragment extends Fragment {

    TextView bmi_number, bmi_category;
    double height,weight,bmi_result;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        //Get text views
        bmi_number = view.findViewById(R.id.tv_bmi);
        bmi_category = view.findViewById(R.id.tv_category);

        //Get arguments
        height = getArguments().getDouble("HEIGHT");
        weight = getArguments().getDouble("WEIGHT");

        //Calculate bmi in kg/m^2 (weight x height^2 x 703)
        //Note: 703 is the conversion from lbs/in^2 to kg/m^2
        bmi_result = weight / (height*height) * 703;

        bmi_number.setText(Long.toString(Math.round(bmi_result)));

        if(bmi_result < 18.5){
            bmi_category.setText("Underweight");
        } else if (bmi_result >= 18.5 && bmi_result<=24.9){
            bmi_category.setText("Normal");
        } else if (bmi_result >= 25 && bmi_result<=29.9){
            bmi_category.setText("Overweight");
        } else if (bmi_result >= 30){
            bmi_category.setText("Obsese");
        }

        return view;
    }

}
