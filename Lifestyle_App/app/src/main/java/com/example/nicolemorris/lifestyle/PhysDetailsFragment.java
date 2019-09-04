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
    String height,weight,sex;
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nextButton = view.findViewById(R.id.b_next_page);
        nextButton.setOnClickListener(this);
        // Apply the adapter to the spinner
        s_sex.setAdapter(adapter);


        return view;

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        switch (view.getId()){
            case R.id.s_sex: {
                dataToPass[2] = parent.getItemAtPosition(pos).toString();
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
            case R.id.b_next_page: {
                mDataPasser.onPhysDataPass(dataToPass);
                break;
            }
        }
    }

}
