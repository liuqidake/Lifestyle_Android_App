package com.example.nicolemorris.lifestyle;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ChangeProfileFragment extends Fragment
        implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    EditText etName, etCity, etState;
    Spinner s_h_feet,s_h_inches,s_w_pounds,s_sex;
    String dHeight,dWeight,dSex;
    String[] dataToPass;
    Button bSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_profile, container, false);

        etName = view.findViewById(R.id.et_name);
        etCity = view.findViewById(R.id.et_city);
        etState = view.findViewById(R.id.et_state);

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

        bSave = view.findViewById(R.id.b_save);
        bSave.setOnClickListener(this);

        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (view.getId()){
            case R.id.s_feet: {

            }
            case R.id.s_inches: {

            }
            case R.id.s_weight: {

            }
            case R.id.s_sex: {

            }
        }

        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_save: {

                break;
            }
        }
    }

}
