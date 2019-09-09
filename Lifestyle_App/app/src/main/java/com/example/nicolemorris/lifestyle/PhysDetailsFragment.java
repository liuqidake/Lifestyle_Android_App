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

    Spinner s_h_feet,s_h_inches,s_w_pounds,s_sex;
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
dataToPass = new String[3];
        s_sex = (Spinner) view.findViewById(R.id.s_sex);

        ArrayAdapter<CharSequence> num_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.number_array, android.R.layout.simple_spinner_item);

        s_h_feet  = view.findViewById(R.id.s_feet);
        s_h_feet.setAdapter(num_adapter);

        s_h_inches = view.findViewById(R.id.s_inches);
        s_h_inches.setAdapter(num_adapter);

        s_w_pounds = view.findViewById(R.id.s_weight);
        s_w_pounds.setAdapter(num_adapter);

        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);

        s_sex = view.findViewById(R.id.s_sex);
        s_sex.setAdapter(gender_adapter);

        nextButton = view.findViewById(R.id.b_next);
        nextButton.setOnClickListener(this);

        return view;

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (view.getId()){
            case R.id.s_feet: {
                dataToPass[0] = s_h_feet.getSelectedItem().toString(); // number of feet e.g."5"
                break;
            }
            case R.id.s_inches: {
                dataToPass[1] = s_h_inches.getSelectedItem().toString(); // number of inches e.g. "10"
                break;
            }
            case R.id.s_weight: {
                dataToPass[2] = s_w_pounds.getSelectedItem().toString();
                break;
            }
            case R.id.s_sex: {
                dataToPass[3] = parent.getItemAtPosition(pos).toString();
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
