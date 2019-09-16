package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class PhysDetailsFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner s_h_feet,s_h_inches,s_w_pounds1,s_w_pounds2,s_w_pounds3,s_sex;
    String[] dataToPass;
    PhysOnDataPass mDataPasser;
    Button nextButton;

    //Callback interface
    public interface PhysOnDataPass{
        public void onPhysDataPass(String[] data);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mDataPasser = (PhysOnDataPass) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement OnDataPass");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phys_details, container, false);

        dataToPass = new String[4];
        dataToPass[0] = "0";
        dataToPass [1] = "0";
        dataToPass[2] = "0";
        dataToPass[3] = "unknown";

        ArrayAdapter<CharSequence> num_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_array, android.R.layout.simple_spinner_item);

        s_h_feet  = (Spinner)view.findViewById(R.id.s_feet);
        s_h_feet.setAdapter(num_adapter);
        s_h_feet.setOnItemSelectedListener(this);

        s_h_inches = (Spinner)view.findViewById(R.id.s_inches);
        s_h_inches.setAdapter(num_adapter);
        s_h_inches.setOnItemSelectedListener(this);

        s_w_pounds1 = (Spinner)view.findViewById(R.id.s_weight1);
        s_w_pounds1.setAdapter(num_adapter);
        s_w_pounds1.setOnItemSelectedListener(this);

        s_w_pounds2 = (Spinner)view.findViewById(R.id.s_weight2);
        s_w_pounds2.setAdapter(num_adapter);
        s_w_pounds2.setOnItemSelectedListener(this);

        s_w_pounds3 = (Spinner)view.findViewById(R.id.s_weight3);
        s_w_pounds3.setAdapter(num_adapter);
        s_w_pounds3.setOnItemSelectedListener(this);


        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        s_sex = (Spinner)view.findViewById(R.id.s_sex);
        s_sex.setAdapter(gender_adapter);
        s_sex.setOnItemSelectedListener(this);

        nextButton = view.findViewById(R.id.b_next);
        nextButton.setOnClickListener(this);

        return view;

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (view.getId()){
            case R.id.s_feet: {
                dataToPass[0] = (String) parent.getItemAtPosition(pos); // number of feet e.g."5"
                break;
            }
            case R.id.s_inches: {
                dataToPass[1] = (String)parent.getItemAtPosition(pos); // number of inches e.g. "10"
                break;
            }
            case R.id.s_weight: {
                dataToPass[2] = (String)parent.getItemAtPosition(pos);
                break;
            }
            case R.id.s_sex: {
                dataToPass[3] = (String)parent.getItemAtPosition(pos);
                break;
            }
        }

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        dataToPass[0] = "0";
        dataToPass [1] = "0";
        dataToPass[2] = "0";
        dataToPass[3] = "unknown";
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_next: {

                //NEED TO ADD HEIGHT, WEIGHT, SEX TO PASS FOR STORAGE :)
                mDataPasser.onPhysDataPass(dataToPass);
                break;
            }
        }
    }

}